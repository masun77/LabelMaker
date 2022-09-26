/**
 * OrderDisplay
 * Create a visual display of the information in the orders given.
 */

package freshStart;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class OrderDisplay {
	/**
	 * Display the information about the given orders to the screen.
	 * @param orders the orders to display
	 */
	public void displayOrders(ArrayList<Order> orders) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // todo change
		
		JPanel ordsPanel = getOrderPanel(orders);
		
		frame.add(ordsPanel);
		frame.setSize(ordsPanel.getPreferredSize());
		frame.setVisible(true);
	}
	
	/**
	 * Put the information from each order into a separate panel,
	 * and return the parent panel containing all of the order panels.
	 * @param orders the orders to put into the panels
	 * @return a panel with one child panel containing the information for each order
	 */
	private JPanel getOrderPanel(ArrayList<Order> orders) {
		Border blackline = BorderFactory.createLineBorder(Color.black);
		HPanel panel = new HPanel();
		for (Order o: orders) {
			VPanel p = new VPanel();
			p.setBorder(blackline);
			addStringsToPanel(p, o.toString());
			panel.add(p);
		}
		panel.setPreferredSize(new Dimension(orders.size() * 200,500));
		return panel;
	}
	
	/**
	 * Given a string, add each new line in the string
	 * to the panel as a separate label.
	 * @param panel the panel to add the lines in the string to
	 * @param str the string to add to the panel
	 */
	private void addStringsToPanel(JPanel panel, String str) {
		while (str.contains("\n")) {
			int index = str.indexOf("\n");
			panel.add(new JLabel(str.substring(0, index)));
			str = str.substring(index+1);
		}
		panel.add(new JLabel(str));
	}
}
