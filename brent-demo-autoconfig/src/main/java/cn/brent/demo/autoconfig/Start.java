package cn.brent.demo.autoconfig;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Start {
	
	private static Logger logger=LoggerFactory.getLogger(Start.class);
	
	public static void main(String[] args) {
		Properties p=new Properties();
		try {
			p.load(Start.class.getResourceAsStream("/start.config"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(p.getProperty("jdbc.url", ""));
		logger.info(p.getProperty("jdbc.user", ""));
		logger.info(p.getProperty("jdbc.url", ""));
		logger.info("runing...");
	}
}
