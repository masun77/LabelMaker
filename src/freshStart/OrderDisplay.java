/**
 * OrderDisplay
 * Create a visual display of the information in the orders given.
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
	 * @return a panel with one child panel containing the information for each order
	 */
	private JPanel getOrderPanel() {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		HPanel panel = new HPanel();
		for (int i = 0; i < allOrders.size(); i++) {
			Order o = allOrders.get(i);
			VPanel p = new VPanel();
			p.setBorder(blackline);
			addOrderToPanel(p, o);
			panel.add(p);
		}
		panel.setPreferredSize(new Dimension(allOrders.size() * 200,500));
		return panel;
	}
	
	private void addOrderToPanel(JPanel panel, Order order) {
		HPanel compPanel = new HPanel();
		OrderCheckBox box = new OrderCheckBox(ordersSelected, order); 
		compPanel.add(box);
		compPanel.add(new JLabel(order.getCompany()));
		panel.add(compPanel);
		addItemsToPanel(panel, order, box);
	}
	
	private void addItemsToPanel(JPanel parent, Order order, OrderCheckBox parentBox) {
		for (Item i: order.getItems()) {
			JCheckBox box = new ItemCheckBox(i, itemsSelected);
			parentBox.addItemCheckBox(box);
			HPanel itemPanel = new HPanel();
			itemPanel.add(box);
			itemPanel.add(new JLabel(i.getQuantity() + " " + i.getProductName()));
			parent.add(itemPanel);
		}
	}
	
	public ArrayList<Order> getOrdersSelected() {
		return ordersSelected;
	}
	
	public ArrayList<Item> getItemsSelected() {
		return itemsSelected;
	}
}
