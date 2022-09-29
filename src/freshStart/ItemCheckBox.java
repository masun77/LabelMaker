package freshStart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class ItemCheckBox extends JCheckBox {
	private ArrayList<Item> itemsSelected;
	private Item item;
	
	public ItemCheckBox(Item i, ArrayList<Item> itemsSelect) {
		item = i;
		itemsSelected = itemsSelect;
		addActionListener(new ItemCheckListener(this));
	}
	
	@Override
	public void setSelected(boolean selection) {
		super.setSelected(selection);
		selectionHelper(this);
	}
	
	private void selectionHelper(JCheckBox box) {
		if (box.isSelected()) {
			if (!itemsSelected.contains(item)) {
				itemsSelected.add(item);
				System.out.println("selected: " + item.getQuantity() + item.getProductName() + " " + item.getCompany());		
			}
		}
		else {
			itemsSelected.remove(item);
		}
	}
	
	private class ItemCheckListener implements ActionListener {
		private JCheckBox box;
		
		public ItemCheckListener(JCheckBox b) {
			box = b; 
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			selectionHelper(box);
		}
		
	}
}
