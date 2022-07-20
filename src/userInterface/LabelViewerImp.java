package userInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import labels.LabelableItem;
import main.AppState;
import main.Item;
import main.Order;
import printing.LabelPrinter;
import userInterface.graphicComponents.PrintCheckBox;
import userInterface.graphicComponents.VPanel;

public class LabelViewerImp implements SideFunction {
	private ArrayList<LabelableItem> items;
	private LabelPrinter pm;
	
	private JFrame f = new JFrame("View Labels");
	private JPanel mainPanel = new VPanel();
	private JPanel contentPanel = new VPanel();
	
	public LabelViewerImp() {
		pm = AppState.getPrinter();
		
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	        	JButton printButton = new JButton("Print Labels");
	     		printButton.addActionListener(new PrintListener());
	     		mainPanel.add(printButton);
		     }
		});
	}

	@Override
	public void showFunction() {
		if (mainPanel.getComponentCount() > 1) {
		   	mainPanel.remove(1);
		}
		
		items = getCheckedItems();
		
		contentPanel = new VPanel();
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

	@Override
	public void resetOrders() {
		pm = AppState.getPrinter();
	}

	@Override
	public void addOrder(Order o) {
		// do nothing
	}

	@Override
	public void removeOrder(Order o) {
		// do nothing
	}
}
