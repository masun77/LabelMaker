package labels;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.AppState;
import main.Item;
import printing.LabelPrinter;
import userInterface.AppFunction;
import userInterface.Utilities;
import userInterface.graphicComponents.PrintCheckBox;
import userInterface.graphicComponents.VPanel;

public class LabelViewerImp implements AppFunction {
	private ArrayList<LabelableItem> items;
	private LabelPrinter pm;
	
	private JFrame f = new JFrame("View Labels");
	private JPanel mainPanel = new VPanel();
	
	public LabelViewerImp() {
		pm = AppState.getPrinter();
	}

	@Override
	public Container getMainContent() {
		return mainPanel;
	}

	@Override
	public void refresh() {
		// do nothing
	}

	@Override
	public void showFunction() {
		items = getCheckedItems();
		
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
	
	private ArrayList<LabelableItem> getCheckedItems() {
		ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = AppState.getCheckBoxArray();
		ArrayList<LabelableItem> items = new ArrayList<LabelableItem>();

		if (checkBoxArray.size() > 0) {
			int cols = checkBoxArray.get(0).size();
			for (int row = 0; row < checkBoxArray.size(); row++) {
				for (int col = 0; col < cols; col++) {
					PrintCheckBox check = checkBoxArray.get(row).get(col);
					if (check.isSelected()) {
						items.add(check.getItem());
					}
				}
			}		
		}
		return items;
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			pm.printLabels(items);
		}
		
	}
}
