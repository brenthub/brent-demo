package cn.brent.pattern.create.singleton;

/**
 * 静态变量初始化的单例
 */
public class StaticSingleton {
	
	private static StaticSingleton uniqueInstance=new StaticSingleton();

	private StaticSingleton() {
	}

	public static StaticSingleton getInstance() {
		return uniqueInstance;
	}

	// other useful methods here
	public String getDescription() {
		return "I'm a statically initialized Singleton!";
	}
}
