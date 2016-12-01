package servlet_examples;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class ScheduleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Timer timer = new Timer();	
	
    public ScheduleServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		doGet(request, response);
//	}
	/**
	public void destroy() {		//清除timer。
		
		timer.cancel();
	}
	**/
	
	public void init() throws ServletException{
		
		MyTimerTask timerTask = new MyTimerTask();
		GregorianCalendar setTime = new GregorianCalendar(2016,11,1,0,0,0);
		Date getSetTime = setTime.getTime();	
		long period = 1*60*60*1000;
		
		timer.scheduleAtFixedRate(timerTask,getSetTime,1000);
	}

}


class MyTimerTask extends TimerTask{	//繼承TimerTask，改寫run()方法。
	public void run(){
		System.out.println("xxxxx3xxx");
	}
	
}
