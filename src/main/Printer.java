package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class Printer implements Printable {

	private Component comp;
	
	public Printer(Component c) {
		comp = c;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) {
			return NO_SUCH_PAGE;   // todo: can I print multiple labels though?
		}
		
		double maxHeight = pageFormat.getImageableHeight();
		double maxWidth = pageFormat.getImageableWidth();
		Dimension dim = comp.getSize();
		
		double xScale = maxWidth / dim.getWidth();
		double yScale = maxHeight / dim.getHeight();

		Graphics2D g2 = (Graphics2D) graphics;
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		g2.scale(xScale, yScale);
		comp.paint(graphics);
		
		return PAGE_EXISTS;
	}
	

}
