package main;


import labels.DateImp;
import labels.LabelView;
import labels.LabelViewerImp;
import labels.Labelable;
import printing.PrintManager;
import userInterface.RDFInterface;
import userInterface.UserInterface;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {		
		// Enter orders
		 
		// Display existing orders
//		ArrayList<Order> orders = new ArrayList<Order>();
//		orders.add(new Order(getItemArrayList(
//				new RDFItem("Long company name", "Arugula", "3 lbs", "00000123456789", new DateImp(4,2,2022), 1))));
//		orders.add(new Order(getItemArrayList(new RDFItem())));
//		orders.add(new Order(getItemArrayList(
//				new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022), 5))));
//		orders.add(new Order(getItemArrayList(
//				new RDFItem("Kris", "Kale", "12 bunches", "474747", new DateImp(4,20,2022), 4))));
//		orders.add(new Order(new ArrayList<Item>(Arrays.asList(
//				new RDFItem("Yao", "Kale", "10 lb case", "474747", new DateImp(1,1,2022), 2),
//				new RDFItem("Yao", "Arugula", "10 lb case", "00000123456789", new DateImp(1,1,2022), 3)))));
//		UserInterface ui = new RDFInterface();
//		ui.showInterface(orders);
		
		// Select labels to print
		
		// Display labels
//		LabelView lv = new LabelViewerImp();
//		lv.showLabels(getItemArrayList(new RDFItem()));
				
		// Print labels
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022)));
		PrintManager pm = new PrintManager();
		pm.printLabels(items);
		
		// Export to QB
		
		// other manipulation of existing orders?
		// multi-user
	}
	
	private static ArrayList<Item> getItemArrayList(Item i) {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(i);
		return items;
	}
	
	private static ArrayList<Order> getOrderArrayList(Order o) {
		ArrayList<Order> ord = new ArrayList<Order>();
		ord.add(o);
		return ord;
	}

}
