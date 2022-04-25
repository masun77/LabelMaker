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
	private int startX, startY;
	private Graphics2D g2 = null;
	private final int[] FONT_SIZES = {30, 20, 12, 10};
	private final String PRODUCT_OF = "Product of USA";
	private final String FARM = "Riverdog Farm";
	private final String LOCATION = "Guinda, CA 95637";
	private final String PACK_DATE = "Pack Date";
	private final int LABEL_WIDTH = 400;
	private final int LABEL_HEIGHT = 200;
	private final int CUSTOMER_WIDTH = 100;
	private final int UPPER_BUFFER = 10;
	private final int LEFT_BUFFER = 10;
	private final int SPACING = 3;
	private final int BAR_CODE_HEIGHT = 50;
	private final int BAR_MODULE_WIDTH = 2;

	public RDFLabel(Item o, int x, int y) {
		item = o;
		this.setMinimumSize(new Dimension(LABEL_WIDTH + LEFT_BUFFER * 3,LABEL_HEIGHT + 50 + UPPER_BUFFER*2));
		startX = x;
		startY = y;
	}

	@Override
	public void paint(Graphics g) {
		setBackgroundWhite(g);
		g2 = (Graphics2D) g;
		createLabel();
	}
	
	/**
	 * Set the background to white by filling it with a white rectangle.
	 * @param g
	 */
	private void setBackgroundWhite(Graphics g) {
		this.setBackground(Color.white);
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
	}
	
	/**
	 * Create a label in the RDF format. 
	 */
	private void createLabel() {
		int currX = startX + LABEL_WIDTH - CUSTOMER_WIDTH;
		int currY = startY;
		addText(currX, currY, item.getCustomer().toUpperCase(), FONT_SIZES[1], Font.BOLD);
		currX = startX + LEFT_BUFFER /2;
		currY = currY + 30 + SPACING;
		g2.drawRect(currX, currY, LABEL_WIDTH, LABEL_HEIGHT - FONT_SIZES[1]);
		currX = startX + LEFT_BUFFER;
		currY += SPACING;
		LabelUtilities.createGS1_128GTINBarCode(g2, currX,currY, item.getGtin(), BAR_MODULE_WIDTH, BAR_CODE_HEIGHT, FONT_SIZES[2]);
		currY += BAR_CODE_HEIGHT + FONT_SIZES[3] + SPACING;		
		addText(currX, currY, item.getProductName().toUpperCase(), FONT_SIZES[0], Font.BOLD);
		currY += FONT_SIZES[0] + SPACING;
		addText(currX, currY, item.getUnit(), FONT_SIZES[1], Font.BOLD);
		currY += FONT_SIZES[1];
		addStandardRDText(currX, currY);
		currX = startX + LABEL_WIDTH - LABEL_WIDTH / 4;
		currY = startY + UPPER_BUFFER + FONT_SIZES[1] + LABEL_HEIGHT /2;
		addText(currX, currY, PACK_DATE, FONT_SIZES[2], Font.PLAIN);
		currY += FONT_SIZES[2] + SPACING;
		addText(currX, currY, item.getPackDate().getAsPackDate(), FONT_SIZES[1], Font.BOLD);
		g2.drawRect(currX -10, currY + SPACING*2, LABEL_WIDTH / 4, FONT_SIZES[1]);
		currY += FONT_SIZES[1] + SPACING * 4;
		LabelUtilities.addVoicePickCode(g2, currX, currY, item.getGtin(), item.getPackDate(), FONT_SIZES[1], FONT_SIZES[0], Font.PLAIN);
	}
	
	/**
	 * Add the standard text that appears at the bottom left of the label.
	 * @param startX the top left x coordinate
	 * @param startY the top left y coordinate
	 */
	private void addStandardRDText(int startX, int startY) {
		addText(startX, startY, PRODUCT_OF, FONT_SIZES[1], Font.BOLD);
		int currY = startY + FONT_SIZES[1] + SPACING;
		addText(startX, currY, FARM, FONT_SIZES[2], Font.PLAIN);
		currY += FONT_SIZES[2];
		addText(startX, currY, LOCATION, FONT_SIZES[2], Font.PLAIN);
	}
	
	/**
	 * Add text to this label.
	 * @param startX the top left x coordinate
	 * @param startY the top left y coordinate
	 * @param text the text to add
	 * @param fontSize the font size for the text
	 * @param fontStyle the font style for the text
	 */
	private void addText(int startX, int startY, String text, int fontSize, int fontStyle) {
		Font font = new Font("Serif", fontStyle, fontSize);
        g2.setFont(font);
        g2.setColor(Color.black);
        
        LabelUtilities.drawStringHelper(g2, font, text, startY, startX);
	}
}
