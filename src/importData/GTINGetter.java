package importData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GTINGetter {
	private Map<String, String> itemCodeToGTIN = new ConcurrentHashMap<String, String>();
	private String gtinFilePath = "resources/gtins.xlsx";
	
	public GTINGetter() {
		readGTINs();
	}
	
	public void readGTINs() {
		FileInputStream fis = getFileInputStream(gtinFilePath);
		if (fis == null) {
			System.out.println("Bad file path: " + gtinFilePath);
			return;
		}
		
		Workbook workbook = getWorkbook(fis);
		if (workbook == null) {		return; }
		
		Sheet sheet = workbook.getSheetAt(0);
		readGTINSFromSheet(sheet);
		
		try {
			workbook.close();
			fis.close();
		}
		catch (IOException e){	}
		
//		System.out.println(itemCodeToGTIN);
//		System.out.println(itemCodeToGTIN.size());
	}	
	
	private void readGTINSFromSheet(Sheet sheet) {
		int codeColumn = 0;
		int gtinColumn = 0;
		Row headerRow = sheet.getRow(0);
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			String s = headerRow.getCell(i).getStringCellValue();
			if (s.equals("Item Code")) {
				codeColumn = i;
			}
			else if (s.equals("GTIN")) {
				gtinColumn = i;
			}
		}

		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row != null) {
				itemCodeToGTIN.put(row.getCell(codeColumn).getStringCellValue(), 
						getGTIN(row.getCell(gtinColumn)));
			}
		}
	}
	
	private String getGTIN(Cell cell) {
		if (cell == null) {
			return "";
		}
		try {
			String s = cell.getStringCellValue();
			return s;
		}
		catch (IllegalStateException e) {
			double i = cell.getNumericCellValue();
			long longNum = (long) i;
			return Long.toString(longNum);
		}
	}
	
	public String getGTINForItemCode(String code) {
		String gtin = itemCodeToGTIN.get(code);
		if (gtin == null) {
			return "";
		}
		return gtin;
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
}
