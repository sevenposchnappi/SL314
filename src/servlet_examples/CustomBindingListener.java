package servlet_examples;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

class CustomBindingListener implements HttpSessionBindingListener {

  // Save a ServletContext to be used for its log() method
  ServletContext context;

  public CustomBindingListener(ServletContext context) {
    this.context = context;
  }
  public void valueBound(HttpSessionBindingEvent event) {
    context.log("[" + new Date() + "] BOUND as " + event.getName() +
                " to " + event.getSession().getId());
    System.out.println("aaaaaaaaaaaaaaaaaa");
  }
  public void valueUnbound(HttpSessionBindingEvent event) {
    context.log("[" + new Date() + "] UNBOUND as " + event.getName() +
                " from " + event.getSession().getId());
    System.out.println("bbbbbbbbbbbbbbbbbb");
  }
}
