package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;

class HandlerThread extends Thread {

  Socket s;

  public HandlerThread(Socket s) {
    this.s = s;
  }
  public void run() {
    try {
      // Print each byte as it comes in from the socket
      InputStream in = s.getInputStream();
      byte[] bytes = new byte[1];
      while ((in.read(bytes)) != -1) {
        System.out.print((char)bytes[0]);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
