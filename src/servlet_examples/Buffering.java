package servlet_examples;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Buffering extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setBufferSize(8 * 1024); // 8K buffer
    res.setContentType("text/html");
    PrintWriter out = res.getWriter();

    int size = res.getBufferSize(); // returns 8192 or greater

    // Record the default size, in the log
    log("The default buffer size is " + size);

    out.println("The client won't see this");
    res.reset();
    out.println("Nor will the client see this!");
    res.reset();
    out.println("And this won't be seen if sendError() is called");
    if (req.getParameter("important_parameter") == null) {
      res.sendError(HttpServletResponse.SC_BAD_REQUEST, "important_parameter needed");
    }
  }
}
