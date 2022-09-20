/**
 * ExcelReader class
 * Reads orders given the name of an existing Excel file, and an ExcelFormat.
 * If the file does not exist or cannot be read, returns an empty list.
 */
package freshStart;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {	
	private int typeColumn = 0;
	private int packDateColumn = 0;
	private int invoiceNumColumn = 0;
	private int poColumn = 0;
	private int companyColumn = 0;
	private int shipDateColumn = 0;
	private int shipViaColumn = 0;
	private int GTINColumn = 0;
	private int itemCodeColumn = 0;
	private int itemDescriptionColumn = 0; 
	private int quantityColumn = 0;
	private int priceColumn = 0;
	
	/**
	 * Returns the orders represented in an Excel file given the file name and format.
	 * @param fileName the Excel file to read orders from
	 * @param ef the format of the file - see the ExcelFormat class
	 * @return a list of orders in the file, or an empty list if no orders or any errors occurred in reading
	 */
	public ArrayList<Order> getOrdersFromFile(String fileName, ExcelFormat ef) {
		FileInputStream fis = getFileInputStream(fileName);
		if (fis == null) {
			return new ArrayList<Order>();
		}
		
		Workbook workbook = getWorkbook(fis);
		if (workbook == null) {
			return new ArrayList<Order>();
		}
		
		Sheet sheet = workbook.getSheetAt(0);
		setColumnIndices(ef, sheet);
		ArrayList<Order> orders = readOrdersFromSheet(sheet, ef);
		
		try {
			workbook.close();
		}
		catch (IOException e){	}
		
		return orders;
	}	
	
	protected void setColumnIndices(ExcelFormat ef, Sheet sheet) {
		
	}
	
	private void makeColumnList() {
		
	}
	
	protected ArrayList<Order> readOrdersFromSheet(Sheet sheet, ExcelFormat ef) {
		int startRow = ef.getDataStartRow();
		for (int r = startRow; r < sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			Cell invCell = row.getCell(typeColumn);				
//			if (invCell != null && checkRowValid(row)) {
//				addItemToOrders(row);
//			}
		}
		return null;
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
			System.out.println("FileNotFound");
			return null;
		}
	}
}
