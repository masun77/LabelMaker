package freshStart;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class PrintSettings {
	private LabelView lv = new LabelViewerG2();
	private ArrayList<Item> itemsToPrint = new ArrayList<>();
	private ArrayList<Component> labelList = new ArrayList<>();
	private LabelFormat format;
	private PrintService[] pservices;
	private JComboBox<String> printerList;
	private JSpinner numCopiespinner = null;
	
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
				labelList.add(label);
			}
		}
		parent.add(labelsPanel);
	}
	
	private void addSettings(JPanel parent) {
		VPanel settingsHolder = new VPanel();
		addPrinterList(settingsHolder);
		addNumCopies(settingsHolder);
		addPrintButton(settingsHolder);		
		parent.add(settingsHolder);
	}
	
	private void addPrinterList(JPanel parent) {
		pservices = PrintServiceLookup.lookupPrintServices(null, null);
		String[] printerNames = new String[pservices.length];
		int setIndex = 0;
		for (int i = 0; i < pservices.length; i++) {
			String name = pservices[i].getName();
			if (name.contains("PDF")) {
				setIndex = i;
			}
			printerNames[i] = name;
		}
		printerList = new JComboBox<>(printerNames);
		printerList.setSelectedIndex(setIndex);
		printerList.setMaximumSize(new Dimension(200,50));
		parent.add(printerList);
	}
	
	private void addNumCopies(JPanel parent) {
		SpinnerModel model =
		        new SpinnerNumberModel(1,1,50,1);
		numCopiespinner = new JSpinner(model);
		HPanel copyPanel = new HPanel();
		copyPanel.add(new JLabel("Copies: "));
		copyPanel.add(numCopiespinner);
		copyPanel.setMaximumSize(new Dimension(200,50));
		parent.add(copyPanel);
	}
	
	private void addPrintButton(JPanel parent) {
		JButton printButton = new JButton("Print Labels");
		printButton.addActionListener(new PrintListener());  // todo
		printButton.setMaximumSize(new Dimension(200,50));
		parent.add(printButton);
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PrinterJob pj = PrinterJob.getPrinterJob();
			LabelPrintable lp = new LabelPrintable(labelList);
			PrintService ps = pservices[printerList.getSelectedIndex()];
			int numCopies = (int) numCopiespinner.getValue();
			
			PrinterDescription pd = new PrintConfigReader().readPrinterConfig();
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(pd.getPrintableArea());
			pras.add(pd.getMediaName());
			pras.add(new Copies(numCopies));
			
			try {
				pj.setPrintService(ps);
				pj.setPrintable(lp);
				pj.print(pras);
			}
			catch (Exception error) {
				error.printStackTrace();
			}
		}
	}
}
