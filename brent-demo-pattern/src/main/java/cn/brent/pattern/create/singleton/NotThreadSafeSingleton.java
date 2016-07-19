package cn.brent.pattern.create.singleton;

/**
 * 非线程安全的单例
 */
public class NotThreadSafeSingleton {
	
	private static NotThreadSafeSingleton uniqueInstance;

	// other useful instance variables here

	private NotThreadSafeSingleton() {
	}

	public static NotThreadSafeSingleton getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new NotThreadSafeSingleton();
		}
		return uniqueInstance;
	}

	// other useful methods here
	public String getDescription() {
		return "I'm a not thread safe Singleton!";
	}
}
