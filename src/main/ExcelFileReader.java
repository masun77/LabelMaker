package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader implements FileReader {
	private int orderTypeRow = 0;
	private int orderTypeColumn = 1;
	private int headerRow = 1;

	@Override
	public ArrayList<Order> readFileToOrders(String fileName) {
		OrderFactory of = new OrderFactory();
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			Workbook wb = new XSSFWorkbook(fis);  // todo: what if not xlsx file
			Sheet sheet = wb.getSheetAt(0);
			
			String orderType = sheet.getRow(orderTypeRow).getCell(orderTypeColumn).getStringCellValue();
			Row headers = sheet.getRow(headerRow);
			int lastRow = sheet.getLastRowNum();
			ArrayList<String> arrHeaders = convertRowToArrayList(headers);
			
			for (int c = 2; c <= lastRow; c++) {
				ArrayList<String> currRow = convertRowToArrayList(sheet.getRow(c));
				Order currOrder = of.makeOrder(orderType, arrHeaders, currRow);
				orders.add(currOrder);
			}
		}
		catch (IOException exception) {
			exception.printStackTrace();
		} 
		
		return orders;
	}
	
	private ArrayList<String> convertRowToArrayList(Row headers) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int h = 0; h < headers.getPhysicalNumberOfCells(); h++) {
			arr.add(headers.getCell(h).getStringCellValue());
		}
		return arr;
	}
	
}
