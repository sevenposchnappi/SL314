package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.RemoteDaemonHttpServlet;

class DaytimeConnection extends Thread {

  DaytimeServlet servlet;
  Socket client;

  DaytimeConnection(DaytimeServlet servlet, Socket client) {
    this.servlet = servlet;
    this.client = client;
    setPriority(NORM_PRIORITY - 1);
  }
  public void run() {
    try {
      // Read the first line sent by the client
      DataInputStream in = new DataInputStream(
                           new BufferedInputStream(
                           client.getInputStream()));
      String line = in.readLine();

      // If it was "object" then return the Date as a serialized object
      if ("object".equals(line)) {
        ObjectOutputStream out =
          new ObjectOutputStream(client.getOutputStream());
        out.writeObject(servlet.getDate());
        out.close();
      }
      // Otherwise, send the Date as a normal string
      else {
        // Wrap a PrintStream around the Socket's OutputStream
        PrintStream out = new PrintStream(client.getOutputStream());
        out.println(servlet.getDate().toString());
        out.close();
      }

      // Be sure to close the connection
      client.close();
    }
    catch (IOException e) {
      servlet.getServletContext()
        .log(e, "IOException while handling client request");
    }
    catch (Exception e) {
      servlet.getServletContext()
        .log("Exception while handling client request");
    }
  }
}
