package main;

import java.util.ArrayList;

import labels.Date;
import labels.DateImp;

public class Order {
	private ArrayList<Item> items = null;
	private String company = "";
	private String PONum = "";
	private String shipVia = "";
	
	public void setCompany(String company) {
		this.company = company;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}

	private Date shipDate = new DateImp();
	
	public Order(ArrayList<Item> its) {
		items = its;
	}
	
	public Order() {
		items = new ArrayList<Item>();
	}
	
	public Order(ArrayList<Item> its, String po, String sv, Date d) {
		items = its;
		PONum = po;
		shipVia = sv;
		shipDate = d;
	}
	
	public ArrayList<Item> getItems() {
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
	
	public Date getShipDate() {
		return shipDate;
	}
	
	public void setShipVia(String sv) {
		shipVia = sv;
	}
	
	public void setItems(ArrayList<Item> newIts) {
		items = newIts;
	}
	
	public void addItem(Item it) {
		items.add(it);
	}
	
	public void printItems() {
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).stringRep());
		}
	}
	
	public void printOrder() {
		printItems();
	}
	
	public Item getItem(String prodName) {
		for (Item i: items) {
			if (i.getProductName().equals(prodName)) {
				return i;
			}
		}
		return null;
	}
}
