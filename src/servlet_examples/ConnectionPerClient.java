package servlet_examples;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

/* Actual Servlet */

public class ConnectionPerClient extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    HttpSession session = req.getSession(true);
    Connection con;

    // Synchronize: Without this two holders might be created for one client
    synchronized (session) {
      // Try getting the connection holder for this client
      ConnectionHolder holder =
        (ConnectionHolder) session.getAttribute("servletapp.connection");

      // Create (and store) a new connection and holder if necessary
      if (holder == null) {
        try {
          holder = new ConnectionHolder(DriverManager.getConnection(
            "jdbc:oracle:oci7:ordersdb", "user", "passwd"));
          session.setAttribute("servletapp.connection", holder);
        }
        catch (SQLException e) {
          log("Couldn't get db connection", e);
        }
      }

      // Get the actual connection from the holder
      con = holder.getConnection();
    }

    // Now use the connection
    try {
      Statement stmt = con.createStatement();
      stmt.executeUpdate(
        "UPDATE INVENTORY SET STOCK = (STOCK - 10) WHERE PRODUCTID = 7");
      stmt.executeUpdate(
        "UPDATE SHIPPING SET SHIPPED = (SHIPPED + 10) WHERE PRODUCTID = 7");

      // Charge the credit card and commit the transaction in another servlet
      res.sendRedirect(res.encodeRedirectURL(
        req.getContextPath() + "/servlet/CreditCardHandler"));
    }
    catch (Exception e) {
      // Any error is grounds for rollback
      try {
        con.rollback();
        session.removeAttribute("servletapp.connection");
      }
      catch (Exception ignored) { }
      out.println("Order failed. Please contact technical support.");
    }
  }
  public void init() throws ServletException {
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
    }
    catch (ClassNotFoundException e) {
      throw new UnavailableException("Couldn't load OracleDriver");
    }
  }
}
