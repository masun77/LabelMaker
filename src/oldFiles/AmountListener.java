package oldFiles;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AmountListener implements FocusListener {
	private EntryForm parent;
	
	public AmountListener(EntryForm ef) {
		parent = ef;
	}
	
	@Override
	public void focusGained(FocusEvent e) {	}

	@Override
	public void focusLost(FocusEvent e) {
		parent.updateTotal();
	}
}
