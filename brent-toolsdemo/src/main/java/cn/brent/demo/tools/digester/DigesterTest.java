package cn.brent.demo.tools.digester;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.ExtendedBaseRules;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.xml.sax.SAXException;

public class DigesterTest {

	@Test
	public void test() throws IOException, SAXException{
		
		Digester dig=new Digester();
		
		//push 调用类
		dig.push(new XmlTest());
		
		//设置匹配规则处理类
		dig.setRules(new ExtendedBaseRules());
		
		//调用XmlTest setVersion()
		dig.addBeanPropertySetter("models/version");
		
		//遇到model结点时创建XmlModel对象 以下调用有顺序
		dig.addObjectCreate("models/model", XmlModel.class);
		
		//遇到model结点时，将子结点当属性
		dig.addBeanPropertySetter("models/model/?");
		
		dig.addBeanPropertySetter("models/model/biger", "big");
		
		//遇到model结点时，将xml结点属性当bean属性
		dig.addSetProperties("models/model");
		
		dig.addSetProperties("models/model", "biger", "big");
		
		//遇到model结点时，调用XmlTest的addModel方法（参数为XmlModel）
		dig.addSetNext("models/model", "addModel");
		
		XmlTest x =(XmlTest) dig.parse(getClass().getResourceAsStream("Test.xml"));
		
		System.out.println(x);
		
	}
	
	public static class XmlTest{
		private String version;
		List<XmlModel> list=new ArrayList<XmlModel>();
		
		public void addModel(XmlModel model){
			list.add(model);
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}
	}
	
	
	public static class XmlModel{
		private String name;
		private Integer age;
		private BigDecimal big;
		private Long lon;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public BigDecimal getBig() {
			return big;
		}
		public void setBig(BigDecimal big) {
			this.big = big;
		}
		public Long getLon() {
			return lon;
		}
		public void setLon(Long lon) {
			this.lon = lon;
		}
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
