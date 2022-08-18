package uiSubcomponents;

import java.awt.Component;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

public class DetailScrollListener implements AdjustmentListener {
	private JPanel detailPanel;
	private JPanel companyNameRow;
	private JScrollBar detailBar;
	private int oldValue = 0;
	private int numOrders;
	
	public DetailScrollListener(JPanel cn, JPanel dp, JScrollBar jb, int no) {
		companyNameRow = cn;
		detailPanel = dp;
		detailBar = jb;
		numOrders = no;
	}
	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		int ratio = 80 / numOrders;
		int value = e.getValue();
		if (value > (oldValue + ratio)) {
			Component order = companyNameRow.getComponent(0);
			companyNameRow.remove(order);
			companyNameRow.add(order);
			companyNameRow.validate();
			System.out.println("shift right");
		}
		if (value < (oldValue - ratio)) {
			int index = companyNameRow.getComponentCount() - 1;
			Component order = companyNameRow.getComponent(index);
			companyNameRow.remove(order);
			companyNameRow.add(order,0);
			companyNameRow.validate();
			System.out.println("shift left");
		}		
		
		System.out.println(e.getValue());
	}

}
