
import java.io.*;                                                    
import java.util.*;                                                  
import javax.servlet.*;                                              
                                                                     


public class ServerSnoop_Tempdir extends GenericServlet {

	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		res.setContentType("text/plain; charset=Big5");
		PrintWriter out = res.getWriter();

		out.println("�ڪ�server container ���Ȧs�ؿ���");
		ServletContext context = getServletContext();
		out.println(context.getAttribute("javax.servlet.context.tempdir"));
	}
}