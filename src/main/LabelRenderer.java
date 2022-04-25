package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.print.*;

import javax.swing.JFrame;

public class LabelRenderer implements GraphicsRenderer {
	private Component window = null;
	
	@Override
	public void renderLabel(Item item) {
		 JFrame f = new JFrame("View Labels");
	     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     f.setOpacity(1);
	     
	     f.add("Center", new RDFLabel(item, 0, 0));
	     localPack(f);
	     f.setVisible(true);
	     window = f;
	}
	
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
		frame.setSize(width, height);
	}
	
	public void print() {
		// may need to move this elsewhere/other class
		PrinterJob  job = PrinterJob.getPrinterJob();
		Printable printer = new Printer(window);
		job.setPrintable(printer);
		boolean doPrint = job.printDialog();
		
		if (doPrint) {
			try {
		        job.print();
		    } catch (PrinterException e) {
		        // The job did not successfully
		        // complete
		    }
		}
	}
	
	
}
