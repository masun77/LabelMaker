/**
 * OrderCheckBox
 * A class that extends JCheckBox. Represents whether its order
 * has been selected. When it is selected or deselected, the items corresponding to it
 * are also selected or deselected. 
 */

package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import main.Order;

public class OrderCheckBox extends JCheckBox {
	private static final long serialVersionUID = 1L;
	private ArrayList<JCheckBox> itemCheckBoxes = new ArrayList<>();
	private ArrayList<Order> ordersSelected;
	private Order order;
	
	public OrderCheckBox(ArrayList<Order> ordersSel, Order ord) {
		ordersSelected = ordersSel;
		order = ord;
		addActionListener(new CompanyCheckListener(this));
	}
	
	/**
	 * Add a checkbox for an item that is part of this order, 
	 * so that its state will be changed when the order checkbox's state is changed.
	 * @param itemBox the item checkbox to add to this order. 
	 */
	public void addItemCheckBox(JCheckBox itemBox) {
		itemCheckBoxes.add(itemBox);
	}
	
	@Override
	public void setSelected(boolean selection) {
		super.setSelected(selection);
		setChildBoxes(selection);
		if (selection) {
			if (!ordersSelected.contains(order)) {
				ordersSelected.add(order);
			}
		}
		else {
			ordersSelected.remove(order);
		}
	}
	
	/**
	 * For each item checkbox corresponding to this order,
	 * set its state to the same as this order. 
	 * @param selection
	 */
	private void setChildBoxes(boolean selection) {
		for (JCheckBox itemBox: itemCheckBoxes) {
			itemBox.setSelected(selection);
		}
	}
	
	/**
	 * When this box is selected or deselected, call
	 * setSelected so that the children boxes are set. 
	 */
	private class CompanyCheckListener implements ActionListener {
		private JCheckBox box;
		
		public CompanyCheckListener(JCheckBox b) {
			box = b;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				box.setSelected(true);
			}
			else {
				box.setSelected(false);			
			}
		}
		
	}
	
}
