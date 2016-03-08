package cn.brent.demo.boot.web;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class DemoSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("session created...");
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
	}

	

}
