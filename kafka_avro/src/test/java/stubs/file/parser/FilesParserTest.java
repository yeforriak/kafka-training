package stubs.file.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class FilesParserTest {

	private static final String FILES_PATH = "dataset/shakespeare";
	private final FilesParser filesParser = new FilesParser(FILES_PATH);

	@Test
	public void itShouldParseFiles() {
		Map<String, List<String>> files = filesParser.parse();
		Assert.assertTrue(files.keySet().size() == 2);
		Iterator<String> fileNames = files.keySet().iterator();
		Assert.assertTrue(fileNames.next().equals("Hamlet"));
		Assert.assertTrue(fileNames.next().equals("Julius Caesar"));

	}

}
