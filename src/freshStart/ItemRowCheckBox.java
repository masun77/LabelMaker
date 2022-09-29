package freshStart;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class ItemRowCheckBox extends JCheckBox {
	private ArrayList<JCheckBox> itemCheckBoxes = new ArrayList<>();
	
	public ItemRowCheckBox() {
		addActionListener(new ItemRowListener(this));
	}
	
	public void addItemCheckBox(JCheckBox itemBox) {
		itemCheckBoxes.add(itemBox);
	}
	
	private class ItemRowListener implements ActionListener {
		private JCheckBox box;
		
		public ItemRowListener(JCheckBox b) {
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
				setChildBoxes(true);
			}
			else {
				setChildBoxes(false);				
			}
		}
		
	}
}
