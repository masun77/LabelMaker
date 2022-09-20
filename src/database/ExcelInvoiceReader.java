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

import freshStart.Date;
import freshStart.DateImp;
import main.AppState;
import main.Order;
import main.RDFItem;

public class ExcelInvoiceReader implements DataImporter {
	private String fileName = "";
	private ArrayList<Order> orders = new ArrayList<>();
	private ArrayList<Integer> orderNums = new ArrayList<>();
	
	// Constants
	private final int START_ROW = 5;  // XSSFSheet is 0-based according to the documentation
	private final int HEADER_ROW = 3;
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
	private final String typeName = "Type";
	private final String packDateName = "Date";
	private final String invNumName = "Num";
	private final String POName = "P. O. #";
	private final String companyName = "Name";
	private final String shipDateName = "Ship Date";
	private final String shipViaName = "Via";
	private final String GTINName = "GTIN NUMBER";
	private final String itemCodeName = "Item";
	private final String itemDescripName = "Item Description";
	private final String qtyName = "Qty";
	private final String priceName = "Sales Price";
	private final String INV_TYPE = "Invoice";
	private final String[] marketNames = {"Market", "TBFM", "ThBFM"};

	@Override
	public ArrayList<Order> readInvoices() {			
		orders = new ArrayList<>();
		orderNums = new ArrayList<>();
		
		try {
			FileInputStream fis = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);
			
			setColumnNums(sheet);
			
			for (int r = START_ROW; r < sheet.getLastRowNum(); r++) {
				Row row = sheet.getRow(r);
				Cell invCell = row.getCell(typeColumn);				
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
	
	private void setColumnNums(Sheet sheet) {
		Row headerRow = sheet.getRow(HEADER_ROW);
		for (int i = 0; i < headerRow.getLastCellNum(); i++) {
			String cellValue = headerRow.getCell(i) == null? "": headerRow.getCell(i).getStringCellValue();
			switch (cellValue) {
			case typeName:
				typeColumn = i;
				break;
			case packDateName:
				packDateColumn = i;
				break;
			case invNumName:
				invoiceNumColumn = i;
				break;
			case POName:
				poColumn = i;
				break;
			case companyName:
				companyColumn = i;
				break;
			case shipDateName:
				shipDateColumn = i;
				break;
			case shipViaName:
				shipViaColumn = i;
				break;
			case GTINName:
				GTINColumn = i;
				break;
			case itemCodeName:
				itemCodeColumn = i;
				break;
			case itemDescripName:
				itemDescriptionColumn = i;
				break;
			case qtyName:
				quantityColumn = i;
				break;
			case priceName:
				priceColumn = i;
				break;
			default: 
				break;
			}			
		}
	}
	
	private boolean checkRowValid(Row row) {
		boolean check = row.getCell(typeColumn).getStringCellValue().equals(INV_TYPE);
		for (String s: marketNames) {
			check = check && !row.getCell(companyColumn).getStringCellValue().contains(s);
		}
		return check;
	}
	
	private void addItemToOrders(Row row) {
		int invNum = (int) row.getCell(invoiceNumColumn).getNumericCellValue();
		Order currOrder;
		
		if (!orderNums.contains(invNum)) {
			orderNums.add(invNum);
			currOrder = new Order();
			orders.add(currOrder);
			currOrder.setShipVia(row.getCell(shipViaColumn).getStringCellValue());
			currOrder.setInvoiceNumber(invNum);
			currOrder.setPONum(getPONumFromRow(row));
			currOrder.setCompany(row.getCell(companyColumn).getStringCellValue());
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
		return row.getCell(companyColumn).getStringCellValue();
	}
	
	private String getItemCodeFromRow(Row row) {
		String itCode = row.getCell(itemCodeColumn).getStringCellValue();
		while (itCode.contains(":")) {
			itCode = itCode.substring(itCode.indexOf(":") + 1);
		}
		return itCode;
	}
	
	private String getProdNameFromRow(Row row) {
		String descrip = AppState.getFileBackup().getProdName(getItemCodeFromRow(row));
		if (descrip.equals("Name not found")) {
			Cell c = row.getCell(itemDescriptionColumn);
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
		Cell c = row.getCell(GTINColumn);
		if (c != null) {
			return Integer.toString((int)c.getNumericCellValue());
		}
		String itCode = getItemCodeFromRow(row);
		
		return AppState.getFileBackup().getGTIN(itCode);
	}
	
	private String getUnitFromRow(Row row) {
		String unit = AppState.getFileBackup().getUnit(getItemCodeFromRow(row));
		
		Cell c = row.getCell(itemDescriptionColumn);
		if (c != null) {
			String s = c.getStringCellValue();
			if (s.contains(",")) {
				int startIndex = s.indexOf(",") + 1;
				int endIndex = s.indexOf("GLOBAL", startIndex);
				if (endIndex < 0) {
					endIndex = s.length();
				}
				unit = s.substring(startIndex, endIndex);
			}
		}
		
		return unit;
	}
	
	private float getQtyFromRow(Row row) {
		return (float) row.getCell(quantityColumn).getNumericCellValue();
	}
	
	private float getPriceFromRow(Row row) {
		return (float) row.getCell(priceColumn).getNumericCellValue();
	}
	
	private Date getPackDateFromRow(Row row) {
		Cell cell = row.getCell(packDateColumn);
		String pd;
		try {
			 pd = cell.getStringCellValue();
			 return DateImp.parseDate(pd);
		}
		catch (IllegalStateException e) {
			pd = cell.getDateCellValue().toString();
			return DateImp.parseCellDate(pd);
		}
	}
	
	private Date getShipDateFromRow(Row row) {
		Cell cell = row.getCell(shipDateColumn);
		String pd;
		try {
			 pd = cell.getStringCellValue();
			 return DateImp.parseDate(pd);
		}
		catch (IllegalStateException e) {
			pd = cell.getDateCellValue().toString();
			return DateImp.parseCellDate(pd);
		}
	}
	
	private String getPONumFromRow(Row row) {
		Cell c = row.getCell(poColumn);
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
