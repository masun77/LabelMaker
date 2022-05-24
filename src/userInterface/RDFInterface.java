package userInterface;

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Item;
import main.Order;

public class RDFInterface implements UserInterface {
	ArrayList<Order> orders = new ArrayList<Order>();
	Order newOrder = new Order(null);
	ArrayList<String> gtins = new ArrayList<String>();
	ArrayList<String> prodNames = new ArrayList<String>();
	JFrame orderDisplay = new JFrame("Label Program");
	JFrame orderEntry = new JFrame("Enter New Order");

	// todo: update interface with new orders (vs recreating the whole thing)
	
	@Override
	public void showInterface(ArrayList<Order> ords) {
		orders.addAll(ords); // is this going to duplicate stuff?  or just call it once at start of program - reading from
		// existing csv for example
		
		initializeOrderDisplay();		
		initializeEntryForm();
		
		System.out.println("displayed");
	}
	
	private void initializeOrderDisplay() {
		orderDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(addButtons());
		mainPanel.add(showOrders());
		DisplayUtilities.localPack(mainPanel);
		
		JScrollPane scrollPane = new JScrollPane(mainPanel);
				
		orderDisplay.add(scrollPane);
		orderDisplay.setSize(new Dimension(1000,700));
		
		// todo change
		orderDisplay.setVisible(false);
		orderEntry.setVisible(true);
	}
	
	private void initializeEntryForm() {
		JPanel entryForm = new EntryForm(new HomeButtonListener(), new OrderSaveButtonListener(), newOrder);
		JScrollPane scrollPane = new JScrollPane(entryForm);
		orderEntry.add(scrollPane);
		orderEntry.setSize(new Dimension(1000,700));
		orderEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	private Component addButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JButton enterOrderButton = new JButton("Add new order");
		enterOrderButton.addActionListener(new EntryButtonListener());
		enterOrderButton.setMinimumSize(new Dimension(100,50));
		buttonPanel.add(enterOrderButton);
		return buttonPanel;
	}
	
	private class EntryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orderDisplay.setVisible(false);
			orderEntry.setVisible(true);
		}
	}
	
	private class HomeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orderEntry.setVisible(false);
			orderDisplay.setVisible(true);
		}
	}
	
	private class OrderSaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orderEntry.setVisible(false);
			orderDisplay.setVisible(true);
			updateOrders();
		}
	}
		
	private Component showOrders() {
		JPanel orderPanel = new JPanel();
		orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
		orderPanel.add(getColumnNames());
		ArrayList<ArrayList<Integer>> displayArray = getDisplayArray();
		addRows(displayArray, orderPanel);
		DisplayUtilities.localPack(orderPanel);
		
		return orderPanel;
	}
	
	private void updateOrders() {
		// TODO
	}
	
	private Component getColumnNames() {
		JPanel headerRow = new JPanel();
		headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
		ArrayList<String> colNames = getCompanyNames();
		for(int n = 0; n < colNames.size(); n++) {
			headerRow.add(new Label(colNames.get(n)));
		}
		headerRow.setMinimumSize(new Dimension(colNames.size()*10,10));
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
	
	private void addRows(ArrayList<ArrayList<Integer>> displayArray, Container orderPanel) {
		for (int r = 0; r < displayArray.size(); r++) {
			ArrayList<Integer> row = displayArray.get(r);
			JPanel rowPanel = new JPanel();
			rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
			rowPanel.add(new Label(prodNames.get(r)));
			for (int it = 0; it < row.size(); it++) {
				rowPanel.add(new Label(Integer.toString(row.get(it))));
			}
			rowPanel.setMinimumSize(new Dimension(row.size()*80,displayArray.size()*30));
			orderPanel.add(rowPanel);
		}
	}
}
