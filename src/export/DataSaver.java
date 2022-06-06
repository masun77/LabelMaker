package export;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import labels.Date;
import labels.DateImp;
import labels.LabelableItem;
import main.Order;
import main.RDFItem;

public class DataSaver {

	/**
	 * Write orders to a csv with the given file path
	 * Each line in the csv will represent one item in an order, with all of its item data
	 * All items in an order will be written into the csv on sequential lines
	 * All orders in the provided arraylist will be written into the csv, with the number of lines
	 * corresponding to the number of items they contain
	 * @param orders the orders to write to the csv
	 * @param filePath the file path of the file to write them to
	 */
	public static void writeOrdersToCSV(ArrayList<Order> orders, String filePath)
	{
	    File file = new File(filePath);
	  
	    try {
	        FileWriter outputfile = new FileWriter(file, false);
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
	
	/**
	 * Write a single order to the csv,
	 * writing a single line for each of its items
	 * @param order the order to write
	 * @param writer the writer to write with
	 * @param orderNumber the order number, so orders are kept separate as needed
	 */
	private static void writeOrder(Order order, CSVWriter writer, int orderNumber) {
		ArrayList<LabelableItem> items = order.getItems();
        List<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem item = items.get(i);
			data.add(new String[] { Integer.toString(orderNumber), 
					order.getPONum(), order.getShipVia(),
					item.getCustomer(), item.getPackDate().getDateMMDDYYYY(), 
					item.getItemCode(), item.getGtin(), item.getProductName(), item.getUnit(), 
					Float.toString(item.getQuantity()), Float.toString(item.getPrice()) });
		}
        writer.writeAll(data);
	}
	
	/**
	 * Read orders from a csv with the given file path into an arraylist. 
	 * @param filePath the path to the csv to read
	 * @return an array list of the orders listed in the csv
	 */
	public static ArrayList<Order> readOrdersFromCSV(String filePath) {
		 try {
		        FileReader filereader = new FileReader(filePath);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
				return getOrdersFromList(allData);
		 
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		 return new ArrayList<Order>();
	}
	
	/**
	 * Get orders from a list of string arrays and return them in an arraylist of orders
	 * @param allData the list of string arrays, with each string array representing one item in an order
	 * @return the arraylist of orders
	 */
	public static ArrayList<Order> getOrdersFromList(List<String[]> allData) {
		ArrayList<Order> orders = new ArrayList<Order>();
		for (String[] row : allData) {
        	Order order = new Order();
        	if (Integer.parseInt(row[0]) < orders.size()) {
        		order = orders.get(Integer.parseInt(row[0]));
        	}
        	else {
        		orders.add(order);
        	}
            order.setPONum(row[1]);
            order.setShipVia(row[2]);
            order.setCompany(row[3]);
            Date date = DateImp.parseDate(row[4]);
            order.setShipDate(date);
        	
            LabelableItem item = new RDFItem();
            item.setCustomer(row[3]);
            item.setPackDate(date);
            
            item.setItemCode(row[5]);
            item.setGtin(row[6]);
            item.setProductName(row[7]);
            item.setUnit(row[8]);
            
            item.setQuantity(Float.parseFloat(row[9]));
            item.setPrice(Float.parseFloat(row[10]));
            order.addItem(item);
        }
		return orders;
	}
	
	/**
	 * Get the description for an item from the csv containing item information,
	 * given the item code, e.g. GRG10
	 * @param itemCode the code of the item to get the description for
	 * @param filePath the path to the csv containing the descriptions
	 * @return
	 */
	public static String getDescriptionFromCSV(String itemCode, String filePath) {
		 try {
		        FileReader filereader = new FileReader(filePath);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		 
		        for (String[] row : allData) {
		        	if (row[0].equals(itemCode)) {
		        		return row[1];
		        	}
		        }
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		return "Code not found";
	}
	
	/**
	 * Get the data about an item from a csv given its item code, e.g. GRG10
	 * @param itemCode the code of the item to get the information for
	 * @param filePath the csv containing item information, where each row
	 * 		contains information about one item - its code, description, GTIN, display name, and unit
	 * @return
	 */
	public static ArrayList<String> getItemData(String itemCode, String filePath) {
		ArrayList<String> data = new ArrayList<String>();
		 try {
		        FileReader filereader = new FileReader(filePath);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		 
		        for (String[] row : allData) {
		        	if (row[0].equals(itemCode)) {
		        		data.add(row[2]);
		        		data.add(row[3]);
		        		data.add(row[4]);
		        	}
		        }
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		if (data.size() == 0) {
			data.add("");
			data.add("");
			data.add("");
		}
		return data;
	}
}
