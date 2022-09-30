package oldFiles;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import display.VPanel;

public class LabelViewDisplay implements LabelDisplayInterface {
	private JFrame f = new JFrame("View Labels");
	private JPanel mainPanel = new VPanel();
	private JPanel contentPanel = new VPanel();
	private PrintListener pl;
	
	public LabelViewDisplay() {
		JButton printButton = new JButton("Print Labels");
		pl = new PrintListener(AppState.getPrinter());
	    printButton.addActionListener(pl);
	    mainPanel.add(printButton);
	}

	@Override
	public void clearOldLabels() {
		if (mainPanel.getComponentCount() > 1) {
			mainPanel.remove(1);
		}
	}

	@Override
	public void setPrinter(LabelPrinter pm) {
		pl.setPrinter(pm);
	}

	@Override
	public void showNewLabels(ArrayList<LabelableItem> items) {
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
}
