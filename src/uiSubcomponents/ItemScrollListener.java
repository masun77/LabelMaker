package uiSubcomponents;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;

public class ItemScrollListener implements AdjustmentListener {
	private JPanel orderPanel;
	
	public ItemScrollListener(JPanel op) {
		orderPanel = op;
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		System.out.println("vert: "+ e.getValue());
		
	}

}
