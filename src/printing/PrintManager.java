package printing;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Media;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

import labels.LabelableItem;
import main.Item;
import main.RDFItem.RDFLabel;

public class PrintManager implements LabelPrinter {
	@Override
	public void printLabels(ArrayList<LabelableItem> items) { 
		PrinterJob pj = PrinterJob.getPrinterJob();
		
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
			if (pservices[p].getName().contains("Godex")) {   
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
		pras.add(new MediaPrintableArea(.2f,2.5f,3.6f,3.5f,MediaPrintableArea.INCH));
		pras.add(MediaSizeName.ISO_A6);
				
		try {
			pj.print(pras);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
