package cn.brent.demo.boot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{

	private Logger logger=LoggerFactory.getLogger(ApplicationStartup.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		logger.info("Application Startup...");
		System.out.println("#########Application Startup###########");
	}

}
