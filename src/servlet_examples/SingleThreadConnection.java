package servlet_examples;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class SingleThreadConnection extends HttpServlet
                                    implements SingleThreadModel {

  Connection con = null;  // database connection, one per pooled instance

  public void destroy() {
    if (con != null) {
      try { con.close(); } catch (SQLException ignored) { }
    }
  }
  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain");
    PrintWriter out = res.getWriter();

    try {
      // Use the connection uniquely assigned to this instance
      Statement stmt = con.createStatement();

      // Update the database any number of ways

      // Commit the transaction
      con.commit();
    }
    catch (SQLException e) {
      try { con.rollback(); } catch (SQLException ignored) { }
    }
  }
  private Connection establishConnection() throws SQLException {
    // Not implemented.  See Chapter 9.
  	return con;
  }
  public void init() throws ServletException {
    // Establish the connection for this instance
    try {
      con = establishConnection();
      con.setAutoCommit(false);
    }
    catch (SQLException e) {
      throw new ServletException(e.getMessage());
    }
  }
}
