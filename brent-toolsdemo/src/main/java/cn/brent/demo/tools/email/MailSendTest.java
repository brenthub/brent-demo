package cn.brent.demo.tools.email;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.mail.Authenticator;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.resolver.DataSourceClassPathResolver;
import org.apache.commons.mail.resolver.DataSourceFileResolver;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.junit.Test;

public class MailSendTest {

	public void init(Email email) throws EmailException {
		Authenticator auth = new DefaultAuthenticator("***@163.com", "****");
		email.setAuthenticator(auth);
		email.setHostName("smtp.163.com");
		email.setSmtpPort(25);
		email.setFrom("*@163.com", "寒十八");
		email.setCharset("UTF-8");
		email.setSubject("祝你成事如意！");
		email.addTo("*@qq.com", "寒木");
		//email.addTo("xxx@xx.xx", "不存的人");
		
		//打印debug信息
		email.setDebug(true);
	}

	@Test
	public void simple() {
		SimpleEmail email = new SimpleEmail();
		try {
			init(email);
			email.setMsg("简单文本邮件");
			System.out.println(email.send());
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void html() throws IOException{
		HtmlEmail email=new HtmlEmail();
		
		try {
			init(email);
			email.setHtmlMsg(getHtmlTpl("test.html"));
			email.embed(new File("pom.xml"));
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	private String getHtmlTpl(String url){
		try {
			InputStream is=getClass().getResourceAsStream(url);
			ByteArrayOutputStream bo=new ByteArrayOutputStream();
			byte[]b=new byte[1024];
			int len;
			while((len=is.read(b))!=-1){
				bo.write(b,0,len);
			};
			return bo.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Test
	public void imageHtmlURL() throws IOException{
		ImageHtmlEmail email=new ImageHtmlEmail();
		try {
			init(email);
			URL url = new URL("http://www.gruntjs.net/");
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHtmlMsg(getHtmlTpl("grunt.html"));
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void imageHtmlClassPath() throws IOException{
		ImageHtmlEmail email=new ImageHtmlEmail();
		try {
			init(email);
			email.setDataSourceResolver(new DataSourceClassPathResolver("/cn/brent/demo/tools/email"));
			email.setHtmlMsg(getHtmlTpl("logo.html"));
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void imageHtmlFile() throws IOException{
		ImageHtmlEmail email=new ImageHtmlEmail();
		try {
			init(email);
			email.setDataSourceResolver(new DataSourceFileResolver(new File("src/main/java/cn/brent/demo/tools/email/")));
			email.setHtmlMsg(getHtmlTpl("logo.html"));
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	
}
