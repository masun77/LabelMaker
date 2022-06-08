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

import labels.LabelableItem;
import main.Item;

public class PrintManager {
	public void printLabels(ArrayList<LabelableItem> items) { 
		ArrayList<Component> labels = new ArrayList<Component>();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem currItem = items.get(i);
			int quantity = ((LabelableItem) currItem).getQuantityRoundedUp();
			for (int j = 0; j < quantity; j++) {
				labels.add(currItem.getLabel());
			}
		}
		
		Printable labelPrinter = new LabelPrinter(labels);
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(labelPrinter);

		PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
		boolean doPrint = job.printDialog(attrSet);
		attrSet.add(new MediaPrintableArea(5f, 5f, 78f,95f, MediaPrintableArea.MM));
				
		if (doPrint) {
			try {
				job.print(attrSet);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
}
