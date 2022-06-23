package printing;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;

import labels.LabelableItem;
import main.Item;
import main.RDFItem.RDFLabel;

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
		
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(null, null);
		PrintService ps = pservices[0];
		for (int p = 0; p < pservices.length; p++) {
			if (pservices[p].getName().contains("Godex")) {
				ps = pservices[p];
			}
		}
				
		DocPrintJob job = ps.createPrintJob();	
		DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
		PrintRequestAttributeSet ds = new HashPrintRequestAttributeSet();
		ds.add(new MediaPrintableArea(0f,.5f,3f,4f,MediaPrintableArea.INCH));
		ds.add(OrientationRequested.LANDSCAPE);
		
		// todo: do for each label
		RDFLabel label = (RDFLabel) labels.get(0);
		BufferedImage img = label.getBufferedImage();
		try {
			ImageIO.write(img, "JPEG", new File("resources/img.jpg")); }
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream("resources/img.jpg");
			Doc doc = new SimpleDoc(fin, flavor, null);
			job.print(doc, ds);
			fin.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
