package servlet_examples;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.MailMessage;
import com.oreilly.servlet.ParameterParser;
import com.oreilly.servlet.ServletUtils;

public class MailServlet extends HttpServlet {

  static final String FROM = "MailServlet";
  static final String TO = "feedback-folks@attentive-company.com";

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    ParameterParser parser = new ParameterParser(req);
    String from = parser.getStringParameter("from", FROM);
    String to = parser.getStringParameter("to", TO);

    try {
      MailMessage msg = new MailMessage();  // assume localhost
      msg.from(from);
      msg.to(to);
      msg.setSubject("Customer feedback");

      PrintStream body = msg.getPrintStream();

      Enumeration en = req.getParameterNames();
      while (en.hasMoreElements()) {
        String name = (String)en.nextElement();
        if (name.equals("to") || name.equals("from")) continue; // Skip to/from
        String value = parser.getStringParameter(name, null);
        body.println(name + " = " + value);
      }

      body.println();
      body.println("---");
      body.println("Sent by " + HttpUtils.getRequestURL(req));

      msg.sendAndClose();

      out.println("Thanks for the submission...");
    }
    catch (IOException e) {
      out.println("There was a problem handling the submission...");
      log("There was a problem sending email", e);
    }
  }
}
