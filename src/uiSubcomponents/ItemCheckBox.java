package uiSubcomponents;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import main.AppState;
import uiDisplay.Utilities;
import static main.AppListenerMessage.*;

public class ItemCheckBox extends JCheckBox {
	private final Dimension CHECK_SIZE = new Dimension(25,25);
	
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
			ArrayList<Boolean> itemSelected = AppState.getItemSelectedArray();
			itemSelected.set(rowNum, currSelection);
			ArrayList<ArrayList<Boolean>> checkBoxArray = AppState.getIndivItemSelectedArray();
			ArrayList<Boolean> row = checkBoxArray.get(rowNum);
			for (int r = 0; r < row.size(); r++) {
				row.set(r, currSelection);
			}
			AppState.sendMessage(UPDATE_CHECKBOXES);
		}
	}
}

