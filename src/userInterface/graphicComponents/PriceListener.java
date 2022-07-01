package userInterface.graphicComponents;

import java.awt.TextField;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import userInterface.EntryForm;

public class PriceListener implements FocusListener {
	private TextField amount;
	private TextField quantity;
	private TextField price;
	private EntryForm parent;
	
	public PriceListener(TextField prc, TextField qty, TextField amt, EntryForm ef) {
		amount = amt;
		quantity = qty;
		price = prc;
		parent = ef;
	}

	@Override
	public void focusGained(FocusEvent e) {	}

	@Override
	public void focusLost(FocusEvent e) {
		String priceText = price.getText();
		String qtyText = quantity.getText();
		if (priceText.length() > 0 && qtyText.length() > 0) {
			amount.setText(Float.toString(
					Float.parseFloat(qtyText) * Float.parseFloat(priceText)));
		}		
		else {
			amount.setText("");
		}
		parent.updateTotal();
	}
}
