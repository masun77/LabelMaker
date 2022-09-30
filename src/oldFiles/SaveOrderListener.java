package oldFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
