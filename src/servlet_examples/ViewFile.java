/*
   測試:   http://localhost:8081/SL314/ViewFile/images/tomcat.gif
   應注意     (※1)注意當有用到【額外路徑資訊】時必須使用【前置路徑對應】的設定
   同時注意(※2)web.xml內的<url-pattern>是<url-pattern>/ViewFile/*</url-pattern>                        
*/

package servlet_examples;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.oreilly.servlet.ServletUtils;

public class ViewFile extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    
    // Use a ServletOutputStream because we may pass binary information
    ServletOutputStream out = res.getOutputStream();

    // Get the file to view
    String file = req.getPathTranslated();

    // No file, nothing to view
    if (file == null) {
      out.println("No file to view");
      return;
    }

    // Get and set the type of the file
    String contentType = getServletContext().getMimeType(file);
    res.setContentType(contentType);

    // Return the file
    try {
      ServletUtils.returnFile(file, out);
    }
    catch (FileNotFoundException e) {
      out.println("File not found");
    }
    catch (IOException e) {
      out.println("Problem sending file: " + e.getMessage());
    }
  }
}
