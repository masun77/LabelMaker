/**
 * OrderDisplay
 * Create a visual display of the information in the orders given.
 * Allows the user to select items, item rows, and orders. 
 */

package display;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import main.Item;
import main.Order;

public class OrderDisplay {
	private ArrayList<Order> allOrders = new ArrayList<>();
	private ArrayList<Order> ordersSelected = new ArrayList<Order>();
	private ArrayList<Item> itemsSelected = new ArrayList<>();
	private HashMap<String, ItemRowCheckBox> itemCheckBoxes = new HashMap<>();
	private ArrayList<String> alphabetizedItemNames;
	private JScrollPane scrollPane = new JScrollPane();
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	
	/**
	 * Display the information about the given orders to the screen.
	 * @param orders the orders to display
	 */
	public JFrame displayOrders(ArrayList<Order> orders) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		scrollPane = getOrderDisplay(orders);
		
		frame.add(scrollPane);
		frame.setSize(new Dimension(700,500));  
		frame.setVisible(true);
		return frame;
	}
	
	/**
	 * Given a list of orders, return a JScrollpane containing the orders in a table with 
	 * companies as the column headers and items as the row headers
	 * @param orders the orders to display
	 * @return a JScrollPane displaying the orders in a table
	 */
	public JScrollPane getOrderDisplay(ArrayList<Order> orders) {
		scrollPane = new JScrollPane();
		allOrders = orders;
		JPanel ordsPanel = getOrderPanel();
		scrollPane.setViewportView(ordsPanel);
		return scrollPane;
	}
	
	/**
	 * Put the information from each order into a separate panel,
	 * and return the parent panel containing all of the order panels.
	 * @return a panel with one child panel containing the information for each order
	 */
	private JPanel getOrderPanel() {
		HPanel panel = new HPanel();
		
		addItemNamesToPanel(panel);
		addOrdersToPanel(panel);
		panel.setPreferredSize(new Dimension(allOrders.size() * 200,alphabetizedItemNames.size() *25));
		
		return panel;
	}
	
	/**
	 * Add all the orders to the parent panel with their name and items.
	 * @param parent the panel to add the orders to. 
	 */
	private void addOrdersToPanel(JPanel parent) {
		HPanel companyNameHeader = new HPanel();
		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);
			VPanel panel = new VPanel();
			panel.setBorder(blackline);
			addOrderToPanel(companyNameHeader, panel, o);
			parent.add(panel);			
		}
		scrollPane.setColumnHeaderView(companyNameHeader);
	}
	
	/**
	 * Add the names of all the unique items in the orders
	 * to the panel as the row headers. 
	 * @param parent the panel to add to. 
	 */
	private void addItemNamesToPanel(JPanel parent) {
		findUniqueItemNames();
		
		VPanel itemList = new VPanel();
		for (String name: alphabetizedItemNames) {
			HPanel namePanel = new HPanel();
			ItemRowCheckBox box = new ItemRowCheckBox();
			namePanel.add(box);
			namePanel.add(new JLabel(name));
			itemList.add(namePanel);
			
			itemCheckBoxes.put(name, box);
		}

		itemList.setPreferredSize(new Dimension(100, alphabetizedItemNames.size() * 25));
		scrollPane.setRowHeaderView(itemList);
	}
	
	/**
	 * Find the names of all the unique items in the orders and save them
	 * to a list. 
	 */
	private void findUniqueItemNames() {
		alphabetizedItemNames = new ArrayList<>();
		for (Order o: allOrders) {
			for (Item i: o.getItems()) {
				String name = i.getProductName();
				if (!alphabetizedItemNames.contains(name)) {
					insertAlphabetical(alphabetizedItemNames, name);
				}
			}
		}
	}
	
	/**
	 * Insert the given String into the list of Strings in alphabetical order. 
	 * @param list the list to insert into
	 * @param item the item to insert
	 */
	private void insertAlphabetical(ArrayList<String> list, String item) {
		int index = 0;
		while (index < list.size() && list.get(index).compareTo(item) < 0) {
			index += 1;
		}
		list.add(index, item);
	}
	
	/**
	 * Add the items from a single order to the given panel and add its company name
	 * to the given header panel.
	 * @param companyNameHeader the header panel to add the order's company name to
	 * @param panel the panel to add the order's items to
	 * @param order the order to add information from
	 */
	private void addOrderToPanel(JPanel companyNameHeader, JPanel panel, Order order) {
		HPanel compPanel = new HPanel();
		OrderCheckBox box = new OrderCheckBox(ordersSelected, order); 
		compPanel.add(box);
		compPanel.add(new JLabel(order.getCompany()));
		companyNameHeader.add(compPanel);
		addItemsToPanel(panel, order, box);
	}
	
	/**
	 * Add the items from the given order to the given panel. 
	 * @param parent the panel to add the items to
	 * @param order the order whose items to add
	 * @param parentBox the checkbox corresponding to this order. item checkboxes will be
	 * 		added as children of the parentBox, so they are check and unchecked when it is. 
	 */
	private void addItemsToPanel(JPanel parent, Order order, OrderCheckBox parentBox) {
		ArrayList<Item> currentItems = order.getItems();
		HashMap<String, Item> itemNameMap = getItemNameMap(currentItems);
		Set<String> names = itemNameMap.keySet();
		for (String s: alphabetizedItemNames) {
			JCheckBox box;
			JLabel label = new JLabel();
			if (names.contains(s)) {
				Item item = itemNameMap.get(s);
				box = new ItemCheckBox(item, itemsSelected);
				parentBox.addItemCheckBox(box);
				itemCheckBoxes.get(s).addItemCheckBox(box);
				label.setText("" + item.getQuantity());
			}
			else {
				box = new JCheckBox();
				box.setEnabled(false);
				label.setText("0");
			}
			HPanel itemPanel = new HPanel();
			itemPanel.add(box);
			itemPanel.add(label);
			parent.add(itemPanel);
		}
	}
	
	/**
	 * Return a map of the names of the items to the items in the given list
	 * @param items the list of items to map with their names
	 * @return a map of the name of each item in the given list, linked to that item. 
	 */
	private HashMap<String, Item> getItemNameMap(ArrayList<Item> items) {
		HashMap<String, Item> currItemNames = new HashMap<>();
		for (Item i: items) {
			currItemNames.put(i.getProductName(), i);
		}
		return currItemNames;
	}
	
	// Getters
	public ArrayList<Order> getOrdersSelected() {
		return ordersSelected;
	}
	
	public ArrayList<Item> getItemsSelected() {
		return itemsSelected;
	}
}
