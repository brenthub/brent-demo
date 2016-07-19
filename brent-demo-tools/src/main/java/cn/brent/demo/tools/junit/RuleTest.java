package cn.brent.demo.tools.junit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.rules.Verifier;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RuleTest {

	/**
	 * TemporaryFolder 创建临时文件或者临时目录，当测试结束后，框架会自动删除
	 */
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testUsingTempFolder() throws IOException {
		File createdFile = folder.newFile("myfile.txt");
		File createdFolder = folder.newFolder("subfolder");
	}

	/**
	 * 外部资源如文件、socket、服务、数据库的连接与关闭
	 */
	@Rule
	public ExternalResource resource = new ExternalResource() {
		/**
		 * 每个测试之前处理
		 */
		@Override
		protected void before() throws Throwable {
			System.out.println("ExternalResource before");
		};

		/**
		 * 每个测试之后处理
		 */
		@Override
		protected void after() {
			System.out.println("ExternalResource after");
		};

	};

	@Test
	public void testU() throws IOException {

	}

	/**
	 * 在出现一个错误后，还可以让测试继续进行下去
	 */
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	/**
	 * 此方法会报告两次失败（普通的断言在第一次出错误后直接返回）
	 */
	@Test
	public void testMultiplesOf2() {
		errorCollector.checkThat(2 * 0, is(2));
		errorCollector.checkThat(3 * 0, is(2));
	}

	/**
	 * 此方法会报告一次失败，在第一次断言失败后就直接返回了
	 */
	@Test
	public void testCallableMultiples() {
		errorCollector.checkSucceeds(new Callable<Object>() {
			public Object call() throws Exception {
				assertThat(2 * 2, is(3));
				System.out.println("i running?");
				assertThat(2 * 3, is(6));
				assertThat(2 * 4, is(8));
				assertThat(2 * 5, is(9));
				return null;
			}
		});
	}

	/**
	 * 收集异常，结果仍为失败
	 */
	@Test
	public void testAddingAnError() {
		errorCollector.addError(new Throwable("Error Collector added an error"));
		errorCollector.addError(new Throwable("Error Collector added a second error"));
	}

	private List<String> errorLog = new ArrayList<String>();

	/**
	 * 每个测试之后(在@After之后)，进行检查
	 */
	@Rule
	public Verifier collector = new Verifier() {
		@Override
		protected void verify() {
			assertTrue(errorLog.isEmpty());
		}
	};

	/**
	 * 此方法会失败（collector的检查不通过）
	 */
	@Test
	public void example() {
		errorLog.add("aa");
	}
	
	/**
	 * 对测试的每个步骤进行监控。
	 */
	@Rule
	public TestRule watchman = new TestWatcher() {
		@Override
		public Statement apply(Statement base, Description description) {
			Statement s = super.apply(base, description);
			System.out.println("watch apply.");
			return s;
		}

		@Override
		protected void succeeded(Description description) {
			String watchedLog = description.getDisplayName() + " " + "success!";
			System.out.println("watch succeed:" + watchedLog);

		}

		@Override
		protected void failed(Throwable e, Description description) {
			String watchedLog = description.getDisplayName() + " " + e.getClass().getSimpleName();
			System.out.println("watch failed:" + watchedLog);

		}

		@Override
		protected void starting(Description description) {
			super.starting(description);
			System.out.println("watch starting.");
		}

		@Override
		protected void finished(Description description) {
			super.finished(description);
			System.out.println("watch finished.");
		}
	};

}
