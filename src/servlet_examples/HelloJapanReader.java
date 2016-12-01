package servlet_examples;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloJapanReader extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/plain; charset=Shift_JIS");
    PrintWriter out = res.getWriter();
    res.setHeader("Content-Language", "ja");

    Locale locale = new Locale("ja", "");
    DateFormat full = DateFormat.getDateTimeInstance(DateFormat.LONG,
                                                     DateFormat.LONG,
                                                     locale);
    out.println("In Japanese:");

    try {
      FileInputStream fis =
        new FileInputStream(req.getRealPath("/HelloWorld.ISO-2022-JP"));
      InputStreamReader isr = new InputStreamReader(fis, "ISO-2022-JP");
      BufferedReader reader = new BufferedReader(isr);
      String line = null;
      while ((line = reader.readLine()) != null) {
        out.println(line);
      }
    }
    catch (FileNotFoundException e) {
      // No Hello for you
    }

    out.println(full.format(new Date()));
  }
}
