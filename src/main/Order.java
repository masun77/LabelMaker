package main;

public class Order {
	private String gtin;
	private String customer;
	private String productName;
	private Date packDate;
	
	public Order() {
		customer = "None";
		productName = "Nothing";
		gtin = "0000000000000";
		packDate = new DateImp();
	}
	
	public Order(String cust, String pn, String gt, Date pd) {
		customer = cust;
		productName = pn;
		gtin = gt;
		packDate = pd;
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
	
	
}
