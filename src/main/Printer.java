package main;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * A printable class. 
 *
 */

public class Printer implements Printable {
	private Container comp;
	
	public Printer(Container panel) {
		comp = panel;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex >= comp.getComponentCount()) {    // Print one page for each component
			return NO_SUCH_PAGE; 
		}
		else {
			double maxHeight = pageFormat.getImageableHeight();
			double maxWidth = pageFormat.getImageableWidth();
			Dimension dim = comp.getSize();
			
			double xScale = maxWidth / dim.getWidth();
			double yScale = maxHeight / dim.getHeight();
	
			Graphics2D g2 = (Graphics2D) graphics;
			g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			g2.scale(xScale, yScale);
			comp.getComponent(pageIndex).paint(graphics);
			
			return PAGE_EXISTS;
		}
	}
	

}
