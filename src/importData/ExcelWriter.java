package importData;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import main.Item;
import main.Order;

public class ExcelWriter {
	private String fileName = "resources/OrderBackup.xlsx";
	private HashMap<HeaderOption, Integer> headerColumns = new HashMap<>();
	private ExcelFormat format;

	public void writeOrders(ArrayList<Order> orders, ExcelFormat form) {
		headerColumns = new HashMap<>();
		format = form;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Current Orders");
	              
        writeHeaders(sheet);
        writeOrders(orders, sheet);
        writeToFile(workbook);        
	}
	
	private void writeHeaders(XSSFSheet sheet) {
        ArrayList<ExcelHeader> headers = format.getHeaders();
        Row headerRow = sheet.createRow(format.getHeaderRowIndex());
        for (int h = 0; h < headers.size(); h++) {
        	ExcelHeader header = headers.get(h);
            Cell cell = headerRow.createCell(h);
            cell.setCellValue(header.getHeader());
            headerColumns.put(header.getHeaderType(), h);
        }
	}
	
	private void writeOrders(ArrayList<Order> orders, XSSFSheet sheet) {
		int rowIndex = format.getDataStartRow();
		for (Order order: orders) {
			for (Item item: order.getItems()) {
				Row currentRow = sheet.createRow(rowIndex);
				writeItemInfo(currentRow, item, order);
				rowIndex += 1;
			}
		}
	}
	
	private void writeItemInfo(Row row, Item item, Order order) {
		for (HeaderOption header: headerColumns.keySet()) {
			Cell cell = row.createCell(headerColumns.get(header));
			String value = getValue(header, item, order);
			cell.setCellValue(value);
		}
	}
	
	private void writeToFile(Workbook workbook) {
		try {
        	FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();
            workbook.close();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	private String getValue(HeaderOption headerType, Item item, Order order) {
		switch (headerType) {
		case TYPE:
			return format.getTypeToSave();
		case COMPANY:
			return item.getCompany();
		case SHIP_DATE:
			return item.getShipDate().getDateMMDDYYYY();
		case INVOICE_NUMBER:
			return order.getInvoiceNum() + "";
		case PO_NUMBER:
			return order.getPONum();
		case SHIP_VIA:
			return order.getShipVia();
		case GTIN:
			return item.getGtin();
		case ITEM_CODE:
			return item.getItemCode();
		case ITEM_DESCRIPTION:
			return item.getProductName() + ", " + item.getUnit();
		case QUANTITY:
			return item.getQuantity() + "";
		case PRICE:
			return item.getPrice() + "";
		case UNIT:
			return item.getUnit();
		case PRODUCT_NAME:
			return item.getProductName();
		default:
			return "";
		}
	}
}
