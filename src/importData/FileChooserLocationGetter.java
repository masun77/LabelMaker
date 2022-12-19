
package importData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import printing.PrinterDescription;

public class FileChooserLocationGetter {
	private String configFilePath = "settings/fileChooserConfig";
	private HashMap<String, ExcelFormat> formats = new HashMap<>();
		
	/**
	 * Return a list of the existing Excel formats.
	 * @return a list of the existing formats
	 */
	public String getFolderPath() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(configFilePath)));
		}
		catch (FileNotFoundException e) {
			return null;
		}
		
		try {
			String line = br.readLine();
			int colonIndex = line.indexOf(":");
			String defaultLocation = line.substring(colonIndex+1).strip();				
			br.close();
			
			return checkIfHome(defaultLocation);
		}
		catch (IOException e) {}
		return null;
	}
	
	private String checkIfHome(String path) {
		if (path.contains("\\") || path.contains("/") || path.contains(":")) {
			return path;
		}
		return System.getProperty("user.home") 
				+ System.getProperty("file.separator")
				+ path;
	}
}
