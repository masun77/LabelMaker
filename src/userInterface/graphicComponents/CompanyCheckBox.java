package userInterface.graphicComponents;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import main.AppState;
import userInterface.Utilities;

public class CompanyCheckBox extends JCheckBox {
	private int orderNum;
	private final Dimension CHECK_SIZE = new Dimension(15,15);
	
	public CompanyCheckBox(int o) {
		orderNum = o;
		addItemListener(new CompanyCheckListener(orderNum, this));
		Utilities.setMinMax(this, CHECK_SIZE);
	}
	
	private class CompanyCheckListener implements ItemListener {
		private int orderIndex;
		private JCheckBox button;
		
		public CompanyCheckListener(int ind, JCheckBox btn) {
			orderIndex = ind;
			button = btn;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			boolean currSelection = button.isSelected();
			ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = AppState.getCheckBoxArray();
			for (int r = 0; r < checkBoxArray.size(); r++) {
				PrintCheckBox box = checkBoxArray.get(r).get(orderIndex);
				if (box.isEnabled()) {
					box.setSelected(currSelection);
				}
			}
		}
	}
}
