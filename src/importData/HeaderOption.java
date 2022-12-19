/**
 * HeaderOption
 * The possible types of headers for reading info from an Excel file
 */

package importData;

public enum HeaderOption {
	TYPE, COMPANY, SHIP_DATE, INVOICE_NUMBER, PO_NUMBER, 
	SHIP_VIA, GTIN, ITEM_CODE, ITEM_DESCRIPTION, QUANTITY, PRICE,
	UNIT, PRODUCT_NAME;
}
