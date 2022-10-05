/**
 * Write orders to an Excel file saved in the
 * resources folder in the given format. 
 */

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

	/**
	 * Write the given orders to an Excel file in resources
	 * in the given format. 
	 * @param orders the orders to write to the Excel file
	 * @param form the format to write the orders in
	 */
	public void writeOrders(ArrayList<Order> orders, ExcelFormat form) {
		headerColumns = new HashMap<>();
		format = form;
		
		XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("Current Orders");
	              
        writeHeaders(sheet);
        writeOrders(orders, sheet);
        writeToFile(workbook);        
	}
	
	/**
	 * Write the header row for the Excel sheet
	 * @param sheet the sheet to write the headers on
	 */
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
	
	/**
	 * Write the items from the orders given to the given sheet 
	 * @param orders the orders to write to the Excel sheet
	 * @param sheet the sheet to write to
	 */
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
	
	/**
	 * Write the data for the given item and order into the given row.
	 * @param row the row to write the data to
	 * @param item the item to write the data for
	 * @param order the order containing the item, to get the order number, PO number, etc. 
	 */
	private void writeItemInfo(Row row, Item item, Order order) {
		for (HeaderOption header: headerColumns.keySet()) {
			Cell cell = row.createCell(headerColumns.get(header));
			String value = getValue(header, item, order);
			cell.setCellValue(value);
		}
	}
	
	/**
	 * Write the workbook object to an Excel file
	 * @param workbook the workbook to write to
	 */
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
	
	/**
	 * For the given headerType, get the corresponding value from the 
	 * given item and order
	 * @param headerType the type of data to get
	 * @param item the item to get it from
	 * @param order the order containing the item
	 * @return the value corresponding to that header type, eg. the quantity or invoice number
	 */
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
