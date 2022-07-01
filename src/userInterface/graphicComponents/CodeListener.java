package userInterface.graphicComponents;

import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import main.AppState;

public class CodeListener implements FocusListener {
	private TextField description;
	private TextField itemCode;
	
	public CodeListener(TextField ic, TextField descrip) {
		description = descrip;
		itemCode = ic;
	}

	@Override
	public void focusGained(FocusEvent e) {	}

	@Override
	public void focusLost(FocusEvent e) {
		if (itemCode.getText().length() > 0) {
			String descrip = AppState.getFileBackup().getItemDescription(itemCode.getText());
			description.setText(descrip);					
		}		
	}
}
