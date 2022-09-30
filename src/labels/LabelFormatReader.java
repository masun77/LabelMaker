/**
 * LabelFormatReader
 * Reads and saves label formats from the settings/labelFormats folder.
 * Each label format must contain a label name and
 * label dimensions, followed by any number of text and rectangle specifications.
 */

package labels;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LabelFormatReader {
	private ArrayList<LabelFormat> formats = new ArrayList<>();
	private ArrayList<String> formatNames = new ArrayList<>();
	private String folderLocation = "settings/labelFormats";
	
	/**
	 * Read and save the label formats from the folder settings/labelFormats.
	 * @return a list of the read label formats
	 */
	public ArrayList<LabelFormat> readLabelFormats() {
		formats = new ArrayList<>();
		formatNames = new ArrayList<>();
		File formatFolder = new File(folderLocation);
		for (File f: formatFolder.listFiles()) {
			addNewFormat(f);
		}
		return formats;
	}
	
	/**
	 * Create a new format and read in the settings for it
	 * from its file.
	 * @param f the file to read the settings from for the label format
	 */
	private void addNewFormat(File f) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(f));
		}
		catch (FileNotFoundException e) {
			return;
		}
		
		LabelFormat labelForm = new LabelFormat();
		try {
			String line = br.readLine();
			while (line != null) {
				labelForm.addSetting(line);
				line = br.readLine();
			}
			formatNames.add(labelForm.getName());
			formats.add(labelForm);
			br.close();
		}
		catch (IOException e) {}
	}
	
	public ArrayList<LabelFormat> getFormats() {
		return formats;
	}
	
}
