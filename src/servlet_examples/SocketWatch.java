package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketWatch {

  public static void main(String[] args) {
    if (args.length < 1) {
      printUsage();
      return;
    }

    // The first argument is the port to listen on
    int port;
    try {
      port = Integer.parseInt(args[0]);
    }
    catch (NumberFormatException e) {
      printUsage();
      return;
    }

    try {
      // Establish a server socket to accept client connections
      // As each connection comes in, pass it to a handler thread
      ServerSocket ss = new ServerSocket(port);
      while (true) {
        Socket request = ss.accept();
        new HandlerThread(request).start();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private static void printUsage() {
    System.out.println("usage: java SocketWatch port");
  }
}
