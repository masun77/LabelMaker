package main;

import java.util.ArrayList;

import labels.Labelable;

public class Utilities {
	public static ArrayList<Item> getItemArrayList(Item i) {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(i);
		return items;
	}
	
	public static ArrayList<Labelable> getLabelableArrayList(Item i) {
		ArrayList<Labelable> items = new ArrayList<Labelable>();
		items.add(i);
		return items;
	}
	
	public static ArrayList<Order> getOrderArrayList(Order o) {
		ArrayList<Order> ord = new ArrayList<Order>();
		ord.add(o);
		return ord;
	}
}
