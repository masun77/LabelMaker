package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.util.ArrayList;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class LabelRendererAndPrinter implements Printable {
	private Container itemParent = null;
	private ArrayList<Item> items = null;

	/**
	 * Open a print dialog for the current graphic. Print if user selects okay,
	 * otherwise do nothing.
	 */
	public void printLabels() {
		try {
			String cn = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(cn);
		} catch (Exception e) {
		}

		PrinterJob job = PrinterJob.getPrinterJob();

		PrintRequestAttributeSet attrSet = new HashPrintRequestAttributeSet();
		job.setPrintable(this);
		boolean doPrint = job.printDialog(attrSet);
		attrSet.add(OrientationRequested.REVERSE_LANDSCAPE);
		attrSet.add(new MediaPrintableArea((float)1, (float)1, (float)7.4,(float)9.9, MediaPrintableArea.INCH));
		
		if (doPrint) {
			try {
				job.print(attrSet);
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex >= itemParent.getComponentCount()) { // Print one page for each component
			return NO_SUCH_PAGE;
		} else {
			double maxHeight = pageFormat.getImageableHeight();
			double maxWidth = pageFormat.getImageableWidth();
			Dimension dim = itemParent.getSize();

			double xScale = maxWidth / dim.getWidth();
			double yScale = maxHeight / dim.getHeight();

			Graphics2D g2 = (Graphics2D) graphics;
			g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			g2.scale(xScale, yScale);
			itemParent.getComponent(pageIndex).paint(graphics);

			return PAGE_EXISTS;
		}
	}
}
