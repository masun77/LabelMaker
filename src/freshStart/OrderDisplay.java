/**
 * OrderDisplay
 * Create a visual display of the information in the orders given.
 * Allow the user to select orders, items, and individual items. 
 */

package freshStart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class OrderDisplay {
	private ArrayList<Order> allOrders = new ArrayList<>();
	private ArrayList<Order> ordersSelected = new ArrayList<Order>();
	private ArrayList<Item> itemsSelected = new ArrayList<>();
	private Border blackline = BorderFactory.createLineBorder(Color.black);
	private ArrayList<ArrayList<ItemCheckBox>> boxes = new ArrayList<>();
	private ArrayList<Item> itemColumn = new ArrayList<>();
	
	/**
	 * Display the information about the given orders to the screen.
	 * @param orders the orders to display
	 */
	public JFrame displayOrders(ArrayList<Order> orders) {
		JFrame frame = new JFrame();
		
		allOrders = orders;
		JPanel ordsPanel = getOrderPanel();
		
		frame.add(ordsPanel);
		frame.setSize(ordsPanel.getPreferredSize());
		frame.setVisible(true);
		return frame;
	}
	
	/**
	 * Put the information from each order into a separate panel,
	 * and return the parent panel containing all of the order panels.
	 * Each order panel contains the orders name and order checkbox,
	 * and each item in the order with an individual checkbox. 
	 * @return a panel with one child panel containing the information for each order
	 */
	private JPanel getOrderPanel() {
		HPanel panel = new HPanel();
		
		addItemColumn(panel);
		
		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);
			VPanel p = new VPanel();
			p.setBorder(blackline);
			addCompanyAndSelection(p, o, i);
			addItemsToPanel(p, o);
			panel.add(p);
		}
		panel.setPreferredSize(new Dimension(allOrders.size() * 200,700));
		return panel;
	}
	
	private void addItemColumn(JPanel parent) {
		getItemsWithUniqueGtins();
		
		VPanel column = new VPanel();
		
		for (int n = 0; n < itemColumn.size(); n++) {
			Item item = itemColumn.get(n);
			HPanel titlePanel = new HPanel();
			
			JCheckBox box = new JCheckBox();
			box.addActionListener(new RowCheckListener(box, n));		
			titlePanel.add(box);
			VPanel namePanel = new VPanel();
			namePanel.add(new JLabel(item.getProductName()));
			namePanel.add(new JLabel(item.getUnit()));
			titlePanel.add(namePanel);
			column.add(titlePanel);
		}
		parent.add(column);
	}
	
	private void getItemsWithUniqueGtins() {
		ArrayList<String> gtins = new ArrayList<>();
		itemColumn = new ArrayList<>();
		for (Order o: allOrders) {
			for (Item i: o.getItems()) {
				if (!gtins.contains(i.getGtin())) {
					gtins.add(i.getGtin());
					itemColumn.add(i);
				}
			}
		}
	}
	
	/**
	 * Add the company name and company checkbox to a panel.
	 * @param panel the panel to add to
	 * @param order the order whose name to add
	 */
	private void addCompanyAndSelection(JPanel panel, Order order, int i) {
		HPanel compPanel = new HPanel();
		JCheckBox box = new JCheckBox(); 
		box.addActionListener(new OrderCheckListener(box, order, i));
		compPanel.add(box);
		compPanel.add(new JLabel(order.getCompany()));
		panel.add(compPanel);
	}
	
	/**
	 * For the given order, add each item in the order to the panel
	 * with its own checkbox.
	 * @param panel the panel to add to
	 * @param order the order to add the items for
	 */
	private void addItemsToPanel(JPanel panel, Order order) {
		ArrayList<Item> items = order.getItems();
		for (int n = 0; n < itemColumn.size(); n++) {
			if (boxes.size() < itemColumn.size()) {
				boxes.add(new ArrayList<>());
			}
			Item item = itemColumn.get(n);
			HPanel itemPanel = new HPanel();
			ItemCheckBox box = new ItemCheckBox(null);
			JLabel qtyLabel = new JLabel();
			boolean containsItem = false;
			for (Item i: items) {
				if (item.getGtin().equals(i.getGtin())) { 
					box = new ItemCheckBox(i);
					box.addActionListener(new ItemCheckListener(box, i));
					qtyLabel.setText(Float.toString(i.getQuantity()));
					containsItem = true;
				}
			}
			if (!containsItem) {
				box.setEnabled(false);
				qtyLabel.setText("0");
			}
			itemPanel.add(box);
			boxes.get(n).add(box);
			itemPanel.add(qtyLabel);
			panel.add(itemPanel);
		}
	}
	
	/**
	 * OrderCheckListener
	 * When selected, adds its associated order to the list of orders selected. 
	 * When deselected, removes its associated order from the list of orders selected. 
	 */
	private class OrderCheckListener implements ActionListener {
		private Order order;
		private JCheckBox box;
		private int index;
		
		public OrderCheckListener(JCheckBox b, Order o, int i) {
			order = o;
			box = b;
			index = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				ordersSelected.add(order);
				for (Item i: order.getItems()) {
					if (!itemsSelected.contains(i)) {
						itemsSelected.add(i);
					}
				}
				for (ArrayList<ItemCheckBox> row: boxes) {
					JCheckBox jcb = row.get(index);
					if (jcb.isEnabled()) {
						jcb.setSelected(true);
					}
				}
			}
			else {
				ordersSelected.remove(order);
				itemsSelected.removeAll(order.getItems());
				for (ArrayList<ItemCheckBox> row: boxes) {
					row.get(index).setSelected(false);
				}
			}
		}
	}
	
	private class RowCheckListener implements ActionListener {
		private JCheckBox box;
		private int index;
		
		public RowCheckListener(JCheckBox b, int i) {
			box = b;
			index = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<ItemCheckBox> row = boxes.get(index);
			if (box.isSelected()) {				
				for (ItemCheckBox checkBox: row) {
					if (checkBox.isEnabled()) {
						checkBox.setSelected(true);
						if (!itemsSelected.contains(checkBox.getItem())) {
							itemsSelected.add(checkBox.getItem());
						}
					}
				}
			}
			else {
				for (ItemCheckBox checkBox: row) {
					checkBox.setSelected(false);
					itemsSelected.remove(checkBox.getItem());
				}
			}
		}
	}
	
	/**
	 * ItemCheckListener
	 * When selected, adds its associated item to the list of items selected. 
	 * When deselected, removes its associated item from the list of items selected. 
	 */
	private class ItemCheckListener implements ActionListener {
		private Item item;
		private JCheckBox box;
		
		public ItemCheckListener(JCheckBox b, Item i) {
			item = i;
			box = b;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				itemsSelected.add(item);
			}
			else {
				itemsSelected.remove(item);
			}
		}
	}
	
	// Getters and setters	
	public ArrayList<Order> getOrdersSelected() {
		return ordersSelected;
	}
	
	public ArrayList<Item> getItemsSelected() {
		return itemsSelected;
	}
}
