/**
 * ExcelFormatGetter
 * Reads in the excel formats from the excelFileFormats folder in the settings folder.
 * Returns an existing ExcelFormat given its name.
 */

package importData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ExcelFormatGetter {
	private String folderLocation = "settings/excelFileFormats";
	private ConcurrentHashMap<String, ExcelFormat> formats = new ConcurrentHashMap<>();
		
	/**
	 * Return a list of the existing Excel formats.
	 * @return a list of the existing formats
	 */
	public ArrayList<ExcelFormat> getFormats() {
		ArrayList<ExcelFormat> forms = new ArrayList<>();
		for (ExcelFormat f: formats.values()) {
			forms.add(f);
		}
		return forms;
	}
	
	/**
	 * Returns a list of the names of the existing Excel formats.
	 * @return list of the names of the existing Excel formats.
	 */
	public ArrayList<String> getFormatNames() {
		ArrayList<String> names = new ArrayList<>();
		for (String s: formats.keySet()) {
			names.add(s);
		}
		return names;
	}
	
	public ExcelFormat getFormatByName(String name) {
		return formats.get(name);
	}
	
	/**
	 * Clears the old Excel formats and reads in all of the formats
	 * in the excelFileFormats folder in settings. 
	 */
	public void readExcelFormats() {
		formats = new ConcurrentHashMap<>();
		File formatFolder = new File(folderLocation);
		for (File f: formatFolder.listFiles()) {
			addNewFormat(f);
		}
	}
	
	/**
	 * Adds a new format given a format .txt file.
	 * Reads each line and adds it to a new ExcelFormat with the addSetting function.
	 * @param f the file to read the format from.
	 */
	protected void addNewFormat(File f) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e) {
			return;
		}
		
		ExcelFormat ef = new ExcelFormat();
		try {
			String line = br.readLine();
			while (line != null) {
				ef.addSetting(line);
				line = br.readLine();
			}
			formats.put(ef.getName(), ef);
			br.close();
		}
		catch (IOException e) {}
	}
}
