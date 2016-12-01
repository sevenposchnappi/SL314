package servlet_examples;

import java.applet.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import com.oreilly.servlet.HttpMessage;

public class SocketChatApplet extends Applet implements Runnable {

  static final int PORT = 2428;

  DataInputStream serverStream;

  TextArea text;
  Label label;
  TextField input;
  Thread thread;
  String user;

  void broadcastMessage(String message) {
    message = user + ": " + message;  // Pre-pend the speaker's name
    try {
      URL url = new URL(getCodeBase(), "/servlet/ChatServlet");
      HttpMessage msg = new HttpMessage(url);
      Properties props = new Properties();
      props.put("message", message);
      msg.sendPostMessage(props);
    }
    catch (SocketException e) {
      // Can't connect to host, report it and abandon the broadcast
      System.out.println("Can't connect to host: " + e.getMessage());
    }
    catch (FileNotFoundException e) {
      // Servlet doesn't exist, report it and abandon the broadcast
      System.out.println("Resource not found: " + e.getMessage());
    }
    catch (Exception e) {
      // Some other problem, report it and abandon the broadcast
      System.out.println("General exception: " +
        e.getClass().getName() + ": " + e.getMessage());
    }
  }
  String getNextMessage() {
    String nextMessage = null;
    while (nextMessage == null) {
      try {
        // Connect to the server if we haven't before
        if (serverStream == null) {
          Socket s = new Socket(getCodeBase().getHost(), PORT);
          serverStream = new DataInputStream(
                         new BufferedInputStream(
                         s.getInputStream()));
        }
  
        // Read a line
        nextMessage = serverStream.readLine();
      }
      catch (SocketException e) {
        // Can't connect to host, report it and wait before trying again
        System.out.println("Can't connect to host: " + e.getMessage());
        serverStream = null;
        try { Thread.sleep(5000); } catch (InterruptedException ignored) { }
      }
      catch (Exception e) {
        // Some other problem, report it and wait before trying again
        System.out.println("General exception: " +
          e.getClass().getName() + ": " + e.getMessage());
        try { Thread.sleep(1000); } catch (InterruptedException ignored) { }
      }
    }
    return nextMessage + "\n";
  }
  public boolean handleEvent(Event event) {
    switch (event.id) {
      case Event.ACTION_EVENT:
        if (event.target == input) {
          broadcastMessage(input.getText());
          input.setText("");
          return true;
        }
    }
    return false;
  }
  public void init() {
    // Check if this applet was loaded directly from the filesystem.
    // If so, explain to the user that this applet needs to be loaded
    // from a server in order to communicate with that server's servlets.
    URL codebase = getCodeBase();
    if (!"http".equals(codebase.getProtocol())) {
      System.out.println();
      System.out.println("*** Whoops! ***");
      System.out.println("This applet must be loaded from a web server.");
      System.out.println("Please try again, this time fetching the HTML");
      System.out.println("file containing this servlet as");
      System.out.println("\"http://server:port/file.html\".");
      System.out.println();
      System.exit(1);  // Works only from appletviewer
                       // Browsers throw an exception and muddle on
    }

    // Get this user's name from an applet parameter set by the servlet
    // We could just ask the user, but this demonstrates a
    // form of servlet->applet communication.
    user = getParameter("user");
    if (user == null) user = "anonymous";

    // Set up the user interface...
    // On top, a large TextArea showing what everyone's saying.
    // Underneath, a labeled TextField to accept this user's input.
    text = new TextArea();
    text.setEditable(false);
    label = new Label("Say something: ");
    input = new TextField();
    input.setEditable(true);

    setLayout(new BorderLayout());
    Panel panel = new Panel();
    panel.setLayout(new BorderLayout());

    add("Center", text);
    add("South", panel);

    panel.add("West", label);
    panel.add("Center", input);
  }
  public void run() {
    while (true) {
      text.appendText(getNextMessage());
    }
  }
  public void start() {
    thread = new Thread(this);
    thread.start();
  }
  public void stop() {
    thread.stop();
    thread = null;
  }
}
