package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class RDFLabel extends Component {
	private Item item;
	private final int[] FONT_SIZES = {40, 50, 60, 70};
	private Graphics2D g2 = null;
	private final String PRODUCT_OF = "Product of USA";
	private final String FARM = "Riverdog Farm";
	private final String LOCATION = "Guinda, CA 95637";
	private final String PACK_DATE = "Pack Date";
	private int startX, startY;
	private final int LABEL_WIDTH = 950;
	private final int LABEL_HEIGHT = 600;

	public RDFLabel(Item o, int x, int y) {
		item = o;
		this.setMinimumSize(new Dimension(1000,1000));
		startX = x;
		startY = y;
		this.setBackground(Color.white);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
		g2 = (Graphics2D) g;
		createLabel();
	}
	
	/**
	 * Create
	 * @param g2
	 */
	private void createLabel() {
		g2.drawRect(startX + 5, startY + FONT_SIZES[1], LABEL_WIDTH, LABEL_HEIGHT);
		LabelUtilities.createGS1_128GTINBarCode(g2, 10,FONT_SIZES[2], item.getGtin());
		addText(800, 2, item.getCustomer().toUpperCase(), FONT_SIZES[2], Font.PLAIN);
		addText(10, 220, item.getProductName().toUpperCase(), FONT_SIZES[3], Font.BOLD);
		addText(10, 300, item.getUnit(), FONT_SIZES[2], Font.BOLD);
		addStandardRDText();
		addText(750, 300, PACK_DATE, FONT_SIZES[1], Font.PLAIN);
		addText(750, 350, item.getPackDate().getAsPackDate(), FONT_SIZES[2], Font.BOLD);
		g2.drawRect(740, 380, 200, FONT_SIZES[2]);
		LabelUtilities.addVoicePickCode(g2, 750, 450, item.getGtin(), item.getPackDate(), FONT_SIZES[1], FONT_SIZES[3], Font.PLAIN);
	}
	
	private void addStandardRDText() {
		addText(10, 360, PRODUCT_OF, FONT_SIZES[2], Font.BOLD);
		addText(10, 420, FARM, FONT_SIZES[1], Font.PLAIN);
		addText(10, 480, LOCATION, FONT_SIZES[1], Font.PLAIN);
	}
	
	private void addText(int startX, int startY, String text, int fontSize, int fontStyle) {
		Font font = new Font("Serif", fontStyle, fontSize);
        g2.setFont(font);
        g2.setColor(Color.black);
        
        LabelUtilities.drawStringHelper(g2, font, text, startY, startX);
	}
}
