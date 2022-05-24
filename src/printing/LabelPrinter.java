package printing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.util.ArrayList;

public class LabelPrinter implements Printable {
	private ArrayList<Component> labels;
	
	public LabelPrinter(ArrayList<Component> comps) {
		labels = comps;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex >= labels.size()) { // Print one page for each component
			return NO_SUCH_PAGE;
		} else {
			Component currLabel = labels.get(pageIndex);
			double maxHeight = pageFormat.getImageableHeight();
			double maxWidth = pageFormat.getImageableWidth();
			Dimension dim = currLabel.getSize();

			double xScale = maxWidth / dim.getWidth();
			double yScale = maxHeight / dim.getHeight();

			Graphics2D g2 = (Graphics2D) graphics;
			g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			g2.scale(xScale, yScale);
			currLabel.paint(graphics);

			return PAGE_EXISTS;
		}
	}
}
