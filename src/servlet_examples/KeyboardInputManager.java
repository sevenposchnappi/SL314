package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;

class KeyboardInputManager extends Thread {

  InputStream in;
  Socket s;

  public KeyboardInputManager(InputStream in, Socket s) {
    this.in = in;
    this.s = s;
    setPriority(MIN_PRIORITY);  // socket reads should have a higher priority
                                // Wish I could use a select() !
    setDaemon(true);  // let the app die even when this thread is running
  }
  public void run() {
    try {
      BufferedReader keyb = new BufferedReader(new InputStreamReader(in));
      PrintWriter server = new PrintWriter(s.getOutputStream());

      String line;
      System.out.println("Connected... Type your manual HTTP request");
      System.out.println("------------------------------------------");
      while ((line = keyb.readLine()) != null) {
        server.print(line);
        server.print("\r\n");  // HTTP lines end with \r\n
        server.flush();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
