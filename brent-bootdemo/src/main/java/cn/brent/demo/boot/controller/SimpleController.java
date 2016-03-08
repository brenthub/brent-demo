package cn.brent.demo.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value ="/")
    @ResponseBody
    public String index(){
		logger.info("index...");
        return "hello world";
    }
	
	@RequestMapping(value ="/h")
	public String hello(){
		return "hello";
	}
	
	
	@RequestMapping(value ="/ajax")
	public String ajax(){
		return "hello";
	}
	
}
