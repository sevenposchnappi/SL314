package servlet_examples;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

class ConnectionHolder implements HttpSessionBindingListener {
  private Connection con = null;

  public ConnectionHolder(Connection con) {
    // Save the Connection
    this.con = con;
    try {
      con.setAutoCommit(false);  // transactions can extend between web pages!
    }
    catch(SQLException e) {
      // Perform error handling
    }
  }
  public Connection getConnection() {
    return con;  // return the cargo
  }
  public void valueBound(HttpSessionBindingEvent event) {
    // Do nothing when added to a Session
  }
  public void valueUnbound(HttpSessionBindingEvent event) {
    // Roll back changes when removed from a Session
    // (or when the Session expires)
    try {
      if (con != null) {
        con.rollback();  // abandon any uncomitted data
        con.close();
      }
    }
    catch (SQLException e) {
      // Report it
    }
  }
}
