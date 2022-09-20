/**
 * Item
 * Represents an item in an order.
 */

package freshStart;

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
	
	@Override
	public String toString() {
		return "Item: " + company + " " + shipDate.getDateMMDDYYYY()
		+ "\n\t\t" + quantity + " " + productName + "\n\t\tItem code: "
		+ itemCode + " GTIN: " + gtin
		+ "\n\t\t" + unit + " at $" + price;
	}

	public String getCompany() {
		return company;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public String getProductName() {
		return productName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getGtin() {
		return gtin;
	}

	public String getUnit() {
		return unit;
	}

	public float getQuantity() {
		return quantity;
	}

	public float getPrice() {
		return price;
	}
	
	
}
