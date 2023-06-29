package example;

import java.util.ArrayList;

// based on: https://stackoverflow.com/questions/3366281/tokenizing-a-string-but-ignoring-delimiters-within-quotes
// see also:
// http://www.java2s.com/example/java-utility-method/string-split-by-quote/split-string-str-char-chrsplit-char-chrquote-fbd19.html
// https://www.baeldung.com/java-split-string-commas

public class CommandLineTokenizer {

	private boolean debug = false;

	public String[] getArgs() {
		return args;
	}

	private String commandline = null;
	private String[] args = {};

	public CommandLineTokenizer(String commandline) {
		this.commandline = commandline;
	}

	public void split() {
		commandline += " "; // To detect last token when not quoted...
		ArrayList<String> strings = new ArrayList<String>();
		boolean inQuote = false;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < commandline.length(); i++) {
			char c = commandline.charAt(i);
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
		args = strings.toArray(new String[strings.size()]);
		return;
	}

}
