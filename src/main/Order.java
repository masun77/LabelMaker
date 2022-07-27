package main;

import java.util.ArrayList;

import labels.Date;
import labels.DateImp;
import labels.LabelableItem;

public class Order {
	private ArrayList<LabelableItem> items = null;
	private String company = "";
	private String PONum = "";
	private String shipVia = "";
	private int invoiceNumber = 0;

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	
	public Order(ArrayList<LabelableItem> its) {
		items = its;
	}
	
	public Order() {
		items = new ArrayList<LabelableItem>();
	}
	
	public Order(String c, ArrayList<LabelableItem> its, String po, String sv) {
		items = its;
		PONum = po;
		shipVia = sv;
		company = c;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public ArrayList<LabelableItem> getItems() {
		return items;
	}
	
	public String getCompany() {
		if (items != null && items.size() > 0) {
			return items.get(0).getCustomer();
		}
		return "None";
	}
	
	public String getPONum() {
		return PONum;
	}
	
	public String getShipVia() {
		return shipVia;
	}
	
	public void setPONum(String po) {
		PONum = po;
	}
	
	public void setShipVia(String sv) {
		shipVia = sv;
	}
	
	public void setItems(ArrayList<LabelableItem> newIts) {
		items = newIts;
	}
	
	public void addItem(LabelableItem it) {
		items.add(it);
	}
	
	public void printItems() {
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).stringRep());
		}
	}
	
	public void printOrder() {
		System.out.println("Invoice num: " + invoiceNumber + "\n\tCompany:" + 
					company + "\n\tPO: " + PONum + "\n\tShip via: " + shipVia);
		printItems();
	}
	
	public LabelableItem getItem(String prodName) {
		for (LabelableItem i: items) {
			if (i.getProductName().equals(prodName)) {
				return i;
			}
		}
		return null;
	}
}
