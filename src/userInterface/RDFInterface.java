package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Item;
import main.Order;

public class RDFInterface implements UserInterface {
	ArrayList<Order> orders = new ArrayList<Order>();
	ArrayList<String> gtins = new ArrayList<String>();
	ArrayList<String> prodNames = new ArrayList<String>();

	// todo: update interface(vs recreating the whole thing)
	
	@Override
	public void showInterface(ArrayList<Order> ords) {
		orders.addAll(ords); // is this going to duplicate stuff? 
		
		JFrame f = new JFrame("Label Program");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(addButtons());
		mainPanel.add(showOrders());
		//localPack(mainPanel);
		
		JScrollPane scrollPane = new JScrollPane(mainPanel);
				
		f.add(scrollPane);
		f.setSize(new Dimension(500,700));
		f.setVisible(true);
		
		System.out.println("displayed");
	}
	
	private Component addButtons() {
		return new JPanel();
	}
	
	private Component showOrders() {
		JPanel orderDisplay = new JPanel();
		orderDisplay.setLayout(new BoxLayout(orderDisplay, BoxLayout.Y_AXIS));
		orderDisplay.add(getColumnNames());
		ArrayList<ArrayList<Integer>> displayArray = getDisplayArray();
		addRows(displayArray, orderDisplay);
		
		return orderDisplay;
	}
	
	private Component getColumnNames() {
		JPanel headerRow = new JPanel();
		headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
		ArrayList<String> colNames = getCompanyNames();
		for(int n = 0; n < colNames.size(); n++) {
			headerRow.add(new Label(colNames.get(n)));
		}
		return headerRow;
	}
	
	private ArrayList<String> getCompanyNames() {
		ArrayList<String> colNames = new ArrayList<String>();
		colNames.add("Company:");
		for (int ord = 0; ord < orders.size(); ord++) {
			String comp = orders.get(ord).getCompany();
			if (!colNames.contains(comp)) {
				colNames.add(comp);
			}
		}
		return colNames;
	}
	
	private ArrayList<ArrayList<Integer>> getDisplayArray() {
		ArrayList<ArrayList<Integer>> displayArray = new ArrayList<ArrayList<Integer>>();
		for (int ord = 0; ord < orders.size(); ord++) {
			Order order = orders.get(ord);
			ArrayList<Item> items = order.getItems();
			for (int it = 0; it < items.size(); it++) {
				Item item = items.get(it);
				int index = gtins.indexOf(item.getGtin());
				ArrayList<Integer> currArr = new ArrayList<Integer>();
				if (index < 0) {
					gtins.add(item.getGtin());
					prodNames.add(item.getProductName());
					currArr = createZeroArray(orders.size());
					displayArray.add(currArr);
				}
				else {
					currArr = displayArray.get(index);
				}
				currArr.set(ord, item.getQuantity());
			}
		}
		return displayArray;
	}
	
	private ArrayList<Integer> createZeroArray(int numZeros) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(0);
		}
		return arr;
	}
	
	private void addRows(ArrayList<ArrayList<Integer>> displayArray, Container orderDisplay) {
		for (int r = 0; r < displayArray.size(); r++) {
			ArrayList<Integer> row = displayArray.get(r);
			JPanel rowPanel = new JPanel();
			rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
			rowPanel.add(new Label(prodNames.get(r)));
			for (int it = 0; it < row.size(); it++) {
				rowPanel.add(new Label(Integer.toString(row.get(it))));
			}
			orderDisplay.add(rowPanel);
		}
	}
}
