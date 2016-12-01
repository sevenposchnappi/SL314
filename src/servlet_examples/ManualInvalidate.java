package servlet_examples;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ManualInvalidate extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    // Get the current session object, create one if necessary
    HttpSession session = req.getSession();

    // Invalidate the session if it's more than a day old or has been
    // inactive for more than an hour.
    if (!session.isNew()) {  // skip new sessions
      Date dayAgo = new Date(System.currentTimeMillis() - 24*60*60*1000);
      Date hourAgo = new Date(System.currentTimeMillis() - 60*60*1000);
      Date created = new Date(session.getCreationTime());
      Date accessed = new Date(session.getLastAccessedTime());

      if (created.before(dayAgo) || accessed.before(hourAgo)) {
        session.invalidate();
        session = req.getSession();  // get a new session
      }
    }

    // Continue processing...
  }
}
