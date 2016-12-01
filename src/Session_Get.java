
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Session_Get extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("text/html; charset=Big5");
		PrintWriter out = res.getWriter();

		HttpSession session = req.getSession();
		Object myName2 = session.getAttribute("myName2");
		out.println(myName2);
	}
}
