package localBackup;

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

import database.DataClient;
import freshStart.Date;
import freshStart.DateImp;
import main.LabelableItem;
import main.Order;
import main.RDFItem;
import printing.PrinterDescription;

public class DataSaver implements LocalFileBackup, DataClient {
	// Item data constants
	private final String ITEM_FILE_NAME = "settings/itemData.csv";
	private final int DATA_ITEM_CODE_INDEX = 0;
	private final int DATA_DESCRIPTION_INDEX = 1;
	private final int DATA_GTIN_INDEX = 2;
	private final int DATA_DISPLAY_NAME_INDEX = 3;
	private final int DATA_UNIT_INDEX = 4;
	
	// Order constants
	private final String ORDER_FILE_NAME = "resources/Orders.csv";
	private final int INVOICE_NUM_INDEX = 0;
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
	private final String PRINT_CONFIG_FILE_NAME = "settings/printerConfig.txt";
	
	// Server constants
	private final String SERVER_CONFIG_FILE_NAME = "settings/serverConfig.txt";
	private final int SERVER_IP = 0;
	private final int SERVER_PORT = 1;
	
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
	        	writeOrder(orders.get(ord), writer);
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
	private void writeOrder(Order order, CSVWriter writer) {
		ArrayList<LabelableItem> items = order.getItems();
        List<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem item = items.get(i);
			String[] orderData = new String[11];
			orderData[INVOICE_NUM_INDEX] = Integer.toString(order.getInvoiceNumber());
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
	public ArrayList<Order> getOrders() {
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
		ArrayList<Integer> invNums = new ArrayList<>();
		for (String[] row : allData) {
        	Order order = new Order();
        	int invNum = Integer.parseInt(row[INVOICE_NUM_INDEX]);
        	if (invNums.contains(invNum)) {
        		order = orders.get(invNums.indexOf(invNum));
        	}
        	else {
        		orders.add(order);
        		invNums.add(invNum);
        	}
        	order.setInvoiceNumber(invNum);
            order.setPONum(row[PO_INDEX]);
            order.setShipVia(row[SHIP_VIA_INDEX]);
            order.setCompany(row[CUSTOMER_INDEX]);
            Date date = DateImp.parseDate(row[DATE_INDEX]);
        	
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
	
	/**
	 * Get the description for an item from the csv containing item information,
	 * given the item code, e.g. GRG10
	 * @param itemCode the code of the item to get the description for
	 * @param filePath the path to the csv containing the descriptions
	 * @return
	 */
	private String getterHelper(String itemCode, int index, String defaultReturn) {
		 try {
		        FileReader filereader = new FileReader(ITEM_FILE_NAME);
		        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
		        CSVReader csvReader = new CSVReaderBuilder(filereader)
		                                  .withCSVParser(parser)
		                                  .build();
		 
		        List<String[]> allData = csvReader.readAll();
		 
		        for (String[] row : allData) {
		        	if (row[DATA_ITEM_CODE_INDEX].equals(itemCode)) {
		        		return row[index];
		        	}
		        }
		    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		return defaultReturn;
	}
	
	/**
	 * Get the description for an item from the csv containing item information,
	 * given the item code, e.g. GRG10
	 * @param itemCode the code of the item to get the description for
	 * @return the item's description, or "Code not found"
	 */
	@Override
	public String getItemDescription(String itemCode) {
		 return getterHelper(itemCode, DATA_DESCRIPTION_INDEX, "Code not found");
	}
	
	/**
	 * Get the GTIN of an item from a csv given its item code, e.g. GRG10
	 * @param itemCode the code of the item to get the information for
	 * @return the items GTIN or a default GTIN if not found
	 */
	@Override
	public String getGTIN(String itemCode) {
		return getterHelper(itemCode, DATA_GTIN_INDEX, "00818181020000");
	}
	
	/**
	 * Get the name of an item from a csv given its item code, e.g. GRG10
	 * @param itemCode the code of the item to get the information for
	 * @return the name of the item
	 */
	@Override
	public String getProdName(String itemCode) {
		return getterHelper(itemCode, DATA_DISPLAY_NAME_INDEX, "Name not found");
	}

	/**
	 * Get the unit of an item from a csv given its item code, e.g. GRG10
	 * @param itemCode the code of the item to get the information for
	 * @return the unit of the item
	 */
	@Override
	public String getUnit(String itemCode) {
		return getterHelper(itemCode, DATA_UNIT_INDEX, "Unit not found");
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

	@Override
	public String getServerIPAddress() {
		try {
	        FileReader filereader = new FileReader(SERVER_CONFIG_FILE_NAME);
	        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
	        CSVReader csvReader = new CSVReaderBuilder(filereader)
	                                  .withCSVParser(parser)
	                                  .build();
	 
	        List<String[]> allData = csvReader.readAll();
	 
	        return allData.get(0)[SERVER_IP];
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		return null;
	}

	@Override
	public int getServerPort() {
		try {
	        FileReader filereader = new FileReader(SERVER_CONFIG_FILE_NAME);
	        CSVParser parser = new CSVParserBuilder().withSeparator('|').build();
	        CSVReader csvReader = new CSVReaderBuilder(filereader)
	                                  .withCSVParser(parser)
	                                  .build();
	 
	        List<String[]> allData = csvReader.readAll();
	 
	        return Integer.parseInt(allData.get(0)[SERVER_PORT]);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		return 0;
	}

	@Override
	public void setIPAddress(String ipAddr) {
		File file = new File(SERVER_CONFIG_FILE_NAME);
		  
	    try {
	        FileWriter outputfile = new FileWriter(file, false);
	        CSVWriter writer = new CSVWriter(outputfile, '|',
	        		CSVWriter.NO_QUOTE_CHARACTER,
	        		CSVWriter.DEFAULT_ESCAPE_CHARACTER,
	        		CSVWriter.DEFAULT_LINE_END);
	        
	        String[] line = {ipAddr};
	        writer.writeNext(line);

	        writer.close();
	    }
	    catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
