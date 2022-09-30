/**
 * ItemRowCheckBox
 * A checkbox extending JCheckBox, representing
 * whether a row of items is currently selected.
 * When it is selected or deselected, the items in its row
 * are also selected or deselected. 
 */

package display;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class ItemRowCheckBox extends JCheckBox {
	private ArrayList<JCheckBox> itemCheckBoxes = new ArrayList<>();
	
	public ItemRowCheckBox() {
		addActionListener(new ItemRowListener(this));
	}
	
	/**
	 * Add a checkbox to this row. It will be selected and deselected
	 * when this ItemRowCheckBox is selected and deselected. 
	 * @param itemBox the checkbox to add to this row
	 */
	public void addItemCheckBox(JCheckBox itemBox) {
		itemCheckBoxes.add(itemBox);
	}
	
	/**
	 * Set all the boxes in this row to the given boolean
	 * @param selection the value to set the selection of the boxes in this row to
	 */
	private void setChildBoxes(boolean selection) {
		for (JCheckBox itemBox: itemCheckBoxes) {
			itemBox.setSelected(selection);
		}
	}
	
	/**
	 * When this checkbox is selected, sets the selection of all its children to 
	 * match.
	 */
	private class ItemRowListener implements ActionListener {
		private JCheckBox box;
		
		public ItemRowListener(JCheckBox b) {
			box = b;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (box.isSelected()) {
				setChildBoxes(true);
			}
			else {
				setChildBoxes(false);				
			}
		}
		
	}
}
