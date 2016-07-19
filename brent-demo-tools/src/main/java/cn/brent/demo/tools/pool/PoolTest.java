package cn.brent.demo.tools.pool;

import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.pool.KeyedObjectPool;
import org.apache.commons.pool.KeyedPoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericKeyedObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import org.apache.commons.pool.impl.StackObjectPoolFactory;
import org.junit.Test;

public class PoolTest {

	@Test
	public void testGenericKeyedObject() {
		KeyedPoolableObjectFactory<String,TestObject> factory = createKeyedPoolableObjectFactory();
		GenericKeyedObjectPoolFactory<String,TestObject> fc = new GenericKeyedObjectPoolFactory<String,TestObject>(factory);
		KeyedObjectPool<String,TestObject> keyPool = fc.createPool();
		testKeyedPool(keyPool);
	}

	private KeyedPoolableObjectFactory<String, TestObject> createKeyedPoolableObjectFactory() {
		return new KeyedPoolableObjectFactory<String,TestObject>() {
			/**
			 * 创建一个实例到对象池
			 */
			@Override
			public TestObject makeObject(String key) throws Exception {
				System.out.println("makeObject!");
				return new TestObject();
			}

			/**
			 * 销毁实例
			 */
			@Override
			public void destroyObject(String key,TestObject obj) throws Exception {
				System.out.println("destroyObject!");
				obj = null;
			}

			/**
			 * 激活实例（每次borrowObject都会调用，不管是不是新创建的）
			 */
			@Override
			public void activateObject(String key,TestObject obj) throws Exception {
				System.out.println("activateObject!");
				obj.setActive(true);
				obj.addNum();
			}

			/**
			 * 释放对象到池
			 */
			@Override
			public void passivateObject(String key,TestObject obj) throws Exception {
				System.out.println("passivateObject!");
				obj.setActive(false);
			}

			/**
			 * 检验对象
			 */
			@Override
			public boolean validateObject(String key,TestObject obj) {
				System.out.println("validateObject!");
				return obj.isActive();
			}
		};
	}

	@Test
	public void testStackBaseObject(){
		PoolableObjectFactory<TestObject> factory = createPoolableObjectFactory();
		StackObjectPoolFactory<TestObject> sfc=new StackObjectPoolFactory<TestObject>(factory, 5, 5);
		//StackObjectPool<TestObject> spool=new StackObjectPool<PoolTest.TestObject>(factory, 10, 10)
		/**
		 * 特点：
		 * 1、参数少，只有两个参数最大空闲（默认8）和初始容量（默认4）
		 * 2、后入先出
		 * 3、borrow和return之后都会测试
		 * 4、初始容量不会限制池的大小，池的大小没有上限
		 * 5、超出最大空闲，将会销毁
		 */
		ObjectPool<TestObject> pool=sfc.createPool();
		testPool(pool);
	}
	
	private PoolableObjectFactory<TestObject> createPoolableObjectFactory(){
		return new PoolableObjectFactory<TestObject>() {
			/**
			 * 创建一个实例到对象池
			 */
			@Override
			public TestObject makeObject() throws Exception {
				System.out.println("makeObject!");
				return new TestObject();
			}

			/**
			 * 销毁实例
			 */
			@Override
			public void destroyObject(TestObject obj) throws Exception {
				System.out.println("destroyObject!");
				obj = null;
			}

			/**
			 * 激活实例（每次borrowObject都会调用，不管是不是新创建的）
			 */
			@Override
			public void activateObject(TestObject obj) throws Exception {
				System.out.println("activateObject!");
				obj.setActive(true);
				obj.addNum();
			}

			/**
			 * 释放对象到池
			 */
			@Override
			public void passivateObject(TestObject obj) throws Exception {
				System.out.println("passivateObject!");
				obj.setActive(false);
			}

			/**
			 * 检验对象
			 */
			@Override
			public boolean validateObject(TestObject obj) {
				System.out.println("validateObject!");
				return obj.isActive();
			}
		};
	}
	
	private void testPool(ObjectPool<TestObject> pool){
		final Random r=new Random();
		try {
			for (int i = 1; i < 100; i++) {
				System.out.println("============"+i+"============");
				System.out.println("borrow前 空闲数量pool.getNumIdle()：" + pool.getNumIdle());
				System.out.println("borrow前 活跃数量pool.getNumActive()：" + pool.getNumActive());
				
				Thread.sleep(2000);
				
				TestObject bo =pool.borrowObject();
				System.out.println("bo:" + bo);
				if(r.nextInt(10)<5){
					pool.returnObject(bo);
					System.out.println("return后 bo:"+bo);
					System.out.println("return后 空闲数量pool.getNumIdle()：" + pool.getNumIdle());
					System.out.println("return后 活跃数量pool.getNumActive()：" + pool.getNumActive());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pool.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void testKeyedPool(KeyedObjectPool<String,TestObject> keyPool){
		final Random r=new Random();
		try {  
            //这里添加池对象，只需要传入key就会默认调用makeObject()方法创建一个对象  
            keyPool.addObject("一级");  
            keyPool.addObject("二级");  
            //这里注释掉，不初始创建这个键的池对象  
            //keyPool.addObject("三级");  
            System.out.println("池中处于闲置状态的实例pool.getNumIdle()："+keyPool.getNumIdle());  
            for (int i = 0; i < 5; i++) {  
                //从池里取对象  
            	TestObject bo = keyPool.borrowObject("一级");  
                System.out.println("一级"+i+"-------"+bo+"-------"+bo.getNum());  
                  
                TestObject bo1 = keyPool.borrowObject("二级");  
                System.out.println("二级"+i+"-------"+bo1+"-------"+bo1.getNum());  
                //上边注释掉的那行代码，这里取对象的时候如果没有闲置对象，也会默认去创建一个key="三级"的池对象  
                TestObject bo2 = keyPool.borrowObject("三级");  
                System.out.println("三级"+i+"-------"+bo2+"-------"+bo2.getNum());  
                  
                if(i<3) {  
                    //用完之后归还对象  
                    keyPool.returnObject("一级", bo);  
                    keyPool.returnObject("二级", bo1);  
                    keyPool.returnObject("三级", bo2);  
                    System.out.println("归还对象！！！");  
                }  
            }  
            //当前池里的实例数量  
            System.out.println("池中所有在用实例pool.getNumActive()："+keyPool.getNumActive());  
            //当前池里的处于闲置状态的实例  
            System.out.println("池中处于闲置状态的实例pool.getNumIdle()："+keyPool.getNumIdle());  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {
			try {
				keyPool.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testGenericBaseObject() {
		PoolableObjectFactory<TestObject> factory = createPoolableObjectFactory();
		Config config = new Config();

		// 最大活跃，当活跃达到此值时不再创建，等待释放或直接异常参照whenExhaustedAction，默认8
		config.maxActive = 10;
		// 最大空闲，默认8，超过的空闲对象将被销毁，如果设置为负数表示不限制
		config.maxIdle = 5;
		// 指明若在对象池空时调用borrowObject方法的行为被设定成等待(whenExhaustedAction=WHEN_EXHAUSTED_BLOCK)时的等待时间
		// (毫秒)，
		// 如果等待时间超过了这个数值，则会抛出一个java.util.NoSuchElementException异常。
		// 默认一直等待(-1)
		config.maxWait = -1;
		// 最小空闲,当空闲数小于等于这个数，会创建一个新对象，默认0
		// 当大于0时，第一次borrow时会依次创建若干个对象（调用makeObject和passivateObject方法）
		config.minIdle = 1;
		// 借用之前先有效性校验，如果不通过，将试图另借，默认false
		config.testOnBorrow = false;
		// 返回之前先有效性校验，默认false
		config.testOnReturn = false;

		// 是否后进先出，默认为true(即：后进先出)
		config.lifo = false;
		
		// 当池枯竭(活动对象数达到最大值)时采取的动作
		// WHEN_EXHAUSTED_BLOCK（等待空闲） 默认为此值
		// WHEN_EXHAUSTED_FAIL(抛出一个java.util.NoSuchElementException异常)
		// 这样会使maxWait参数失去意义
		// WHEN_EXHAUSTED_GROW（创建一个对象，不过这就使maxActive和maxTotal参数失去了意义）
		config.whenExhaustedAction = GenericKeyedObjectPool.WHEN_EXHAUSTED_BLOCK;

		// 设定间隔每过多少毫秒进行一次后台对象清理的行动。如果这个值不是正数，则实际上不会进行后台对象清理
		// 默认-1
		config.timeBetweenEvictionRunsMillis = 3000;

		// 设定在进行后台对象清理时，每次检查几个对象。如果这个值不是正数，则每次检查的对象数是检查时池内对象的总数乘以这个值的负倒数再向上取整的结果
		// 也就是说，如果这个值是-2的话，那么每次大约检查当时池内对象总数的1/2左右，默认3
		config.numTestsPerEvictionRun = -2;

		// 设定在进行后台对象清理时，视空闲时间超过了多少毫秒的对象为过期。过期的对象将被回收(destroyObject)。
		// 如果这个值不是正数，那么对空闲时间没有特别的约束。
		// 默认1800000 (30分钟)
		config.minEvictableIdleTimeMillis = 1000;

		// 设定在进行后台对象清理时，是否对空闲的对象进行有效性检查。不能通过有效性检查的对象将被回收（destroyObject）。
		config.testWhileIdle = false;

		// ObjectPool<TestObject> pool=new
		// GenericObjectPool<TestObject>(factory,config);
		GenericObjectPoolFactory<TestObject> fc = new GenericObjectPoolFactory<PoolTest.TestObject>(factory, config);
		
		final ObjectPool<TestObject> pool = fc.createPool();
		
		testPool(pool);

	}

	public static class TestObject {
		// 记录从池中取出次数
		private int num;
		private boolean active;

		public TestObject() {
			active = true;
			num = 0;
		}

		public int getNum() {
			return num;
		}

		public void addNum() {
			this.num++;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
