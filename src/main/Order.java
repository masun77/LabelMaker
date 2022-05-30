package main;

import java.util.ArrayList;

public class Order {
	private ArrayList<Item> items = null;
	private String company = "";
	private String PONum = "";
	private String shipVia = "";
	
	public Order(ArrayList<Item> its) {
		items = its;
	}
	
	public Order() {
		items = new ArrayList<Item>();
	}
	
	public Order(ArrayList<Item> its, String po, String sv) {
		items = its;
		PONum = po;
		shipVia = sv;
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
		System.out.println(company + "\t" + PONum + "\t" + shipVia);
		printItems();
	}
	
	public Item getItem(String prodName) {
		for (Item i: items) {
			if (i.getCustomer().equals(prodName)) {
				return i;
			}
		}
		return null;
	}
}
