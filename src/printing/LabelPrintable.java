package printing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.util.ArrayList;

public class LabelPrintable implements Printable {
	private ArrayList<Component> labels;
	
	public LabelPrintable(ArrayList<Component> comps) {
		labels = comps;
	}

	@Override
	public int print(Graphics graphics, PageFormat pf, int pageIndex) throws PrinterException {
		if (pageIndex >= labels.size()) { // Print one page for each label
			return NO_SUCH_PAGE;
		} else {
			Component currLabel = labels.get(pageIndex);
									
			double maxHeight = pf.getImageableHeight();
			double maxWidth = pf.getImageableWidth();
			
			Dimension dim = currLabel.getSize();

			double xScale = maxWidth / dim.getWidth();
			double yScale = maxHeight / dim.getHeight();

			Graphics2D g2 = (Graphics2D) graphics;
			g2.translate(pf.getImageableX(), pf.getImageableY());
						
			g2.scale(xScale, yScale);
			currLabel.paint(graphics);

			return PAGE_EXISTS;
		}
	}
}
