/*
   ����:   http://localhost:8081/SL314/ViewFile/images/tomcat.gif
   ���`�N     (��1)�`�N���Ψ�i�B�~���|��T�j�ɥ����ϥΡi�e�m���|�����j���]�w
   �P�ɪ`�N(��2)web.xml����<url-pattern>�O<url-pattern>/ViewFile/*</url-pattern>                        
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
