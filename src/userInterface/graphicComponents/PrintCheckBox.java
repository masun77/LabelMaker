package userInterface.graphicComponents;

import java.awt.Dimension;

import javax.swing.JCheckBox;

import labels.LabelableItem;
import userInterface.Utilities;

public class PrintCheckBox extends JCheckBox {
	LabelableItem item;
	private final Dimension CHECK_SIZE = new Dimension(15,15);
	
	public PrintCheckBox(LabelableItem it) {
		item = it;
		Utilities.setMinMax(this, CHECK_SIZE);
	}
	
	public LabelableItem getItem() {
		return item;
	}
}
