
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Session_Set extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();
        session.setAttribute("myName2","§d¥Ã§Ó2");
	
	}
}
