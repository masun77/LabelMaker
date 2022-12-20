/**
 * OrderDisplay
 * Create a visual display of the information in the orders given.
 * Allows the user to select items, item rows, and orders. 
 */

package display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalCheckBoxIcon;

import main.Item;
import main.Order;

public class OrderDisplay {
	private ArrayList<Order> allOrders = new ArrayList<>();
	private ArrayList<Order> ordersSelected = new ArrayList<Order>();
	private ArrayList<Item> itemsSelected = new ArrayList<>();
	private ConcurrentHashMap<String, ItemRowCheckBox> itemCheckBoxes = new ConcurrentHashMap<>();
	private ArrayList<JCheckBox> allBoxes = new ArrayList<>();
	private ArrayList<String> alphabetizedItemNameUnit;
	private JScrollPane scrollPane = new JScrollPane();
	private final Border blackline = BorderFactory.createLineBorder(Color.black);
	private ConcurrentHashMap<Order, Integer> orderWidths = new ConcurrentHashMap<>();
	
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
		allBoxes = new ArrayList<>();
		itemsSelected = new ArrayList<>();
		ordersSelected = new ArrayList<>();
		itemCheckBoxes = new ConcurrentHashMap<>();
		allOrders = orders;

		addItemNamesToPanel();	
		JPanel ordsPanel = getOrderPanel();
		scrollPane.setViewportView(ordsPanel);
		return scrollPane;
	}
	
	/**
	 * Put the information from each order into a separate panel,
	 * and return the parent panel containing all of the order panels.
	 * @return a panel with one child panel for each order, 
	 * 			containing the information for that order
	 */
	private JPanel getOrderPanel() {
		VPanel holder = new VPanel();
		HPanel parent = new HPanel();
		HPanel companyNameHeader = new HPanel();
		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);
			VPanel orderColumn = new VPanel();
			orderColumn.setBorder(blackline);
			addIndividualOrder(companyNameHeader, orderColumn, o);
			parent.add(orderColumn);			
		}
		scrollPane.setColumnHeaderView(companyNameHeader);
		parent.setAlignmentX(Component.LEFT_ALIGNMENT);
		holder.add(parent);
		holder.add(Box.createVerticalGlue());
		return holder;
	}
	
	/**
	 * Add the names of all the unique items in the orders
	 * to the panel as the row headers. 
	 * @param parent the panel to add to. 
	 */
	private void addItemNamesToPanel() {
		findUniqueItemNames();
		
		HPanel totalItemList = new HPanel();
		VPanel itemUnitList = new VPanel();
		VPanel itemNameList = new VPanel();
		for (String name: alphabetizedItemNameUnit) {
			HPanel namePanel = new HPanel();
			namePanel.setBorder(null);
			namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			ItemRowCheckBox box = new ItemRowCheckBox();
			allBoxes.add(box);
			namePanel.add(box);
			int commaIndex = name.indexOf(",");
			namePanel.add(new TextSizeLabel(name.substring(0,commaIndex), true, 300));
			itemNameList.add(namePanel);
			itemUnitList.add(new TextSizeLabel(name.substring(commaIndex + 2), true, 100));
			
			itemCheckBoxes.put(name, box);
		}

		totalItemList.add(itemNameList);
		totalItemList.add(itemUnitList);
		scrollPane.setRowHeaderView(totalItemList);
	}
	
	/**
	 * Find the names of all the unique items in the orders and save them
	 * to a list. 
	 */
	private void findUniqueItemNames() {
		alphabetizedItemNameUnit = new ArrayList<>();
		for (Order o: allOrders) {
			for (Item i: o.getItems()) {
				String name = i.getProductName() + ", " + i.getUnit();
				if (!alphabetizedItemNameUnit.contains(name)) {
					insertAlphabetical(alphabetizedItemNameUnit, name);
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
	private void addIndividualOrder(JPanel companyNameHeader, JPanel panel, Order order) {
		OrderCheckBox box = new OrderCheckBox(ordersSelected, order); 
		addCompanyHeader(companyNameHeader, box, order);
		addItemsToPanel(panel, order, box);
	}
	
	/**
	 * To the given parent panel, add the checkbox and order company,
	 * followed by the order date, PO num, and ship via method.
	 * @param parent the parent to add the info to
	 * @param orderBox the checkbox to add
	 * @param order the order to add information for
	 */
	private void addCompanyHeader(JPanel parent, JCheckBox orderBox, Order order) {
		VPanel totalPanel = new VPanel();
		totalPanel.setBorder(blackline);
		
		HPanel compPanel = new HPanel();
		compPanel.setBorder(null);
		compPanel.add(orderBox);
		allBoxes.add(orderBox);
		compPanel.add(new TextSizeLabel(order.getCompany()));
		totalPanel.add(compPanel);
		
		JLabel otherInfo = new TextSizeLabel(" " + order.getShipDate().getMMDD() + ", "
				+ order.getPONum()
				+ ", " + order.getShipVia() + " ");
		otherInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
		totalPanel.add(otherInfo);
		
		parent.add(totalPanel);
		
		orderWidths.put(order, (int) totalPanel.getPreferredSize().getWidth());	
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
		ConcurrentHashMap<String, Item> itemNameMap = getItemNameMap(currentItems);
		Set<String> names = itemNameMap.keySet();
		for (String s: alphabetizedItemNameUnit) {
			JCheckBox box;
			SetSizeLabel label = new SetSizeLabel();
			label.setAllSizes(new Dimension(orderWidths.get(order)-17, 28));
			if (names.contains(s)) {
				Item item = itemNameMap.get(s);
				box = new ItemCheckBox(item, itemsSelected);
				allBoxes.add(box);
				parentBox.addItemCheckBox(box);
				itemCheckBoxes.get(s).addItemCheckBox(box);
				label.setText("" + item.getQuantity());
			}
			else {
				box = new JCheckBox();
				box.setEnabled(false);
				box.setVisible(false);
				label.setText("");
			}
			HPanel itemPanel = new HPanel();
			itemPanel.add(box);
			itemPanel.add(label);
			itemPanel.setBorder(null);
			parent.add(itemPanel);
		}
	}
	
	/**
	 * Return a map of the names of the items to the items in the given list
	 * @param items the list of items to map with their names
	 * @return a map of the name of each item in the given list, linked to that item. 
	 */
	private ConcurrentHashMap<String, Item> getItemNameMap(ArrayList<Item> items) {
		ConcurrentHashMap<String, Item> currItemNames = new ConcurrentHashMap<>();
		for (Item i: items) {
			currItemNames.put(i.getProductName() + ", " + i.getUnit(), i);
		}
		return currItemNames;
	}
	
	/**
	 * Clears the selected items, orders, and their checkboxes. 
	 */
	public void clearSelections() {
		ordersSelected.removeAll(ordersSelected);
		itemsSelected.removeAll(itemsSelected);
		for (JCheckBox box: allBoxes) {
			box.setSelected(false);
		}
	}
	
	public ArrayList<Order> deleteSelectedOrders() {
		allOrders.removeAll(ordersSelected);
		return allOrders;
	}
	
	public void selectAllBoxes() {
		for (JCheckBox box: allBoxes) {
			box.setSelected(true);
		}
	}
	
	// Getters
	public ArrayList<Order> getOrdersSelected() {
		return ordersSelected;
	}
	
	public ArrayList<Item> getItemsSelected() {
		return itemsSelected;
	}
}
