package freshStart;

import labels.Date;

public class Item {
	private String company;
	private Date shipDate;
	private String productName;
	private String itemCode = "";
	private String gtin = "00818181020000";
	private String unit;
	private float quantity;
	private float price;
	
	public Item(String comp, Date sd, String pname, String code, String gt, String ut, float qty, float prc) {
		company = comp;
		shipDate = sd;
		productName = pname;
		itemCode = code;
		gtin = gt;
		unit = ut;
		quantity = qty;
		price = prc;
	}
}
