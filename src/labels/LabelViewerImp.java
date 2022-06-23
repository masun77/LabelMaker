package labels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Item;
import main.Order;
import printing.PrintManager;
import userInterface.Utilities;

public class LabelViewerImp implements LabelView {
	ArrayList<LabelableItem> items;

	@Override
	public void showLabels(ArrayList<LabelableItem> its) {
		items = its;
		
		JFrame f = new JFrame("View Labels");

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		JButton printButton = new JButton("Print Labels");
		printButton.addActionListener(new PrintListener());
		mainPanel.add(printButton);

		for (int i = 0; i < items.size(); i++) {
			LabelableItem currItem = items.get(i);
			for (int j = 0; j < ((Item) currItem).getQuantityRoundedUp(); j++) {
				mainPanel.add(currItem.getLabel());
			}
		}
		Utilities.localVPack(mainPanel);
		
		JScrollPane scrollPane = new JScrollPane(mainPanel);
				
		f.add(scrollPane);
		f.setSize(new Dimension(500,700));
		f.setVisible(true);
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PrintManager pm = new PrintManager();
			pm.printLabels(items);
		}
		
	}
}
