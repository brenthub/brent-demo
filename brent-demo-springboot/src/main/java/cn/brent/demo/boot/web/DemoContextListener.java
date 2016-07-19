package cn.brent.demo.boot.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DemoContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("context Initialized...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
