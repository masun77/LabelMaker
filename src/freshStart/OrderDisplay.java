/**
 * OrderDisplay
 * Create a visual display of the information in the orders given.
 */

package freshStart;

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

public class OrderDisplay {
	private ArrayList<Order> allOrders = new ArrayList<>();
	private ArrayList<Order> ordersSelected = new ArrayList<Order>();
	private ArrayList<Item> itemsSelected = new ArrayList<>();
	private HashMap<String, ItemRowCheckBox> itemCheckBoxes = new HashMap<>();
	private ArrayList<String> alphabetizedItemNames;
	private JScrollPane scrollPane = new JScrollPane();
	
	/**
	 * Display the information about the given orders to the screen.
	 * @param orders the orders to display
	 */
	public JFrame displayOrders(ArrayList<Order> orders) {
		JFrame frame = new JFrame();
		scrollPane = new JScrollPane();
		
		allOrders = orders;
		JPanel ordsPanel = getOrderPanel();
		scrollPane.setViewportView(ordsPanel);
		
		frame.add(scrollPane);
		frame.setSize(ordsPanel.getPreferredSize());  // todo
		frame.setVisible(true);
		return frame;
	}
	
	/**
	 * Put the information from each order into a separate panel,
	 * and return the parent panel containing all of the order panels.
	 * @return a panel with one child panel containing the information for each order
	 */
	private JPanel getOrderPanel() {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		HPanel panel = new HPanel();
		
		addItemNamesToPanel(panel);

		HPanel companyNameHeader = new HPanel();
		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);
			VPanel p = new VPanel();
			p.setBorder(blackline);
			addOrderToPanel(companyNameHeader, p, o);
			panel.add(p);			
		}
		panel.setPreferredSize(new Dimension(allOrders.size() * 200,500));
		scrollPane.setColumnHeaderView(companyNameHeader);
		return panel;
	}
	
	private void addItemNamesToPanel(JPanel parent) {
		alphabetizedItemNames = new ArrayList<>();
		for (Order o: allOrders) {
			for (Item i: o.getItems()) {
				String name = i.getProductName();
				if (!alphabetizedItemNames.contains(name)) {
					insertAlphabetical(alphabetizedItemNames, name);
				}
			}
		}
		
		VPanel itemList = new VPanel();
		itemList.add(new JLabel("Items"));
		for (String name: alphabetizedItemNames) {
			HPanel namePanel = new HPanel();
			ItemRowCheckBox box = new ItemRowCheckBox();
			namePanel.add(box);
			namePanel.add(new JLabel(name));
			itemList.add(namePanel);
			
			itemCheckBoxes.put(name, box);
		}

		itemList.setPreferredSize(new Dimension(100,500));
		scrollPane.setRowHeaderView(itemList);
	}
	
	private void insertAlphabetical(ArrayList<String> list, String item) {
		int index = 0;
		while (index < list.size() && list.get(index).compareTo(item) < 0) {
			index += 1;
		}
		list.add(index, item);
	}
	
	private void addOrderToPanel(JPanel companyNameHeader, JPanel panel, Order order) {
		HPanel compPanel = new HPanel();
		OrderCheckBox box = new OrderCheckBox(ordersSelected, order); 
		compPanel.add(box);
		compPanel.add(new JLabel(order.getCompany()));
		companyNameHeader.add(compPanel);
		addItemsToPanel(panel, order, box);
	}
	
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
	
	private HashMap<String, Item> getItemNameMap(ArrayList<Item> items) {
		HashMap<String, Item> currItemNames = new HashMap<>();
		for (Item i: items) {
			currItemNames.put(i.getProductName(), i);
		}
		return currItemNames;
	}
	
	public ArrayList<Order> getOrdersSelected() {
		return ordersSelected;
	}
	
	public ArrayList<Item> getItemsSelected() {
		return itemsSelected;
	}
}
