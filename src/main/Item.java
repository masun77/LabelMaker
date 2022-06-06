package main;

import labels.Date;
import labels.DateImp;
import labels.LabelableItem;

/**
 * Represents a single item in an order
 * with a name, GTIN, pack date, unit, and voice pick code.
 */
public abstract class Item implements LabelableItem {
	protected String gtin;
	protected String customer;
	protected String productName;
	protected Date packDate;
	protected String unit;
	protected String voicePickCode = "";
	protected float quantity;
	protected float price;
	protected String itemCode = "";
	
	public Item() {
		customer = "None";
		productName = "Nothing";
		gtin = "00818181020000";
		packDate = new DateImp();
		unit = "Empty";
		quantity = 1;
		price = 0;
	}
	
	public Item(String cust, String prodName, String unt, String gtnum, Date pkdate, float qty, float p, String itCd) {
		customer = cust;
		productName = prodName;
		gtin = gtnum;
		packDate = pkdate;
		unit = unt;
		quantity = qty;
		price = p;
		itemCode = itCd;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getGtin() {
		return gtin;
	}

	public String getCustomer() {
		return customer;
	}

	@Override
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
	
	public float getQuantity() {
		return quantity;
	}
	
	public int getQuantityRoundedUp() {
		int q = (int) quantity / 1;
		if (quantity > q) {
			return q + 1;
		}
		return q;
	}
	
	@Override
	public String stringRep() {
		return customer + " " + packDate.getDateMMDDYYYY() + " " + quantity + " " + productName + " " + unit + " " + gtin + " " + price;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
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

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}
