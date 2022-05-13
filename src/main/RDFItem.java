package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import display.Labelable;
import labels.Date;

public class RDFItem extends Item implements Labelable {
	private RDFLabel label = null;

	public RDFItem() {
		super();
		initializeLabel();
	}
	
	public RDFItem(String cust, String pn, String ut, String gt, Date pd) {
		super(cust, pn, ut, gt, pd);
		initializeLabel();
	}
	

	@Override
	public Component getLabel() {
		return label;
	}
	
	private void initializeLabel() {
		label = new RDFLabel();
		
	}
	
	private class RDFLabel extends Canvas {
		private Graphics2D g2 = null;
		private final int[] FONT_SIZES = {20, 13, 11, 9};
		private final String PACK_DATE = "Pack Date";
		private final int LABEL_WIDTH = 400;
		private final int LABEL_HEIGHT = 175;
		private final int CUSTOMER_WIDTH = 50;
		private final int UPPER_BUFFER = 10;
		private final int LEFT_BUFFER = 10;
		private final int SPACING = 5;
		private final int BAR_CODE_HEIGHT = 30;
		private final int BAR_MODULE_WIDTH = 1;
		
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
			int x = LABEL_WIDTH - CUSTOMER_WIDTH;
			int y = 0;
			addCustomer(x, y);
			x = LEFT_BUFFER /2;
			y = FONT_SIZES[1] + SPACING*2;
			addOuterBorder(x, y);
			x = LEFT_BUFFER;
			y += SPACING;
			
			
			
			LabelUtilities.createGS1_128GTINBarCode(g2, x, y, item.getGtin(), BAR_MODULE_WIDTH, BAR_CODE_HEIGHT, FONT_SIZES[2]);
			x += 10* BAR_MODULE_WIDTH;
			y += BAR_CODE_HEIGHT + FONT_SIZES[3] + SPACING;		
			addProductName(x, y);
			y += FONT_SIZES[0] + SPACING;
			addUnit(x,y);
			y += FONT_SIZES[1];

			x = startX + LABEL_WIDTH - LABEL_WIDTH / 4;
			y = startY + LABEL_HEIGHT /2 ;
			addPackDateText(x, y);
			y += FONT_SIZES[2];
			addItemDate(x, y);
			y += FONT_SIZES[1] + SPACING * 2;
			LabelUtilities.addVoicePickCode(g2, x, y, item.getGtin(), item.getPackDate(), FONT_SIZES[1], FONT_SIZES[0], Font.PLAIN);
		}
		
		private void addCustomer(int x, int y) {
			addText(x, y, item.getCustomer().toUpperCase(), FONT_SIZES[1], Font.BOLD);
		}
		
		private void addOuterBorder(int x, int y) {
			g2.drawRect(x, y, LABEL_WIDTH, (int)(LABEL_HEIGHT *.77));
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
			g2.drawRect(x -10, y + SPACING, LABEL_WIDTH / 6, FONT_SIZES[1]+2);
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
			Font font = new Font("SansSerif", fontStyle, fontSize);
	        g2.setFont(font);
	        g2.setColor(Color.black);
	        
	        LabelUtilities.drawStringHelper(g2, font, text, startY, startX);
		}
		
		private static int modWidth = 4;    
		private static int modHeight = 130;  
		private static final int MODS_TO_FIRST_CHAR = 32; // 10 quiet zone + 11 start char + 11 FNC1
			
		/**
		 * Append a text version of the GTIN under the bar code in the given graphics context. 
		 * @param g2 the graphics context
		 * @param startX the starting x coordinate of the bar code
		 * @param startY the starting y coordinate of the bar code
		 * @param gtin the GTIN to represent in the text
		 * @param fontSize the size of the font to use
		 */
		private static void appendHumanReadableVersion(Graphics2D g2, int startX, int startY, String gtin, int fontSize) {
			String hrv = "(" + AI_CODE + ")" + gtin;
			
			Font font = new Font("SansSerif", Font.PLAIN, fontSize);
	        g2.setFont(font);
	        g2.setColor(Color.black);

	        drawStringHelper(g2, font, hrv, startY + modHeight, startX +20);
		}
		
		/**
		 * Draw a string on this graphics object 
		 * @param g2 the graphics object
		 * @param font the font to use
		 * @param str the string to draw
		 * @param startY the top left y coordinate
		 * @param startX the top left x coordinate
		 */
		public static void drawStringHelper(Graphics2D g2, Font font, String str, int startY, int startX) {
			FontRenderContext frc = ((Graphics2D)g2).getFontRenderContext();
	        Rectangle2D boundsTemp = font.getStringBounds(str, frc);  
			startY += (int) boundsTemp.getHeight();
	        g2.drawString(str, startX, startY);
		}
		
		/**
		 * Add a voice pick code to this graphics object
		 * @param g2 the graphics object
		 * @param startX the top left x coordinate
		 * @param startY the top left y coordinate
		 * @param gtin the GTIN to use for calculating the voice pick code
		 * @param date the date to use for calculating the voice pick code
		 * @param smallFontSize the font size to use for the two small numbers
		 * @param largeFontSize the font size to use for the two large numbers
		 * @param fontStyle the font style to use
		 */
		public static void addVoicePickCode(Graphics2D g2, int startX, int startY, String gtin, Date date, int smallFontSize, int largeFontSize, int fontStyle) {
			String vpc = calculateVoicePickCode(gtin, date);
			String smallNums = vpc.substring(0,2);
			String bigNums = vpc.substring(2);
			
			g2.setColor(Color.BLACK);
			Font font = new Font("SansSerif", fontStyle, largeFontSize); 
			g2.setFont(font);
			
			FontRenderContext frc = ((Graphics2D)g2).getFontRenderContext();
	        Rectangle2D boundsTemp = font.getStringBounds(vpc, frc);  
	    	g2.fill(new Rectangle(startX, startY, (int) boundsTemp.getWidth(), (int) boundsTemp.getHeight()));
	    	
	    	g2.setColor(Color.WHITE);
	    	drawStringHelper(g2, font, bigNums, startY - 5, startX + (int) boundsTemp.getWidth()/2);
	    	
	    	font = new Font("SanSerif", fontStyle, smallFontSize-2); 
	    	g2.setFont(font);
	    	drawStringHelper(g2, font, smallNums, startY + 3, startX + 2);
		}
	}
}
