package printing;

import java.awt.Component;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;

import labels.Labelable;
import main.Item;

public class PrintManager {
	public void printLabels(ArrayList<Labelable> items) { 
		ArrayList<Component> labels = new ArrayList<Component>();
		for (int i = 0; i < items.size(); i++) {
			Labelable currItem = items.get(i);
			for (int j = 0; j < ((Item) currItem).getQuantity(); j++) {
				labels.add(currItem.getLabel());
			}
		}
		
		Printable labelPrinter = new LabelPrinter(labels);
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(labelPrinter);

		PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
		boolean doPrint = job.printDialog(attrSet);
//		attrSet.add(OrientationRequested.REVERSE_LANDSCAPE);
//		attrSet.add(new MediaPrintableArea(5f, 5f, 78f,95f, MediaPrintableArea.MM));
				
		if (doPrint) {
			try {
				job.print(attrSet);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
}
