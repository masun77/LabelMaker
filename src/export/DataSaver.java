package export;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import labels.DateImp;
import main.Item;
import main.Order;
import main.RDFItem;

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
			data.add(new String[] { Integer.toString(orderNumber), 
					order.getPONum(), order.getShipVia(),
					item.getCustomer(), item.getPackDate().getDateMMDDYYYY(), item.getProductName(), 
					item.getUnit(), Integer.toString(item.getQuantity()), Float.toString(item.getPrice()) });
		}
        writer.writeAll(data);
	}
	
	public static ArrayList<Order> readOrdersFromCSV(String filePath) {
		ArrayList<Order> orders = new ArrayList<Order>();
		 try {
		        FileReader filereader = new FileReader(filePath);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		 
		        for (String[] row : allData) {
		        	Order order = new Order();
		        	if (Integer.parseInt(row[0]) < orders.size()) {
		        		order = orders.get(0);
		        	}
		        	else {
		        		orders.add(order);
		        	}
		            order.setPONum(row[1]);
		            order.setShipVia(row[2]);
		        	
		        	Item item = new RDFItem();
		            item.setCustomer(row[3]);
		            item.setPackDate(DateImp.parseDate(row[4]));
		            item.setProductName(row[5]);
		            item.setUnit(row[6]);
		            item.setQuantity(Integer.parseInt(row[7]));
		            item.setPrice(Float.parseFloat(row[8]));
		            order.addItem(item);
		            
		            order.printOrder();
		        }
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		return orders;
	}
}
