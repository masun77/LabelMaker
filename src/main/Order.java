package main;

import java.util.ArrayList;

public class Order {
	private ArrayList<Item> items = null;
	private String company = "";
	
	public Order(ArrayList<Item> its) {
		items = its;
		company = its.get(0).getCustomer();
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public String getCompany() {
		return company;
	}
}
