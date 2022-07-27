package database;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import labels.Date;
import labels.DateImp;
import main.AppState;
import main.Order;
import main.RDFItem;

public class ExcelInvoiceReader implements DataImporter {
	private String fileName = "";
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<Integer> orderNums = new ArrayList<>();
	
	// Constants
	private final int START_ROW = 5;  // XSSFSheet is 0-based according to the documentation
	private final int TYPE_COL = 3;
	private final int PACK_DATE_COL = 4;
	private final int INV_NUM_COL = 5;
	private final int PO_COL = 6;
	private final int COMPANY_COL = 7;
	private final int SHIP_DATE_COL = 8;
	private final int SHIP_VIA_COL = 9;
	private final int GTIN_COL = 10;
	private final int ITEM_CODE_COL = 11;
	private final int ITEM_DESCRIPTION_COL = 12; 
	private final int QTY_COL = 13;
	private final int PRICE_COL = 14;
	private final String INV_TYPE = "Invoice";
	private final String[] marketNames = {"Market", "TBFM", "ThBFM"};

	@Override
	public ArrayList<Order> readInvoices() {	
		// todo - open file explorer to select file? or what? or do that in another button, then click read? 
		// preview imported orders before importing? 
		
		orders = new ArrayList<>();
		orderNums = new ArrayList<>();
		
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			
			for (int r = START_ROW; r < sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				Cell invCell = row.getCell(TYPE_COL);				
				if (invCell != null && checkRowValid(row)) {
					addItemToOrders(row);
				}
			}

			workbook.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Order>();
		}		
		
		return orders;
	}
	
	private boolean checkRowValid(Row row) {
		boolean check = row.getCell(TYPE_COL).getStringCellValue().equals(INV_TYPE);
		for (String s: marketNames) {
			check = check && !row.getCell(COMPANY_COL).getStringCellValue().contains(s);
		}
		return check;
	}
	
	private void addItemToOrders(Row row) {
		int invNum = (int) row.getCell(INV_NUM_COL).getNumericCellValue();
		Order currOrder;
		
		if (!orderNums.contains(invNum)) {
			orderNums.add(invNum);
			currOrder = new Order();
			orders.add(currOrder);
			currOrder.setShipVia(row.getCell(SHIP_VIA_COL).getStringCellValue());
			currOrder.setInvoiceNumber(invNum);
			currOrder.setPONum(getPONumFromRow(row));
			currOrder.setCompany(row.getCell(COMPANY_COL).getStringCellValue());
		}
		
		currOrder = orders.get(orderNums.indexOf(invNum));
		
		RDFItem newIt = new RDFItem(getCustFromRow(row), getItemCodeFromRow(row), 
				getProdNameFromRow(row), getGTINFromRow(row),
				getUnitFromRow(row), getQtyFromRow(row), getPriceFromRow(row), 
				new DateImp(), getPackDateFromRow(row), getShipDateFromRow(row)
				);
		currOrder.addItem(newIt);
	}
	
	private String getCustFromRow(Row row) {
		return row.getCell(COMPANY_COL).getStringCellValue();
	}
	
	private String getItemCodeFromRow(Row row) {
		String itCode = row.getCell(ITEM_CODE_COL).getStringCellValue();
		while (itCode.contains(":")) {
			itCode = itCode.substring(itCode.indexOf(":") + 1);
		}
		return itCode;
	}
	
	private String getProdNameFromRow(Row row) {
		String descrip = AppState.getFileBackup().getProdName(getItemCodeFromRow(row));
		if (descrip.equals("Name not found")) {
			Cell c = row.getCell(ITEM_DESCRIPTION_COL);
			if (c != null) {
				String s = c.getStringCellValue();
				if (s.contains(",")) {
					descrip = s.substring(0, s.indexOf(","));
				}
				else {
					descrip = s;
				}
			}
		}
		return descrip;
	}
	
	private String getGTINFromRow(Row row) {
		Cell c = row.getCell(GTIN_COL);
		if (c != null) {
			return Integer.toString((int)c.getNumericCellValue());
		}
		String itCode = getItemCodeFromRow(row);
		
		return AppState.getFileBackup().getGTIN(itCode);
	}
	
	private String getUnitFromRow(Row row) {
		return AppState.getFileBackup().getUnit(getItemCodeFromRow(row));
	}
	
	private float getQtyFromRow(Row row) {
		return (float) row.getCell(QTY_COL).getNumericCellValue();
	}
	
	private float getPriceFromRow(Row row) {
		return (float) row.getCell(PRICE_COL).getNumericCellValue();
	}
	
	private Date getPackDateFromRow(Row row) {
		String pd = row.getCell(PACK_DATE_COL).getStringCellValue();
		return DateImp.parseDate(pd);
		
	}
	
	private Date getShipDateFromRow(Row row) {
		String sd = row.getCell(SHIP_DATE_COL).getStringCellValue();
		return DateImp.parseDate(sd);
	}
	
	private String getPONumFromRow(Row row) {
		Cell c = row.getCell(PO_COL);
		if (c.getCellType().equals(CellType.NUMERIC)) {
			return Integer.toString((int)c.getNumericCellValue());
		}
		return c.getStringCellValue();
	}

	@Override
	public void setResourcePath(String filePath) {
		fileName = filePath;
	}
}
