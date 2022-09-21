package freshStart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PrintConfigReader {
	private String configFileLocation = "settings/printerConfig.txt";
	
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
					switch (i) {
					case 0:
						name = line.strip();
						break;
					case 1:
						mName = line.strip();
						break;
					case 2:
						floats = parseFloats(line);
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
			strs.add(line.substring(0,commaIndex).strip());
			line = line.substring(commaIndex + 1);
			commaIndex = line.indexOf(",");
		}
		strs.add(line.strip());
		
		return strs;
	}
	
	private ArrayList<Float> parseFloats(String line) {
		ArrayList<String> strs = parseCommaSeparatedValues(line);
		ArrayList<Float> floats = new ArrayList<Float>();
		for (String s: strs) {
			floats.add(Float.parseFloat(s));
		}
		return floats;
	}
}
