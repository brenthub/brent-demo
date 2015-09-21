package cn.brent.demo.jfinal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.Controller;

public class BaseController extends Controller {
	
	protected Logger logger=LoggerFactory.getLogger(getClass());
	
}
