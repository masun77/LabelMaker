package freshStart;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PrintSettings {
	private LabelView lv = new LabelViewerG2();
	private ArrayList<Item> itemsToPrint = new ArrayList<>();
	private LabelFormat format;
	
	public PrintSettings(ArrayList<Item> items, LabelFormat lf) {
		itemsToPrint = items;
		format = lf;
	}
	
	public void showPrintDialog() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // todo change when using
		frame.setSize(700, 1000);
		JPanel wholeDialog = new HPanel();
		addLabels(wholeDialog);
		addSettings(wholeDialog);
		frame.add(wholeDialog);
		frame.setVisible(true);
	}
	
	private void addLabels(JPanel parent) {
		JPanel labelsPanel = new VPanel();
		for (Item i: itemsToPrint) {
			ArrayList<JPanel> labels = lv.getLabelsForItem(i, format);
			for (JPanel label: labels) {
				labelsPanel.add(label);
			}
			System.out.println("adding for " + i.getProductName());
		}
		parent.add(labelsPanel);
	}
	
	private void addSettings(JPanel parent) {
		// combobox for printer options
		// field for num copies
		// combobox for size - from label formats? or textfields for anything they want?
	}
}
