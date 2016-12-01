package servlet_examples;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.RemoteDaemonHttpServlet;

// MessageSource acts as the source for new messages.
// Clients interested in receiving new messages can
// observe this object.
class MessageSource extends Observable {
  public void sendMessage(String message) {
    setChanged();
    notifyObservers(message);
  }
}
