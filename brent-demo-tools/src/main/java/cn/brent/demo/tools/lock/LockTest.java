package cn.brent.demo.tools.lock;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class LockTest {

	private final Lock lock = new ReentrantLock();

	private final Condition addCondition = lock.newCondition();

	private final Condition subCondition = lock.newCondition();

	private static int num = 0;
	private List<String> lists = new LinkedList<String>();

	public void add() {
		lock.lock();

		try {
			while (lists.size() == 10) {// 当集合已满,则"添加"线程等待
				addCondition.await();
			}

			num++;
			lists.add(Thread.currentThread().getName());
			System.out.println("The Lists Size is " + lists.size());
			System.out.println("The Current Thread is " + Thread.currentThread().getName());
			System.out.println("==============================");
			this.subCondition.signal();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {// 释放锁
			lock.unlock();
		}
	}

	public void sub() {
		lock.lock();

		try {
			while (lists.size() == 0) {// 当集合为空时,"减少"线程等待
				subCondition.await();
			}

			String str = lists.get(0);
			lists.remove(0);
			System.out.println("The Token is [" + str + "]");
			System.out.println("The Current Thread is " + Thread.currentThread().getName());
			System.out.println("==============================");
			num--;
			addCondition.signal();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	@Test
	public void reentrantLockTest() {
		final ExecutorService exec = Executors.newFixedThreadPool(20);

		Random rd = new Random();

		final Runnable add = new Runnable() {
			public void run() {
				try {
					Thread.sleep(rd.nextInt(10000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				add();
			}
		};
		final Runnable notify = new Runnable() {
			public void run() {
				try {
					Thread.sleep(rd.nextInt(10000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				sub();
			}
		};
		for (int index = 0; index < 1; index++) {
			exec.submit(add);
			exec.submit(notify);
		}
		synchronized (getClass()) {
			try {
				getClass().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void reentrantLockTest2() {
		final ExecutorService exec = Executors.newFixedThreadPool(20);

		final Condition con = lock.newCondition();

		final Runnable await = new Runnable() {
			public void run() {
				lock.lock();
				try {
					con.await();
					System.out.println("end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}

			}
		};
		final Runnable notify = new Runnable() {
			public void run() {
				lock.lock();
				try {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
					}
					System.out.println("notify");
					con.signal();
					System.out.println("notify end");
				} finally {
					lock.unlock();
				}
			}
		};
		exec.submit(await);
		exec.submit(notify);
		synchronized (getClass()) {
			try {
				getClass().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void reentrantLockTest3() {
		final ExecutorService exec = Executors.newFixedThreadPool(20);

		final Runnable wait = new Runnable() {
			public void run() {
				lock.lock();
				try {
					this.getClass().wait();
					System.out.println("end");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}

			}
		};
		final Runnable getlock = new Runnable() {
			public void run() {
				lock.lock();
				try {
					System.out.println("notify end");
				} finally {
					lock.unlock();
				}
			}
		};
		exec.submit(wait);
		exec.submit(getlock);
		synchronized (getClass()) {
			try {
				getClass().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void reentrantLockTest4() {
		final ExecutorService exec = Executors.newFixedThreadPool(20);

		final Runnable wait = new Runnable() {
			public void run() {
				lock.lock();
				lock.lock();
				try {
					//this.getClass().wait();
					System.out.println("end");
				}  finally {
					lock.unlock();
//					lock.unlock();
				}

			}
		};
		final Runnable getlock = new Runnable() {
			public void run() {
				lock.lock();
				try {
					System.out.println("notify end");
				} finally {
					lock.unlock();
				}
			}
		};
		exec.submit(wait);
		exec.submit(getlock);
		synchronized (getClass()) {
			try {
				getClass().wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}
