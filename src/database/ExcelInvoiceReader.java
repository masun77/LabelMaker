package database;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.Order;

public class ExcelInvoiceReader implements DataImporter {
	private String fileName = "";
	
	// Constants
	private final int START_ROW = 5;  // XSSFSheet is 0-based according to the documentation
	private final int START_COLUMN = 3;
	private final int TYPE_COL = 3;
	private final int DATE_COL = 4;
	private final int PO_COL = 6;
	private final int COMPANY_COL = 7;
	private final int SHIP_VIA_COL = 8;
	private final int ITEM_CODE_COL = 9;
	private final int QTY_COL = 10;
	private final int PRICE_COL = 11;
	private final String INV_TYPE = "Invoice";

	@Override
	public ArrayList<Order> readInvoices() {		
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			
			for (int r = START_ROW; r < sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				Cell invCell = row.getCell(TYPE_COL);				
				if (invCell != null && checkRowValid(row)) {
					System.out.println(row.getCell(COMPANY_COL).getStringCellValue());
				}
			}

			workbook.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Order>();
		}		
		
		return null;
	}
	
	private boolean checkRowValid(Row row) {
		boolean check = row.getCell(TYPE_COL).getStringCellValue().equals(INV_TYPE);
		check = check && !row.getCell(COMPANY_COL).getStringCellValue().contains("Market");
		return check;
	}

	@Override
	public void setResourcePath(String filePath) {
		fileName = filePath;
	}
}
