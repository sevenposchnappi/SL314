package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;

public class HttpClient {

  public static void main(String[] args) {
    if (args.length < 2) {
      printUsage();
      return;
    }

    // Host is the first parameter, port is the second
    String host = args[0];
    int port;
    try {
      port = Integer.parseInt(args[1]);
    }
    catch (NumberFormatException e) {
      printUsage();
      return;
    }

    try {
      // Open a socket to the server
      Socket s = new Socket(host, port);

      // Start a thread to send keyboard input to the server
      new KeyboardInputManager(System.in, s).start();

      // Now print everything we receive from the socket 
      BufferedReader in = 
        new BufferedReader(new InputStreamReader(s.getInputStream()));
      String line;
      while ((line = in.readLine()) != null) {
        System.out.println(line);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private static void printUsage() {
    System.out.println("usage: java HttpClient host port");
  }
}
