package main;

import labels.Date;
import labels.DateImp;
import labels.Labelable;

/**
 * Represents a single item in an order
 * with a name, GTIN, pack date, unit, and voice pick code.
 */
public abstract class Item implements Labelable {
	protected String gtin;
	protected String customer;
	protected String productName;
	protected Date packDate;
	protected String unit;
	protected String voicePickCode = "";
	protected int quantity;
	private float price;
	
	public Item() {
		customer = "None";
		productName = "Nothing";
		gtin = "00000818180000";
		packDate = new DateImp();
		unit = "Empty";
		quantity = 0;
		price = 0;
	}
	
	public Item(String cust, String prodName, String unt, String gtnum, Date pkdate, int qty, float price) {
		customer = cust;
		productName = prodName;
		gtin = gtnum;
		packDate = pkdate;
		unit = unt;
		quantity = qty;
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
	
	public float getPrice() {
		return price;
	}

	public Date getPackDate() {
		return packDate;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public String stringRep() {
		return customer + " " + packDate.getDateMMDDYYYY() + " " + quantity + " " + productName + " " + unit + " " + gtin + " " + price;
	}

	public void setGtin(String gtin) {
		this.gtin = gtin;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setPackDate(Date packDate) {
		this.packDate = packDate;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setVoicePickCode(String voicePickCode) {
		this.voicePickCode = voicePickCode;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
