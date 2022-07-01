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
import printing.PrinterDescription;

public class DataSaver implements FileBackup {
	// Item data constants
	private final String ITEM_FILE_NAME = "resources/itemData.csv";
	private final int DATA_ITEM_CODE_INDEX = 0;
	private final int DATA_DESCRIPTION_INDEX = 1;
	private final int DATA_GTIN_INDEX = 2;
	private final int DATA_DISPLAY_NAME_INDEX = 3;
	private final int DATA_UNIT_INDEX = 4;
	
	// Order constants
	private final String ORDER_FILE_NAME = "resources/Orders.csv";
	private final int ORDER_NUM_INDEX = 0;
	private final int PO_INDEX = 1;
	private final int SHIP_VIA_INDEX = 2;
	private final int CUSTOMER_INDEX = 3;
	private final int DATE_INDEX = 4;
	private final int ITEM_CODE_INDEX = 5;
	private final int GTIN_INDEX = 6;
	private final int PROD_NAME_INDEX = 7;
	private final int UNIT_INDEX = 8;
	private final int QTY_INDEX = 9;
	private final int PRICE_INDEX = 10;
	
	// Print constants
	private final String PRINT_CONFIG_FILE_NAME = "resources/printerConfig.txt";
	
	
	/**
	 * Write orders to a csv with the given file path
	 * Each line in the csv will represent one item in an order, with all of its item data
	 * All items in an order will be written into the csv on sequential lines
	 * All orders in the provided arraylist will be written into the csv, with the number of lines
	 * corresponding to the number of items they contain
	 * @param orders the orders to write to the csv
	 * @param filePath the file path of the file to write them to
	 */
	@Override
	public void saveOrders(ArrayList<Order> orders)
	{
	    File file = new File(ORDER_FILE_NAME);
	  
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
	private void writeOrder(Order order, CSVWriter writer, int orderNumber) {
		ArrayList<LabelableItem> items = order.getItems();
        List<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem item = items.get(i);
			String[] orderData = new String[11];
			orderData[ORDER_NUM_INDEX] = Integer.toString(orderNumber);
			orderData[PO_INDEX] = order.getPONum();
			orderData[SHIP_VIA_INDEX] = order.getShipVia();
			orderData[CUSTOMER_INDEX] = item.getCustomer();
			orderData[DATE_INDEX] = item.getPackDate().getDateMMDDYYYY();
			orderData[ITEM_CODE_INDEX] = item.getItemCode();
			orderData[GTIN_INDEX] = item.getGtin();
			orderData[PROD_NAME_INDEX] = item.getProductName();
			orderData[UNIT_INDEX] = item.getUnit();
			orderData[QTY_INDEX] = Float.toString(item.getQuantity());
			orderData[PRICE_INDEX] = Float.toString(item.getPrice());
			
			data.add(orderData);
					
		}
        writer.writeAll(data);
	}
	
	/**
	 * Read orders from a csv with the given file path into an arraylist. 
	 * @param filePath the path to the csv to read
	 * @return an array list of the orders listed in the csv
	 */
	@Override
	public ArrayList<Order> readSavedOrders() {
		 try {
		        FileReader filereader = new FileReader(ORDER_FILE_NAME);
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
	public ArrayList<Order> getOrdersFromList(List<String[]> allData) {
		ArrayList<Order> orders = new ArrayList<Order>();
		for (String[] row : allData) {
        	Order order = new Order();
        	if (Integer.parseInt(row[ORDER_NUM_INDEX]) < orders.size()) {
        		order = orders.get(Integer.parseInt(row[ORDER_NUM_INDEX]));
        	}
        	else {
        		orders.add(order);
        	}
            order.setPONum(row[PO_INDEX]);
            order.setShipVia(row[SHIP_VIA_INDEX]);
            order.setCompany(row[CUSTOMER_INDEX]);
            Date date = DateImp.parseDate(row[DATE_INDEX]);
            order.setShipDate(date);
        	
            LabelableItem item = new RDFItem();
            item.setCustomer(row[CUSTOMER_INDEX]);
            item.setPackDate(date);
            
            item.setItemCode(row[ITEM_CODE_INDEX]);
            item.setGtin(row[GTIN_INDEX]);
            item.setProductName(row[PROD_NAME_INDEX]);
            item.setUnit(row[UNIT_INDEX]);
            
            item.setQuantity(Float.parseFloat(row[QTY_INDEX]));
            item.setPrice(Float.parseFloat(row[PRICE_INDEX]));
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
	@Override
	public String getItemDescription(String itemCode) {
		 try {
		        FileReader filereader = new FileReader(ITEM_FILE_NAME);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		 
		        for (String[] row : allData) {
		        	if (row[DATA_ITEM_CODE_INDEX].equals(itemCode)) {
		        		return row[DATA_DESCRIPTION_INDEX];
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
	@Override
	public ArrayList<String> getItemData(String itemCode) {
		ArrayList<String> data = new ArrayList<String>();
		 try {
		        FileReader filereader = new FileReader(ITEM_FILE_NAME);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		 
		        for (String[] row : allData) {
		        	if (row[DATA_ITEM_CODE_INDEX].equals(itemCode)) {
		        		data.add(row[DATA_GTIN_INDEX]);
		        		data.add(row[DATA_DISPLAY_NAME_INDEX]);
		        		data.add(row[DATA_UNIT_INDEX]);
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

	@Override
	public PrinterDescription getPrinterDescription() {
		try {
	        FileReader filereader = new FileReader(PRINT_CONFIG_FILE_NAME);
	        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
	        CSVReader csvReader = new CSVReaderBuilder(filereader)
	                                  .withCSVParser(parser)
	                                  .build();
	 
	        List<String[]> allData = csvReader.readAll();
	 
	        return new PrinterDescription(allData);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}
}
