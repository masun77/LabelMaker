package oldFiles;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PrintListener implements ActionListener {
	private LabelPrinter lp;
	private ArrayList<LabelableItem> items = new ArrayList<>();
	
	public PrintListener(LabelPrinter printer) {
		lp = printer;
	}
	
	public void setPrinter(LabelPrinter printer) {
		lp = printer;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		lp.printLabels(items);
	}
}
