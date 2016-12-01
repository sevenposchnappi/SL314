
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ServletContext_Set extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();

		ServletContext context = getServletContext();
        context.setAttribute("myName1","§d¥Ã§Ó1");
	
	}
}
