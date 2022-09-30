/**
 * ExcelReader class
 * Reads orders given the name of an existing Excel file, and an ExcelFormat.
 * If the file does not exist or cannot be read, returns an empty list.
 */
package importData;

import static importData.HeaderOption.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import labels.Date;
import labels.DateImp;
import main.Item;
import main.Order;

public class ExcelReader {		
	private ArrayList<Integer> columnIndices = new ArrayList<>();
	private ArrayList<String> headersUsing = new ArrayList<>(); 
	private ArrayList<HeaderOption> options = new ArrayList<>();
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<Integer> orderNums = new ArrayList<>();
	private ExcelFormat format = null;
	
	/**
	 * Returns the orders represented in an Excel file given the file name and format.
	 * @param fileName the Excel file to read orders from
	 * @param ef the format of the file - see the ExcelFormat class
	 * @return a list of orders in the file, or an empty list if no orders or any errors occurred in reading
	 */
	public ArrayList<Order> getOrdersFromFile(String fileName, ExcelFormat ef) {
		format = ef;
		FileInputStream fis = getFileInputStream(fileName);
		if (fis == null || format == null) {
			System.out.println("Bad file or format: " + fileName);
			return new ArrayList<Order>();
		}
		
		Workbook workbook = getWorkbook(fis);
		if (workbook == null) {
			return new ArrayList<Order>();
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		setColumnIndices(sheet);
		ArrayList<Order> orders = readOrdersFromSheet(sheet);
		
		try {
			workbook.close();
		}
		catch (IOException e){	}
		
		return orders;
	}	
	
	/**
	 * Set the list of column indices that correspond to the headers
	 * from the given Excel format. This allows values from those columns
	 * to be saved to the correct field in the items and orders
	 * read from the file.
	 * @param sheet the sheet to get the column indices from
	 */
	protected void setColumnIndices(Sheet sheet) {
		columnIndices = new ArrayList<>();
		headersUsing = new ArrayList<>();
		options = new ArrayList<>();
		ArrayList<ExcelHeader> headers = format.getHeaders();
		for (ExcelHeader h: headers) {
			headersUsing.add(h.getHeader());
			options.add(h.getHeaderType());
			columnIndices.add(0);
		}

		Row headerRow = sheet.getRow(format.getHeaderRowIndex());
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			String cellValue = headerRow.getCell(i) == null? "" : headerRow.getCell(i).getStringCellValue();
			int headerIndex = headersUsing.indexOf(cellValue);
			if (headerIndex >= 0) {
				columnIndices.set(headerIndex, i);
			}
		}
	}
	
	/**
	 * Read and return the orders from the given sheet.
	 * @param sheet the sheet to read the orders from
	 * @return a list of the orders read from the sheet
	 */
	protected ArrayList<Order> readOrdersFromSheet(Sheet sheet) {
		orders = new ArrayList<Order>();
		orderNums = new ArrayList<>();
		int startRow = format.getDataStartRow();
		for (int r = startRow; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row != null && isItemRow(row)) {
				addItemToOrders(row);
			}
		}
		return orders;
	}
		
	/**
	 * Convert a static type of HeaderOption into the column index
	 * corresponding to that type on the current sheet.
	 * @param h the header type to get the column index for
	 * @return the index of the column of that header type on the current sheet
	 */
	private int headerTypeToColumn(HeaderOption h) {
		return columnIndices.get(options.indexOf(h));
	}
	
	/**
	 * Check whether a row contains item data. It contains item data
	 * if it has the correct type specified in the format file,
	 * and is not in the list of excluded companies in the format file.
	 * @param row the row to check
	 * @return true is the row contains item data to read, false otherwise
	 */
	private boolean isItemRow(Row row) {
		Cell invCell = row.getCell(headerTypeToColumn(TYPE));
		if (invCell == null) {
			return false;
		}
		if (!invCell.getStringCellValue().equals(format.getTypeToSave())) {
			return false;
		}
		boolean check = true;
		for (String s: format.getExcludedCompanies()) {
			check = check && !getCompanyFromRow(row).contains(s);
		}
		return check;
	}
	
	/**
	 * Add the item specified in a row of data to the current list of orders
	 * @param row the row to read the item from
	 */
	protected void addItemToOrders(Row row) {
		int invNum;
		try {
			invNum = (int) row.getCell(headerTypeToColumn(INVOICE_NUMBER)).getNumericCellValue();
		}
		catch (Exception e) {
			invNum = Integer.parseInt(row.getCell(headerTypeToColumn(INVOICE_NUMBER)).getStringCellValue());
		}
		Order currOrder = getCorrespondingOrder(invNum, row);
				
		Item newIt = new Item(getCompanyFromRow(row), getShipDateFromRow(row),
				getProdNameFromRow(row), 
				getItemCodeFromRow(row), getGTINFromRow(row),
				getUnitFromRow(row), getQtyFromRow(row), getPriceFromRow(row));
		currOrder.addItem(newIt);
	}
	
	/**
	 * Get the order that corresponds to this invoice number, or create a new order
	 * if the number is new.
	 * @param invNum the number of the order to get
	 * @param row the row of data to get the order for
	 * @return the order corresponding to the given invoice number, or a new order with this
	 * 		invoice number and the data from the given row
	 */
	private Order getCorrespondingOrder(int invNum, Row row) {
		if (!orderNums.contains(invNum)) {
			Order currOrder = new Order();
			currOrder.setShipVia(row.getCell(headerTypeToColumn(SHIP_VIA)).getStringCellValue());
			currOrder.setInvoiceNum(invNum);
			currOrder.setPONum(getPONumFromRow(row));
			currOrder.setCompany(getCompanyFromRow(row));
			currOrder.setShipDate(getShipDateFromRow(row));
			orders.add(currOrder);
			orderNums.add(invNum);
			return currOrder;
		}
		
		return orders.get(orderNums.indexOf(invNum));
	}
	
	/**
	 * Get the company from the given row
	 * @param row the row to get the company from
	 * @return the company
	 */
	private String getCompanyFromRow(Row row) {
		return row.getCell(headerTypeToColumn(COMPANY)).getStringCellValue();
	}
	
	/**
	 * Get the item description from the given row
	 * @param row the row to get the item description from
	 * @return the item description
	 */
	private String getItemDescriptionFromRow(Row row) {
		return row.getCell(headerTypeToColumn(ITEM_DESCRIPTION)).getStringCellValue();
	}
	
	/**
	 * Get the item code from the given row
	 * @param row the row to get the item code from
	 * @return the item code
	 */
	private String getItemCodeFromRow(Row row) {
		String itCode = row.getCell(headerTypeToColumn(ITEM_CODE)).getStringCellValue();
		while (itCode.contains(":")) {
			itCode = itCode.substring(itCode.indexOf(":") + 1);
		}
		return itCode;
	}
	
	/**
	 * Get the product name from the given row's item description
	 * @param row the row to get the product name from
	 * @return the product name
	 */
	private String getProdNameFromRow(Row row) {
		if (options.contains(PRODUCT_NAME)) {
			return row.getCell(headerTypeToColumn(PRODUCT_NAME)).getStringCellValue();
		}
		
		String s = getItemDescriptionFromRow(row);
		String descrip = "";
		if (s.contains(",")) {
			descrip = s.substring(0, s.indexOf(","));
		}
		else {
			descrip = s;
		}
		return descrip;
	}
	
	/**
	 * Get the GTIN from the given row
	 * @param row the row to get the GTIN from
	 * @return the GTIN
	 */
	private String getGTINFromRow(Row row) {
		Cell c = row.getCell(headerTypeToColumn(GTIN));
		if (c != null) {
			try {
				return Integer.toString((int)c.getNumericCellValue());
			}
			catch (Exception e)
			{
				return c.getStringCellValue();
			}
		}
		
		return ""; // TODO: put GTINs into quickbooks
	}
	
	/**
	 * Get the unit from the given row's item description
	 * @param row the row to get the unit from
	 * @return the unit
	 */
	private String getUnitFromRow(Row row) {
		if (options.contains(UNIT)) {
			return row.getCell(headerTypeToColumn(UNIT)).getStringCellValue();
		}
		
		String descrip = getItemDescriptionFromRow(row);
		if (descrip.contains(",")) {
			int startIndex = descrip.indexOf(",") + 1;
			int endIndex = descrip.indexOf("GLOBAL", startIndex);
			if (endIndex < 0) {
				endIndex = descrip.length();
			}
			return descrip.substring(startIndex, endIndex);
		}

		return "";
	}
	
	/**
	 * Get the quantity from the given row
	 * @param row the row to get the quantity from
	 * @return the quantity
	 */
	private float getQtyFromRow(Row row) {
		try {
			return (float) row.getCell(headerTypeToColumn(QUANTITY)).getNumericCellValue();
		}
		catch (Exception e)
		{
			return Float.parseFloat(row.getCell(headerTypeToColumn(QUANTITY)).getStringCellValue());
		}
	}
	
	/**
	 * Get the price from the given row
	 * @param row the row to get the price from
	 * @return the price
	 */
	private float getPriceFromRow(Row row) {
		try {
			return (float) row.getCell(headerTypeToColumn(PRICE)).getNumericCellValue();
		}
		catch (Exception e)
		{
			return Float.parseFloat(row.getCell(headerTypeToColumn(PRICE)).getStringCellValue());
		}
	}
	
	/**
	 * Get the PO number from the given row
	 * @param row the row to get the PO number from
	 * @return the PO number
	 */
	private String getPONumFromRow(Row row) {
		Cell c = row.getCell(headerTypeToColumn(PO_NUMBER));
		if (c.getCellType().equals(CellType.NUMERIC)) {
			return Integer.toString((int)c.getNumericCellValue());
		}
		return c.getStringCellValue();
	}
	
	/**
	 * Get the ship date from the given row
	 * @param row the row to get the ship date from
	 * @return the ship date
	 */
	private Date getShipDateFromRow(Row row) {
		Cell cell = row.getCell(headerTypeToColumn(SHIP_DATE));
		if (cell == null) {
			return new DateImp(1,1,2022);
		}
		try {
			 String sd = cell.getStringCellValue();
			 return DateImp.parseDate(sd);
		}
		catch (IllegalStateException e) {
			String sd = cell.getDateCellValue().toString();
			return DateImp.parseCellDate(sd);
		}
	}
	
	/**
	 * Get the Workbooks from a fileInputStream.
	 * @param fis the fileInputStream to create the workbook from
	 * @return the workbook, or null if there was an error reading the file
	 */
	protected Workbook getWorkbook(FileInputStream fis) {
		try {
			Workbook wb = new XSSFWorkbook(fis);
			return wb;
		}
		catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Get a fileInputStream given a file name.
	 * @param fileName the name of the file to read
	 * @return the FileInputStream, or null if the file did not exist
	 */
	protected FileInputStream getFileInputStream(String fileName) {
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			return fis;
		}
		catch (FileNotFoundException e){
			return null;
		}
	}
}
