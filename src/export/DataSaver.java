package export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import main.Item;
import main.Order;

public class DataSaver {

	public static void writeOrdersToCSV(ArrayList<Order> orders, String filePath)
	{
	    File file = new File(filePath);
	    // todo: first clear existing file; or save with backups - 2 copies? or 3? or new one each time?
	  
	    try {
	        FileWriter outputfile = new FileWriter(file);
	        CSVWriter writer = new CSVWriter(outputfile, '|',
	        		CSVWriter.NO_QUOTE_CHARACTER,
	        		CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	        		CSVWriter.DEFAULT_LINE_END);


	        for (int ord = 0; ord < orders.size(); ord++) {
	        	writeOrder(orders.get(ord), writer, ord);
	        }

	        writer.close();
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void writeOrder(Order order, CSVWriter writer, int orderNumber) {
		ArrayList<Item> items = order.getItems();
        List<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			data.add(new String[] { Integer.toString(orderNumber), item.getCustomer(), item.getPackDate().getDateMMDDYYYY(), item.getProductName(), 
					item.getUnit(), Integer.toString(item.getQuantity()) });
		}
        writer.writeAll(data);
	}
}
