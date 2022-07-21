package userInterface.graphicComponents;

import java.awt.Dimension;

import javax.swing.JCheckBox;

import labels.LabelableItem;
import userInterface.Utilities;

public class PrintCheckBox extends JCheckBox {
	LabelableItem item;
	private final Dimension CHECK_SIZE = new Dimension(25,25);
	
	public PrintCheckBox() {
		item = null;
		Utilities.setMinMax(this, CHECK_SIZE);
		this.setEnabled(false);
	}
	
	public PrintCheckBox(LabelableItem it) {
		item = it;
		Utilities.setMinMax(this, CHECK_SIZE);
	}
	
	public void setItem(LabelableItem it) {
		item = it;
	}
	
	public LabelableItem getItem() {
		return item;
	}
}
