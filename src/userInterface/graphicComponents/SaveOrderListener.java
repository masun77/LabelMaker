package userInterface.graphicComponents;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import userInterface.EntryForm;

public class SaveOrderListener implements ActionListener {
	private EntryForm parent;
	
	public SaveOrderListener(EntryForm ef) {
		parent = ef;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		parent.onSave();
	}
}
