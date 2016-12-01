package servlet_examples;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ClientPull extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    res.setHeader("Refresh", "10");
    out.println(new java.util.Date());
  }
}
