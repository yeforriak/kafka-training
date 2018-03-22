package stubs.file.parser;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilesParser {
	private final String filesPath;

	public FilesParser(String filesPath) {
		this.filesPath = filesPath;
	}
	
	
	private String removeExtensions(String fileName) {
		return fileName.substring(0, fileName.length() - 4);	
	}
	
	public Map<String,List<String>> parse(){
		File inputDir;
		try {
			inputDir = new File (Thread.currentThread().getContextClassLoader().getResource(this.filesPath).toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
		
		if(!inputDir.isDirectory()) {
			throw new RuntimeException("You must provided an input directory");
		}
		
		HashMap<String, List<String>> response = new HashMap<>();
		for(File file : inputDir.listFiles()) {
			String fileName = removeExtensions(file.getName());
			List<String> fileLines;
			try {
				fileLines = Files.readAllLines(Paths.get(file.getPath()));
			} catch (IOException e) {
				throw new RuntimeException("Error reading file", e);
			}
			response.put(fileName, fileLines);
		}
		
		return response;
	}

}
