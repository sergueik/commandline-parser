package example;

/**
 * Copyright 2023 Serguei Kouzmine
 */

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.hasItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import example.CommandLineParser;

/**
 * Unit Tests for CommandLineParser
 * 
 * @author: Serguei Kouzmine (kouzmine_serguei@yahoo.com)
 */

// https://stackoverflow.com/questions/3366281/tokenizing-a-string-but-ignoring-delimiters-within-quotes
// see also:
// http://www.java2s.com/example/java-utility-method/string-split-by-quote/split-string-str-char-chrsplit-char-chrquote-fbd19.html
// https://www.baeldung.com/java-split-string-commas
public class TokenizerTest {

	private static boolean debug = true;
	private String[] args = {};
	private String arg = null;

	// stop keeping it
	@Before
	public void load() {
		arg = "name1=\"a test\" name2=\"another test with space\" name3=value name4=true";

	}

	@Test
	public void test() {
		args = split(arg);
		System.err.println(Arrays.asList(args));
	}

	public static String[] split(String str) {
		str += " "; // To detect last token when not quoted...
		ArrayList<String> strings = new ArrayList<String>();
		boolean inQuote = false;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '"' || c == ' ' && !inQuote) {
				if (c == '"')
					inQuote = !inQuote;
				if (!inQuote && sb.length() > 0) {
					strings.add(sb.toString());
					sb.delete(0, sb.length());
				}
			} else
				sb.append(c);
		}
		return strings.toArray(new String[strings.size()]);
	}
}
