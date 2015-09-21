package cn.brent.demo.jfinal;

import org.apache.commons.lang3.StringUtils;

import cn.brent.jfinal.handler.RequestContextHandler;
import cn.brent.jfinal.render.FtlRender;
import cn.brent.jfinal.route.AutoBindRoutes;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.IErrorRenderFactory;
import com.jfinal.render.Render;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class Config extends JFinalConfig {

	private Routes routes;

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", false));
		
		me.setBaseViewPath("/WEB-INF/templates");
		
		me.setViewType(ViewType.FREE_MARKER);

		me.setErrorRenderFactory(new IErrorRenderFactory() {
			@Override
			public Render getRender(int errorCode, String view) {
				FtlRender ren=new FtlRender("/WEB-INF/templates/500.html");
				ren.setAttr("code", errorCode);
				String msg;
				if (errorCode == 401 ) {
					msg="没有登录";
				} else if (errorCode == 403) {
					msg="没有权限。";
				} else if (errorCode == 404) {
					msg="URL不存在。";
				} else if (errorCode == 500) {
					if(StringUtils.isNotEmpty(view)){
						msg=view;
					}else{
						msg = "系统异常，请联系管理员。";
					}
				} else {
					msg ="未知异常";
				}
				ren.setAttr("errorMsg", msg);
				return ren;
			}
		});
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		routes = me;
		AutoBindRoutes r=new AutoBindRoutes("cn.brent.demo.jfinal.action").suffix("Action");
		routes.add(r);
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {

		// shiro权限管理
		ShiroPlugin shiro = new ShiroPlugin(routes);
		me.add(shiro);

		// 复杂SQL用xml管理 获取：SqlKit.sql("xx.xx")
		SqlInXmlPlugin sqlInXml = new SqlInXmlPlugin();
		me.add(sqlInXml);

		// 缓存插件
		EhCachePlugin ehcahe = new EhCachePlugin();
		me.add(ehcahe);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new RequestContextHandler());
	}

}
