package printing;

import java.awt.Component;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import labels.LabelableItem;
import main.AppState;

public class PrintManager implements LabelPrinter {
	
	@Override
	public void printLabels(ArrayList<LabelableItem> items) { 
		PrinterJob pj = PrinterJob.getPrinterJob();
		PrinterDescription printerDesc = AppState.getFileBackup().getPrinterDescription();
		
		ArrayList<Component> labels = new ArrayList<Component>();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem currItem = items.get(i);
			int quantity = ((LabelableItem) currItem).getQuantityRoundedUp();
			for (int j = 0; j < quantity; j++) {
				labels.add(currItem.getLabel());
			}
		}
		LabelPrintable lp = new LabelPrintable(labels);
		pj.setPrintable(lp);
		
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(null, null);
		PrintService ps = pservices[0];
		for (int p = 0; p < pservices.length; p++) {
			if (pservices[p].getName().contains(printerDesc.getPrinterName())) {    // todo make sure is godex if testing
				ps = pservices[p];
			}
		}
		
		try {
			pj.setPrintService(ps);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		pras.add(printerDesc.getPrintableArea());
		pras.add(printerDesc.getMediaName());
				
		try {
			pj.print(pras);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
