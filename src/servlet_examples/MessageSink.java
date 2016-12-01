package servlet_examples;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.RemoteDaemonHttpServlet;

// MessageSink acts as the receiver of new messages.
// It listens to the source.
class MessageSink implements Observer {

  String message = null;  // set by update() and read by getNextMessage()

  // Gets the next message sent out from the message source
  synchronized public String getNextMessage(MessageSource source) {
    // Tell source we want to be told about new messages
    source.addObserver(this);

    // Wait until our update() method receives a message
    while (message == null) {
      try { wait(); } catch (Exception ignored) { }
    }

    // Tell source to stop telling us about new messages
    source.deleteObserver(this);

    // Now return the message we received
    // But first set the message instance variable to null
    // so update() and getNextMessage() can be called again.
    String messageCopy = message;
    message = null;
    return messageCopy;
  }
  // Called by the message source when it gets a new message
  synchronized public void update(Observable o, Object arg) {
    // Get the new message
    message = (String)arg;

    // Wake up our waiting thread
    notify();
  }
}
