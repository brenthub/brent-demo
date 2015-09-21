package cn.brent.demo.jfinal.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

import cn.brent.demo.jfinal.BaseController;
import cn.brent.toolbox.web.model.JsonReturn;

import com.jfinal.ext.route.ControllerBind;

@ControllerBind(controllerKey = "/", viewPath="/")
public class MainAction extends BaseController {

	public void index(){
		getRequest().setAttribute("title", "我是index");
		render("index.html");
	}
	
	public void login(){
		render("login.html");
	}
	
	public void dologin(){
		try {
			SecurityUtils.getSubject().login(new UsernamePasswordToken(getPara("name"), getPara("pwd"), false));
		} catch (Exception e) {
			renderJson(JsonReturn.fail(e.getMessage()));
			return;
		}
		renderJson(JsonReturn.ok());
	}
	
	public void logout(){
		SecurityUtils.getSubject().logout();
		renderJson(JsonReturn.ok());
	}
	
}
