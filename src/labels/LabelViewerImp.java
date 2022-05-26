package labels;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.Item;
import main.Utilities;

public class LabelViewerImp implements LabelView {

	@Override
	public void showLabels(ArrayList<Labelable> items) {
		JFrame f = new JFrame("View Labels");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < items.size(); i++) {
			Labelable currItem = items.get(i);
			for (int j = 0; j < ((Item) currItem).getQuantity(); j++) {
				mainPanel.add(currItem.getLabel());
			}
		}
		Utilities.localPack(mainPanel);
		
		JScrollPane scrollPane = new JScrollPane(mainPanel);
				
		f.add(scrollPane);
		f.setSize(new Dimension(500,700));
		f.setVisible(true);
	}
}
