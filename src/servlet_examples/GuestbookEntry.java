package servlet_examples;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.CacheHttpServlet;

class GuestbookEntry {
  public String name;
  public String email;
  public String comment;
}
