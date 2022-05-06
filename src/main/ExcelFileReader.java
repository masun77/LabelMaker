package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileReader implements FileReader {
	private ArrayList<Order> orderList = null;
	private int headerRow = 0;

	@Override
	public ArrayList<Order> readFileToOrders(String fileName) {
		orderList = new ArrayList<Order>();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			Workbook wb = new XSSFWorkbook(fis);  
			Sheet sheet = wb.getSheetAt(0);
			
			Row headers = sheet.getRow(headerRow);
			ArrayList<String> arrHeaders = convertRowToArrayList(headers);
			//ArrayList<String> currRow = convertRowToArrayList(sheet.getRow(1));
			
			System.out.println(arrHeaders.toString());
			
			wb.close();
			fis.close(); // may be redundant
			
		}
		catch (IOException exception) {
			exception.printStackTrace();
		} 
		
		return orderList;
	}
	
	private ArrayList<String> convertRowToArrayList(Row headers) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int h = 0; h < headers.getPhysicalNumberOfCells(); h++) {
			Cell cell = headers.getCell(h);
			CellType ct = cell.getCellType();
			if (ct == CellType.STRING) {
				arr.add(cell.getStringCellValue());
			}
			else if (ct == CellType.NUMERIC) {
					//todo
			} 
			else {
				//todo
			}
		}
		return arr;
	}
	
}
