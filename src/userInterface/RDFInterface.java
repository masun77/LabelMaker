package userInterface;

import java.awt.Component;
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

import export.DataSaver;
import main.Item;
import main.Order;
import main.Utilities;

public class RDFInterface implements UserInterface {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private JFrame orderDisplay = new JFrame("Label Program");
	private JFrame orderEntry = new JFrame("Enter New Order");
	private JPanel orderPanel = null;
	private JPanel displayMainPanel = new JPanel();
	private final String[] SAVE_FILE_NAMES = {"resources/Orders1.csv", "resources/Orders2.csv"};
	private int currentSaveFile = 0;
	private final Dimension NAME_SIZE = new Dimension(80,15);
	private final Dimension WINDOW_SIZE = new Dimension(500,300);
	
	public RDFInterface(ArrayList<Order> ords) {
		orders.addAll(ords); 
		showInterface();
	}
	
	public RDFInterface(String csvname) {
		orders = DataSaver.readOrdersFromCSV(csvname);
		showInterface();
	}
	
	public RDFInterface() {
		orders = DataSaver.readOrdersFromCSV(SAVE_FILE_NAMES[currentSaveFile]);
		showInterface();
	}
	
	@Override
	public void showInterface() {
		
		initializeOrderDisplay();		
		initializeEntryForm();
		
		System.out.println("displaying UI");
	}
	
	private void initializeOrderDisplay() {
		orderDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		displayMainPanel.setLayout(new BoxLayout(displayMainPanel, BoxLayout.Y_AXIS));
		
		setOrderArray();
		displayMainPanel.add(addButtons());
		displayMainPanel.add(orderPanel);
		Utilities.localVPack(displayMainPanel);
		
		JScrollPane scrollPane = new JScrollPane(displayMainPanel);
				
		orderDisplay.add(scrollPane);
		orderDisplay.setSize(WINDOW_SIZE);
		
		orderDisplay.setVisible(true);
		orderEntry.setVisible(false);
	}
	
	private void initializeEntryForm() {
		JPanel entryForm = new EntryForm(new HomeButtonListener(), new OrderSaveButtonListener(), orders);
		JScrollPane scrollPane = new JScrollPane(entryForm);
		orderEntry.add(scrollPane);
		orderEntry.setSize(WINDOW_SIZE);
		orderEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
		
	private Component addButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JButton enterOrderButton = new JButton("Add new order");
		enterOrderButton.addActionListener(new EntryButtonListener());
		enterOrderButton.setMinimumSize(new Dimension(100,50));
		buttonPanel.add(enterOrderButton);
		Utilities.localHPack(buttonPanel);
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
			displayMainPanel.remove(1);
			setOrderArray();
			displayMainPanel.add(orderPanel);
			Utilities.localVPack(displayMainPanel);
			orderDisplay.setVisible(true);
			DataSaver.writeOrdersToCSV(orders, SAVE_FILE_NAMES[currentSaveFile]);
			currentSaveFile = currentSaveFile == 0? 1 : 0;
		}
	}
		
	private void setOrderArray() {
		orderPanel = new JPanel();
		orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
		orderPanel.add(getColumnNames());
		ArrayList<ArrayList<Integer>> displayArray = getDisplayArray();
		addRows(displayArray);
		Utilities.localVPack(orderPanel);
	}
	
	private Component getColumnNames() {
		JPanel headerRow = new JPanel();
		headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
		ArrayList<String> colNames = getCompanyNames();
		for(int n = 0; n < colNames.size(); n++) {
			Label nameLabel = new Label(colNames.get(n));
			Utilities.setMinMax(nameLabel, NAME_SIZE);
			headerRow.add(nameLabel);
		}
		Utilities.localHPack(headerRow);
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
		gtins = new ArrayList<String>();
		prodNames = new ArrayList<String>();
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
	
	private void addRows(ArrayList<ArrayList<Integer>> displayArray) {
		for (int r = 0; r < displayArray.size(); r++) {
			ArrayList<Integer> row = displayArray.get(r);
			JPanel rowPanel = new JPanel();
			rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
			Label prodName = new Label(prodNames.get(r));
			Utilities.setMinMax(prodName, NAME_SIZE);
			rowPanel.add(prodName);
			for (int it = 0; it < row.size(); it++) {
				Label qty = new Label(Integer.toString(row.get(it)));
				Utilities.setMinMax(qty, NAME_SIZE);
				rowPanel.add(qty);
			}
			Utilities.localHPack(rowPanel);
			orderPanel.add(rowPanel);
		}
	}
}
