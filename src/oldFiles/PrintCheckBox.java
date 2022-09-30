package oldFiles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class PrintCheckBox extends JCheckBox {
	LabelableItem item;
	private final Dimension CHECK_SIZE = new Dimension(25,25);
	
	public PrintCheckBox() {
		item = null;
		Utilities.setMinMax(this, CHECK_SIZE);
		this.setEnabled(false);
		this.setBackground(Color.white);
	}
	
	public PrintCheckBox(LabelableItem it, int orderIndex, int itemIndex) {
		item = it;
		Utilities.setMinMax(this, CHECK_SIZE);
		addItemListener(new PrintCheckListener(this, orderIndex, itemIndex));
	}
	
	private class PrintCheckListener implements ItemListener {
		private int orderIndex;
		private int itemIndex;
		private JCheckBox box;
		
		public PrintCheckListener(JCheckBox b, int colIndex, int rowIndex) {
			orderIndex = colIndex;
			itemIndex = rowIndex;
			box = b;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			boolean currSelection = box.isSelected();
			ArrayList<ArrayList<Boolean>> checkBoxArray = AppState.getIndivItemSelectedArray();
			checkBoxArray.get(itemIndex).set(orderIndex, currSelection);
		}
	}
	
	public void setItem(LabelableItem it) {
		item = it;
	}
	
	public LabelableItem getItem() {
		return item;
	}
	
	
}
