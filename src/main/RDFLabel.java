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
	private final int[] FONT_SIZES = {20, 15, 12, 10};
	private final String PRODUCT_OF = "Product of USA";
	private final String FARM = "Riverdog Farm";
	private final String LOCATION = "Guinda, CA 95637";
	private final String PACK_DATE = "Pack Date";
	private final int LABEL_WIDTH = 300;
	private final int LABEL_HEIGHT = 150;
	private final int CUSTOMER_WIDTH = 50;
	private final int UPPER_BUFFER = 10;
	private final int LEFT_BUFFER = 10;
	private final int SPACING = 3;
	private final int BAR_CODE_HEIGHT = 30;
	private final int BAR_MODULE_WIDTH = 1;

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
		int x = startX + LABEL_WIDTH - CUSTOMER_WIDTH;
		int y = startY;
		addCustomer(x, y);
		x = startX + LEFT_BUFFER /2;
		y = startY + FONT_SIZES[1] + SPACING*2;
		addOuterBorder(x, y);
		x = startX + LEFT_BUFFER;
		y += SPACING;
		LabelUtilities.createGS1_128GTINBarCode(g2, x, y, item.getGtin(), BAR_MODULE_WIDTH, BAR_CODE_HEIGHT, FONT_SIZES[2]);
		y += BAR_CODE_HEIGHT + FONT_SIZES[3] + SPACING;		
		addProductName(x, y);
		y += FONT_SIZES[0] + SPACING;
		addUnit(x,y);
		y += FONT_SIZES[1];
		addStandardRDText(x, y);
		x = startX + LABEL_WIDTH - LABEL_WIDTH / 4;
		y = startY + LABEL_HEIGHT /2 + FONT_SIZES[2];
		addPackDateText(x, y);
		y += FONT_SIZES[2] + SPACING;
		addItemDate(x, y);
		y += FONT_SIZES[1] + SPACING * 4;
		LabelUtilities.addVoicePickCode(g2, x, y, item.getGtin(), item.getPackDate(), FONT_SIZES[2], FONT_SIZES[0], Font.PLAIN);
	}
	
	private void addCustomer(int x, int y) {
		addText(x, y, item.getCustomer().toUpperCase(), FONT_SIZES[1], Font.BOLD);
	}
	
	private void addOuterBorder(int x, int y) {
		g2.drawRect(x, y, LABEL_WIDTH, LABEL_HEIGHT - FONT_SIZES[1]);
	}
	
	private void addProductName(int x, int y) {
		addText(x, y, item.getProductName().toUpperCase(), FONT_SIZES[0], Font.BOLD);
	}
	
	private void addUnit(int x, int y) {
		addText(x, y, item.getUnit(), FONT_SIZES[1], Font.BOLD);
	}
	
	private void addPackDateText(int x, int y) {
		addText(x, y, PACK_DATE, FONT_SIZES[2], Font.PLAIN);
	}
	
	private void addItemDate(int x, int y) {
		addText(x, y, item.getPackDate().getAsPackDate(), FONT_SIZES[1], Font.BOLD);
		g2.drawRect(x -10, y + SPACING*2, LABEL_WIDTH / 4, FONT_SIZES[1]);
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
