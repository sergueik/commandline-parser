package example;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.security.Permission;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * Unit Tests for Apache Commons CLI CommandLineParser
 * see https://stackoverflow.com/questions/309396/java-how-to-test-methods-that-call-system-exit
 * https://gist.github.com/nickname55/880addec70a8303b2359680376d5d066
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings({ "deprecation", "serial" })
public class CommonsCommandLineParserTest extends TestCase {

	private CommonsCommandLineParser sut;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		sut = new CommonsCommandLineParser();
		sut.saveFlagValue("d", "data");
		sut.saveFlagValue("o", "operation");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	// handling url
	public void test6() throws Exception {
		String data = "http://www.google.com";
		sut.run("-d", data, "-o", "operation");
		String value = sut.getFlagValue("d");
		assertThat(String.format("Unexpected value %s", value), value, is(data));
	}

	@Test
	// handling long option names
	// TODO: the option is not working ?
	public void test7() throws Exception {
		sut.run("--data", "data", "--o", "operation");
		String value = sut.getFlagValue("d");
		assertThat(String.format("Unexpected value %s", value), value, is("data"));
	}

	@Test
	public void test8() throws Exception {
		sut.run("--data", "data", "-d", "different data");
		String value = sut.getFlagValue("d");
		assertThat(String.format("Unexpected value %s", value), value, is("data"));
	}

	public void test9() throws Exception {
		sut.run("--data", "a,b,c,d,e,f", "--operaiton", "a,b,c,d,e,f");
		String[] v1 = sut.getFlagValue("d").split(",");
		String[] v2 = sut.getFlagValue("o").split(",");
		assertThat(v1.length == v2.length, is(true));
	}
}
