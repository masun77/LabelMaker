package oldFiles;

import static oldFiles.AppListenerMessage.*;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class CompanyCheckBox extends JCheckBox {
	private int orderNum;
	private final Dimension CHECK_SIZE = new Dimension(25,25);
	
	public CompanyCheckBox(int o) {
		orderNum = o;
		addItemListener(new CompanyCheckListener(orderNum, this));
		Utilities.setMinMax(this, CHECK_SIZE);
	}
	
	private class CompanyCheckListener implements ItemListener {
		private int orderIndex;
		private JCheckBox box;
		
		public CompanyCheckListener(int ind, JCheckBox btn) {
			orderIndex = ind;
			box = btn;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			boolean currSelection = box.isSelected();
			ArrayList<Boolean> companySelected = AppState.getCompanySelectedArray();
			companySelected.set(orderNum, currSelection);
			ArrayList<ArrayList<Boolean>> checkBoxArray = AppState.getIndivItemSelectedArray();
			for (int r = 0; r < checkBoxArray.size(); r++) {
				checkBoxArray.get(r).set(orderIndex, currSelection);
			}
			AppState.sendMessage(UPDATE_CHECKBOXES);
		}
	}
}
