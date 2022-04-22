package main;

/**
 * Represents a single item in an order
 * with a name, GTIN, pack date, unit, and voice pick code.
 */
public class Item {
	private String gtin;
	private String customer;
	private String productName;
	private Date packDate;
	private String unit;
	private String voicePickCode = "";
	
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
