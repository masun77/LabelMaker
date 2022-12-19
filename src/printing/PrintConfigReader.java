/**
 * PrintConfigReader
 * Reads in the configuration for the printer and media from the file
 * settings/printerConfig.txt. 
 * In the file, the first line should be the printer name,
 *  second line should be the media type, 
 *  and third line comma-separated startX, startY, width, and height
 *  of the printable area.
 *  
 *  For example, printerConfig.txt might read as follows, excluding comments:
 *  PDF					// printer name or unique substring of printer name
 *  A6					// media name
 *  .2,2.5,3.6,3.5		//	start x coordinate in inches, start y coordinate, width, height in inches
 */

package printing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PrintConfigReader {
	private String configFileLocation = "settings/printerConfig.txt";
	
	/**
	 * Reads in the printer configuration from the file and returns
	 * a PrinterDescription object with that information. 
	 * @return PrinterDescription containing the information in the file
	 */
	public PrinterDescription readPrinterConfig() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(configFileLocation)));
		}
		catch (FileNotFoundException e) {
			return null;
		}
		
		try {
			String name = "";
			String mName = "";
			ArrayList<Float> floats = new ArrayList<Float>();
			for (int i = 0; i < 3; i++) {
				String line = br.readLine();
				if (line != null) {
					switch (i) {    // First line is printer name
					case 0:
						name = line.trim();
						break;
					case 1:
						mName = line.trim();    // Second line is media name
						break;
					case 2:
						floats = parseFloats(line);   // Third line is starting coordinate and printable area dimensions
					}
				}
			}
			br.close();

			PrinterDescription pd = new PrinterDescription(name, mName, 
					floats.get(0), floats.get(1), floats.get(2), floats.get(3));
			return pd;
		}
		catch (IOException e) {}
		return null;
	}
	
	/**
	 * Parse a string of values separated by commas into an array
	 * of strings with each value as one item in the arry
	 * @param line the String to parse
	 * @return an array of strings with each comma-separated item as one String
	 */
	private ArrayList<String> parseCommaSeparatedValues(String line) {
		ArrayList<String> strs = new ArrayList<>();
		int commaIndex = line.indexOf(",");
		while(commaIndex >= 0) {
			strs.add(line.substring(0,commaIndex).trim());
			line = line.substring(commaIndex + 1);
			commaIndex = line.indexOf(",");
		}
		strs.add(line.trim());
		
		return strs;
	}
	
	/**
	 * Parse a comma-separated string into floats and return a list of them. 
	 * @param line the STring to parse into floats
	 * @return a list of the floats in the string 
	 */
	private ArrayList<Float> parseFloats(String line) {
		ArrayList<String> strs = parseCommaSeparatedValues(line);
		ArrayList<Float> floats = new ArrayList<Float>();
		for (String s: strs) {
			floats.add(Float.parseFloat(s));
		}
		return floats;
	}
}
