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
	
	// TODO and tests

	@Override
	public void readFileToOrders(String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			Workbook wb = new XSSFWorkbook(fis);  
			Sheet sheet = wb.getSheetAt(0);
			
			Row headers = sheet.getRow(headerRow);
			ArrayList<String> arrHeaders = convertRowToArrayList(headers);
			ArrayList<String> currRow = convertRowToArrayList(sheet.getRow(1));
			
			
			
		}
		catch (IOException exception) {
			exception.printStackTrace();
		} 
	}
	
	private ArrayList<String> convertRowToArrayList(Row headers) {
		ArrayList<String> arr = new ArrayList<String>();
		for (int h = 0; h < headers.getPhysicalNumberOfCells(); h++) {
			arr.add(headers.getCell(h).getStringCellValue());
		}
		return arr;
	}
	
}
