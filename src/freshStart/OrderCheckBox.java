package freshStart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class OrderCheckBox extends JCheckBox {
	private ArrayList<JCheckBox> itemCheckBoxes = new ArrayList<>();
	private ArrayList<Order> ordersSelected;
	private Order order;
	
	public OrderCheckBox(ArrayList<Order> ordersSel, Order ord) {
		ordersSelected = ordersSel;
		order = ord;
		addActionListener(new CompanyCheckListener(this));
	}
	
	public void addItemCheckBox(JCheckBox itemBox) {
		itemCheckBoxes.add(itemBox);
	}
	
	private class CompanyCheckListener implements ActionListener {
		private JCheckBox box;
		
		public CompanyCheckListener(JCheckBox b) {
			box = b;
		}
		
		private void setChildBoxes(boolean selection) {
			for (JCheckBox itemBox: itemCheckBoxes) {
				itemBox.setSelected(selection);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				ordersSelected.add(order);
				setChildBoxes(true);
			}
			else {
				ordersSelected.remove(order);
				setChildBoxes(false);				
			}
		}
		
	}
	
}
