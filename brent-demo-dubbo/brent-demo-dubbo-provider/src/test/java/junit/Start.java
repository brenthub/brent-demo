package junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
	
	private static Logger logger= LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) throws InterruptedException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
		context.start();
		logger.info("running");
		synchronized (Start.class) {
			Start.class.wait();
		}
		
	}
}
