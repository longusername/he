package listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import util.MyWebSocket;

public class CountListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		System.out.println("session´´½¨");
		ServletContext application = event.getSession().getServletContext();
		int num = 0;
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num++;
		application.setAttribute("num", num);
		MyWebSocket.sendMessageAll(String.valueOf(num));
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		System.out.println("sessionÏú»Ù");
		ServletContext application = event.getSession().getServletContext();
		int num = 0;
		if (application.getAttribute("num") != null) {
			num = (Integer) application.getAttribute("num");
		}
		num--;
		if (num < 0) {
			num = 0;
		}
		application.setAttribute("num", num);
		MyWebSocket.sendMessageAll(String.valueOf(num));
	}

}
