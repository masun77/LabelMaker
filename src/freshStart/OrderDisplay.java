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
	private ArrayList<JCheckBox> companyCheckBoxes = new ArrayList<>();
	private ArrayList<ArrayList<JCheckBox>> itemCheckBoxes = new ArrayList<>();
	
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
		Border blackline = BorderFactory.createLineBorder(Color.black);
		HPanel panel = new HPanel();
		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);
			VPanel p = new VPanel();
			p.setBorder(blackline);
			addCompanyAndSelection(p, o);
			addItemsToPanel(p, o);
			panel.add(p);
		}
		panel.setPreferredSize(new Dimension(allOrders.size() * 200,500));
		return panel;
	}
	
	/**
	 * Add the company name and company checkbox to a panel.
	 * @param panel the panel to add to
	 * @param order the order whose name to add
	 */
	private void addCompanyAndSelection(JPanel panel, Order order) {
		HPanel compPanel = new HPanel();
		JCheckBox box = new JCheckBox(); 
		box.addActionListener(new OrderCheckListener(box, order));
		companyCheckBoxes.add(box);
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
		for (int i = 0; i < items.size(); i++) {
			Item currItem = items.get(i);
			HPanel itemPanel = new HPanel();
			JCheckBox box = new JCheckBox(); 
			box.addActionListener(new ItemCheckListener(box, currItem));
			itemPanel.add(box);
			itemPanel.add(new JLabel(currItem.getQuantity() + " " + currItem.getProductName()));
			itemPanel.setPreferredSize(new Dimension(150, 50));
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
		
		public OrderCheckListener(JCheckBox b, Order o) {
			order = o;
			box = b;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				ordersSelected.add(order);
			}
			else {
				ordersSelected.remove(order);
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
