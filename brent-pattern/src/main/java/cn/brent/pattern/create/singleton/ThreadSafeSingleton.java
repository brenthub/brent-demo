package cn.brent.pattern.create.singleton;

/**
 * 线程安全的单例
 */
public class ThreadSafeSingleton {
	
	private volatile static ThreadSafeSingleton uniqueInstance;

	// other useful instance variables here

	private ThreadSafeSingleton() {
	}

	public static synchronized ThreadSafeSingleton getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ThreadSafeSingleton();
		}
		return uniqueInstance;
	}
	
	public static ThreadSafeSingleton getInstance2() {
		if (uniqueInstance == null) {
			synchronized (ThreadSafeSingleton.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new ThreadSafeSingleton();
				}
			}
		}
		return uniqueInstance;
	}

	// other useful methods here
	public String getDescription() {
		return "I'm a thread safe Singleton!";
	}
}
