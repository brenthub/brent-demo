package cn.brent.demo.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@Configuration//配置控制  
//@EnableAutoConfiguration//启用自动配置  
//@ComponentScan//组件扫描  
@SpringBootApplication // 代替上边三个注解
@ServletComponentScan
public class Application {

	public static void main(String[] args) {
		SpringApplication app=new SpringApplication(Application.class);
		//设置banner打印位置（关闭、控制台、日志）
		app.setBannerMode(Banner.Mode.LOG);
		app.run(args);
	}

}
