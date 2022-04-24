package example;

/**
 * Copyright 2020,2021 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import example.CommandLineParser;

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

@SuppressWarnings("deprecation")
public class FileArgParserTest {

	private static boolean debug = true;
	private static CommandLineParser commandLineParser;

	@BeforeClass
	public static void load() {
		commandLineParser = new CommandLineParser();
		commandLineParser.setDebug(debug);
	}

	@Ignore
	@Test
	public void argumentDataFileValueTest() {
		final String[] argsArray = new String[] { "-a", "@a.txt" };
		commandLineParser.saveFlagValue("a");
		commandLineParser.parse(argsArray);
		assertThat(commandLineParser.hasFlag("a"), is(true));
		assertThat(commandLineParser.getFlagValue("a"), is("foo,bar"));
	}

}
