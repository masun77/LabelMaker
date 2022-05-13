package main;

import labels.Date;
import labels.DateImp;

/**
 * Represents a single item in an order
 * with a name, GTIN, pack date, unit, and voice pick code.
 */
public class Item {
	protected String gtin;
	protected String customer;
	protected String productName;
	protected Date packDate;
	protected String unit;
	protected String voicePickCode = "";
	
	public Item() {
		customer = "None";
		productName = "Nothing";
		gtin = "00000818180000";
		packDate = new DateImp();
		unit = "Empty";
	}
	
	public Item(String cust, String pn, String ut, String gt, Date pd) {
		customer = cust;
		productName = pn;
		gtin = gt;
		packDate = pd;
		unit = ut;
	}

	public String getGtin() {
		return gtin;
	}

	public String getCustomer() {
		return customer;
	}

	public String getProductName() {
		return productName;
	}

	public Date getPackDate() {
		return packDate;
	}
	
	public String getUnit() {
		return unit;
	}
}
