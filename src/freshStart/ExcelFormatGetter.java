/**
 * ExcelFormatGetter
 * Reads in the excel formats from the excelFileFormats folder in the settings folder.
 * Returns an existing ExcelFormat given its name.
 */

package freshStart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExcelFormatGetter {
	private String folderLocation = "settings/excelFileFormats";
	private HashMap<String, ExcelFormat> formats = new HashMap<>();
		
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
	
	/**
	 * Clears the old Excel formats and reads in all of the formats
	 * in the excelFileFormats folder in settings. 
	 */
	public void readExcelFormats() {
		formats = new HashMap<>();
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
