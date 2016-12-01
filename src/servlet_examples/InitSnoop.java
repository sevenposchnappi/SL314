package servlet_examples;

import java.io.*;
import java.util.*;
import javax.servlet.*;

public class InitSnoop extends GenericServlet {

  // No init() method needed

  public void service(ServletRequest req, ServletResponse res)
                             throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    out.println("Init Parameters:");
    Enumeration en = getInitParameterNames();
    while (en.hasMoreElements()) {
      String name = (String) en.nextElement();
      out.println(name + ": " + getInitParameter(name));
    }
  }
}
