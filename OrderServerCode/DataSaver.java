package server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataSaver {

	public static void writeOrdersToCSV(ArrayList<String> data, String filePath)
	{
	    File file = new File(filePath);
	  
	    try {
	        FileWriter outputfile = new FileWriter(file, false);
	        BufferedWriter writer = new BufferedWriter(outputfile);
	        
	        for (int str = 0; str < data.size(); str++) {
		        writer.write(data.get(str));
	        }

	        writer.close();
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
