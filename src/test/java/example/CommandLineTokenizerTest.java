package example;

import static org.hamcrest.CoreMatchers.containsString;

/**
 * Copyright 2023 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit Tests for CommandLinetokenizer
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

public class CommandLineTokenizerTest {

	private static boolean debug = true;
	private static CommandLineTokenizer commandLineTokenizer;
	private String[] args = {};
	private String commandline = null;

	// stop keeping it
	@Before
	public void load() {
		commandline = "name1=\"a test\" name2=\"another test with trailing space \" name3=value name4=true";
		commandLineTokenizer = new CommandLineTokenizer(commandline);
	}

	@Test
	public void test() {
		commandLineTokenizer.split();
		args = commandLineTokenizer.getArgs();
		assertThat(args, notNullValue());
		assertThat(args.length, is(4));
		assertThat(args[0], containsString(" "));
		String arg = args[1];
		assertThat(arg.substring(arg.length() - 1), is(" "));
		System.err.println(Arrays.asList(args));
	}
}
