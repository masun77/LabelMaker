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
import main.Item;
import main.Order;
import main.RDFItem;

public class DataSaver {

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
	
	private static void writeOrder(Order order, CSVWriter writer, int orderNumber) {
		ArrayList<Item> items = order.getItems();
        List<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			data.add(new String[] { Integer.toString(orderNumber), 
					order.getPONum(), order.getShipVia(),
					item.getCustomer(), item.getPackDate().getDateMMDDYYYY(), 
					item.getItemCode(), item.getGtin(), item.getProductName(), item.getUnit(), 
					Integer.toString(item.getQuantity()), Float.toString(item.getPrice()) });
		}
        writer.writeAll(data);
	}
	
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
	
	public static ArrayList<Order> getOrdersFromList(List<String[]> allData) {
		ArrayList<Order> orders = new ArrayList<Order>();
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
            order.setCompany(row[3]);
            Date date = DateImp.parseDate(row[4]);
            order.setShipDate(date);
        	
        	Item item = new RDFItem();
            item.setCustomer(row[3]);
            item.setPackDate(date);
            
            item.setItemCode(row[5]);
            item.setGtin(row[6]);
            item.setProductName(row[7]);
            item.setUnit(row[8]);
            
            item.setQuantity(Integer.parseInt(row[9]));
            item.setPrice(Float.parseFloat(row[10]));
            order.addItem(item);
        }
		return orders;
	}
	
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
		return data;
	}
}
