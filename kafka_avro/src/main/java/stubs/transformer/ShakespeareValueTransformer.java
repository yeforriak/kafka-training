package stubs.transformer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stubs.model.ShakespeareValue;

public class ShakespeareValueTransformer {
	private static final Pattern pattern = Pattern.compile("^\\s*(\\d*)\\s*(.*)$");

	public static ShakespeareValue shakespeareValueFrom(String line) {
		Matcher matcher = pattern.matcher(line);
		if (matcher.matches()) {
			int lineNumber = Integer.parseInt(matcher.group(1));
			String lineOfWork = matcher.group(2);
			return new ShakespeareValue(lineNumber, lineOfWork);
		}

		throw new RuntimeException("Did not match Regex " + line);
	}
}
