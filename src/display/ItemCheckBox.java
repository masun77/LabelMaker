/**
 * ItemCheckBox
 * Represents whether an individual item has been selected or not. 
 */

package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import main.Item;

public class ItemCheckBox extends JCheckBox {
	private static final long serialVersionUID = 1L;
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
	
	/**
	 * If this box is being selected, add it to the 
	 * itemsSelected list if it is not already present. 
	 * If it is deselected, remove it from that list. 
	 * @param box
	 */
	private void selectionHelper(JCheckBox box) {
		if (box.isSelected()) {
			if (!itemsSelected.contains(item)) {
				itemsSelected.add(item);
			}
		}
		else {
			itemsSelected.remove(item);
		}
	}
	
	/**
	 * When this item is selected, call the function 
	 * to add or remove it from the itemsSelected list. 
	 */
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
