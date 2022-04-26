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

public class LabelRendererAndPrinter implements GraphicsRenderAndPrinter, Printable {
	private Container itemParent = null;
	private ArrayList<Item> items = null;

	@Override
	public void renderLabels(ArrayList<Item> items) {
		this.items = items;

		JFrame f = new JFrame("View Labels");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setOpacity(1);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		itemParent = mainPanel;

		for (int i = 0; i < items.size(); i++) {
			RDFLabel label = new RDFLabel(items.get(i), 0, 0);
			mainPanel.add(label);
		}

		f.add(mainPanel);
		localPack(f);
		f.setVisible(true);
	}

	/**
	 * Set the frame's size to the maximum minimum width and height of its children
	 * 
	 * @param frame the frame to set the size of
	 */
	private void localPack(JFrame frame) {
		Component[] children = frame.getComponents();
		int height = 0;
		int width = 0;
		Component current = null;
		for (int i = 0; i < children.length; i++) {
			current = children[i];
			Dimension d = current.getMinimumSize();
			height += d.getHeight();
			if (d.getWidth() > width) {
				width = (int) d.getWidth();
			}
		}
		height = height * 2;
		frame.setSize(width, height);
	}

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

		//attrSet.add(MediaSizeName.ISO_A7);
		attrSet.add(OrientationRequested.LANDSCAPE);
		
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
