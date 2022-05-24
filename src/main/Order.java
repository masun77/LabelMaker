package main;

import java.util.ArrayList;

public class Order {
	private ArrayList<Item> items = null;
	private String company = "";
	
	public Order(ArrayList<Item> its) {
		items = its;
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
	
	public void setItems(ArrayList<Item> newIts) {
		items = newIts;
	}
	
	public void printItems() {
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).stringRep());
		}
	}
}
