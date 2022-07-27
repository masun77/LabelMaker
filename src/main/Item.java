package main;

import labels.Date;
import labels.DateImp;
import labels.LabelableItem;

/**
 * Represents a single item in an order
 * with a name, GTIN, pack date, unit, and voice pick code.
 */
public abstract class Item implements LabelableItem {
	protected String gtin = "00818181020000";
	protected String customer;
	protected String productName;
	protected Date pickDate;
	protected Date packDate;
	protected Date shipDate;
	protected String lotNum;
	protected String unit;
	protected String voicePickCode = "";
	protected float quantity;
	protected float price;
	protected String itemCode = "";
	
	public Item(String cust, String itCd, String prodName, String gt, String ut,
			float qty, float pr,
			Date pickDt, Date packDt, Date shipDt) {
		customer = cust;
		itemCode = itCd;
		productName = prodName;
		gtin = gt;
		unit = ut;
		quantity = qty;
		price = pr;
		pickDate = pickDt;
		packDate = packDt;
		shipDate = shipDt;
	}
	
	public Item() {
		customer = "None";
		productName = "Nothing";
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

	public Date getPickDate() {
		return pickDate;
	}

	public void setPickDate(Date pickDate) {
		this.pickDate = pickDate;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	public String getLotNum() {
		return lotNum;
	}

	public void setLotNum(String lotNum) {
		this.lotNum = lotNum;
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
		return customer + " " + packDate.getDateMMDDYYYY() + " " + shipDate.getDateMMDDYYYY()
				+ " " + quantity + " " + productName +
				" " + unit + " " + gtin + " " + price;
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
