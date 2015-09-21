package cn.brent.demo.jfinal.action;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;

import cn.brent.demo.jfinal.BaseController;
import cn.brent.toolbox.web.model.JsonReturn;


public class TestAction extends BaseController{

	public void test(){
		render("test.html");
	}
	
	@RequiresUser
	public void testUser(){
		setAttr("name", SecurityUtils.getSubject().getPrincipal());
		render("test.html");
	}
	
	@RequiresRoles(value = { "RoleA" })
	public void testRole(){
		setAttr("name", SecurityUtils.getSubject().getPrincipal());
		render("test.html");
	}
	
	@RequiresPermissions(value = { "aPem" })
	public void testPem(){
		renderJson(JsonReturn.ok("aa"));
	}
	
	public void testError(){
		renderError(500, "Test Error");
	}
}
