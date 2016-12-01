package servlet_examples;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.Base64Decoder;

public class CustomAuth extends HttpServlet {

  Map<String,String> users = new Hashtable<String,String>();

  // This method checks the user information sent in the Authorization
  // header against the database of users maintained in the users Hashtable.
  protected boolean allowUser(String auth) throws IOException {
    if (auth == null) return false;  // no auth

    if (!auth.toUpperCase().startsWith("BASIC "))
      return false;  // we only do BASIC

    // Get encoded user and password, comes after "BASIC "
    String userpassEncoded = auth.substring(6);

    // Decode it, using any base 64 decoder (we use com.oreilly.servlet)
    String userpassDecoded = Base64Decoder.decode(userpassEncoded);

    // Check our user list to see if that user and password are "allowed"
    if ("allowed".equals(users.get(userpassDecoded)))
      return true;
    else
      return false;
  }
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    // Get Authorization header
    String auth = req.getHeader("Authorization");

    // Do we allow that user?
    if (!allowUser(auth)) {
      // Not allowed, so report he's unauthorized
      res.setHeader("WWW-Authenticate", "BASIC realm=\"users\"");
      res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
      // Could offer to add him to the allowed user list
    }
    else {
      // Allowed, so show him the secret stuff
      out.println("Top-secret stuff");
    }
  }
  public void init() throws ServletException {
    // Names and passwords are case sensitive!
    users.put("peter1:111", "allowed");
    users.put("peter2:222", "allowed");
    users.put("peter3:333", "allowed");
  }
}
