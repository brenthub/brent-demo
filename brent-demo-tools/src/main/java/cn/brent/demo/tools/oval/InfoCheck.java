package cn.brent.demo.tools.oval;

import net.sf.oval.constraint.CheckWithCheck.SimpleCheck;

public class InfoCheck implements SimpleCheck {

	/** */
	private static final long serialVersionUID = 2859025034219420033L;

	@Override
	public boolean isSatisfied(Object validatedObject, Object value) {
		
		OvalMo oval=(OvalMo)validatedObject;
		
		System.out.println(oval.getName());
		System.out.println(value);
		
		return true;
	}

}
