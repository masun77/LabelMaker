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
import printing.LabelPrinter;
import printing.PrintManager;
import userInterface.Utilities;
import userInterface.VPanel;

public class LabelViewerImp implements LabelView {
	ArrayList<LabelableItem> items;
	LabelPrinter pm;
	
	public LabelViewerImp() {
		pm = new PrintManager();
	}

	@Override
	public void showLabels(ArrayList<LabelableItem> its) {
		items = its;
		
		JFrame f = new JFrame("View Labels");

		JPanel mainPanel = new VPanel();
		
		JButton printButton = new JButton("Print Labels");
		printButton.addActionListener(new PrintListener());
		mainPanel.add(printButton);
		
		JPanel contentPanel = new VPanel();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem currItem = items.get(i);
			for (int j = 0; j < ((Item) currItem).getQuantityRoundedUp(); j++) {
				contentPanel.add(currItem.getLabel());
			}
		}
		Utilities.localVPack(contentPanel);
		
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		mainPanel.add(scrollPane);
				
		f.add(mainPanel);
		f.setSize(new Dimension(500,700));
		f.setVisible(true);
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			pm.printLabels(items);
		}
		
	}
}
