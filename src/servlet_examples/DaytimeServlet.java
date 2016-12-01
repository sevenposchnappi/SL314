package servlet_examples;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.RemoteDaemonHttpServlet;

public class DaytimeServlet extends RemoteDaemonHttpServlet
                            implements DaytimeServer {

  public void destroy() {
    // If you override destroy() you also have to call super.destroy()
    super.destroy();
  }
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    // If the client says "format=object" then
    // send the Date as a serialized object
    if ("object".equals(req.getParameter("format"))) {
      ObjectOutputStream out = new ObjectOutputStream(res.getOutputStream());
      out.writeObject(getDate());
    }
    // Otherwise send the Date as a normal ASCII string
    else {
      PrintWriter out = res.getWriter();
      out.println(getDate().toString());
    }
  }
  public void doPost(HttpServletRequest req, HttpServletResponse res)
                                throws ServletException, IOException {
    doGet(req, res);
  }
  // The single method from DaytimeServer
  public Date getDate() {
    return new Date();
  }
  // Handle a client's socket connection by spawning a DaytimeConnection
  // thread.
  public void handleClient(Socket client) {
    new DaytimeConnection(this, client).start();
  }
  public void init(ServletConfig config) throws ServletException {
    // As before, if you override init() you have to call super.init()
    super.init(config);
  }
}
