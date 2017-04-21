package cn.brent.demo.tools.oval;

import net.sf.oval.constraint.CheckWith;
import net.sf.oval.constraint.NotEmpty;

public class OvalMo {

	private String id;
	
	@NotEmpty
	private String name;
	@CheckWith(value = InfoCheck.class)
	private String info;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
