package freshStart;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame {
	private OrderDisplay display;
	private LabelFormat format;
	private JFrame frame;

	public void showOrderDisplay(OrderDisplay orderDisplay, ArrayList<Order> orders, LabelFormat lf) {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		HPanel panel = new HPanel();
		display = orderDisplay;
		format = lf;
		panel.add(display.getOrderDisplay(orders));
		addPrintButton(panel);
		
		frame.add(panel);
		frame.setSize(new Dimension(700,500));
		frame.setVisible(true);
	}
	
	private void addPrintButton(JPanel parent) {
		JButton printButton = new JButton("Print Selected Labels");
		printButton.addActionListener(new PrintListener());
		parent.add(printButton);
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PrintSettingsDialog dialog = new PrintSettingsDialog(display.getItemsSelected(), format);
			dialog.showPrintDialog();
		}
		
	}
	
	public boolean isWindowOpen() {
		return frame.isVisible();
	}
}
