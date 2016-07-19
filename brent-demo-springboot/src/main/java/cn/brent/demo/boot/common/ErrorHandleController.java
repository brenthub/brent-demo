package cn.brent.demo.boot.common;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

//@Controller
public class ErrorHandleController implements ErrorController {

	@Override
	public String getErrorPath() {
		
		return "/error";
	}

}
