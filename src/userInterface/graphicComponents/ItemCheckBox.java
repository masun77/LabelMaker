package userInterface.graphicComponents;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import main.AppState;
import userInterface.Utilities;

public class ItemCheckBox extends JCheckBox {
	private final Dimension CHECK_SIZE = new Dimension(15,15);
	
	public ItemCheckBox(int row) {
		addItemListener(new ItemCheckListener(row, this));
		Utilities.setMinMax(this, CHECK_SIZE);
	}
	
	private class ItemCheckListener implements ItemListener {
		private int rowNum;
		private JCheckBox button;
		
		public ItemCheckListener(int rn, JCheckBox btn) {
			rowNum = rn;
			button = btn;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			boolean currSelection = button.isSelected();
			ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = AppState.getCheckBoxArray();
			ArrayList<PrintCheckBox> row = checkBoxArray.get(rowNum);
			for (int r = 0; r < row.size(); r++) {
				PrintCheckBox box = row.get(r);
				if (box.isEnabled()) {
					box.setSelected(currSelection);
				}
			}
		}
	}
}

