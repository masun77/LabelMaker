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
	private ArrayList<Order> ordersSelected = new ArrayList<Order>();
	private ArrayList<JCheckBox> companyCheckBoxes = new ArrayList<>();
	private ArrayList<Order> allOrders = new ArrayList<>();
	
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
			addCompanyAndSelection(p, o, i);
			addStringsToPanel(p, o.toString());
			panel.add(p);
		}
		panel.setPreferredSize(new Dimension(allOrders.size() * 200,500));
		return panel;
	}
	
	private void addCompanyAndSelection(JPanel panel, Order order, int index) {
		HPanel compPanel = new HPanel();
		JCheckBox box = new JCheckBox(); 
		box.addActionListener(new CompanyCheckListener(box, index));
		companyCheckBoxes.add(box);
		compPanel.add(box);
		compPanel.add(new JLabel(order.getCompany()));
		panel.add(compPanel);
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
	
	public ArrayList<Order> getOrdersSelected() {
		return ordersSelected;
	}
	
	private class CompanyCheckListener implements ActionListener {
		private int index;
		private JCheckBox box;
		
		public CompanyCheckListener(JCheckBox b, int i) {
			index = i;
			box = b;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				ordersSelected.add(allOrders.get(index));
			}
			else {
				ordersSelected.remove(allOrders.get(index));
			}
		}
		
	}
}
