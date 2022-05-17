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
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import display.Alignment;
import display.Labelable;
import display.RectangleBounds;
import labels.Date;

public class RDFItem extends Item implements Labelable {
	private RDFLabel label = null;

	public RDFItem() {
		super();
		label = new RDFLabel();
	}
	
	public RDFItem(String cust, String prodName, String unit, String gtin, Date packDate) {
		super(cust, prodName, unit, gtin, packDate);
		label = new RDFLabel();
	}
	

	@Override
	public Component getLabel() {
		return label;
	}
	
	private class RDFLabel extends Component {
		private Graphics2D g2 = null;
		private final Dimension labelSize = new Dimension(400,175);
		private final RectangleBounds customerBounds = new RectangleBounds(300,5,380,25);
		private final RectangleBounds barCodeBounds = new RectangleBounds(5,30,300,100);
		private final RectangleBounds prodNameBounds = new RectangleBounds(5,105,300,125);
		private final RectangleBounds unitBounds = new RectangleBounds(5,130,300,150);
		private final RectangleBounds dateLabelBounds = new RectangleBounds(305,70,380,85);
		private final RectangleBounds packDateBounds = new RectangleBounds(315,90,380,105);
		private final RectangleBounds voicePickCodeBounds = new RectangleBounds(305,140,380,170);
		private final String PACK_DATE = "Pack Date";
		
		public RDFLabel() {
			super();
			this.setMinimumSize(labelSize);
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
			addCustomer();

			// Bar code w human readable GTIN
			
			addProductName();
			addUnit();
			addDateLabel();
			addPackDate();
			
			// Voice pick code
		}
		
		private void addCustomer() {
			addText(customer.toUpperCase(), Font.BOLD, Alignment.RIGHT_ALIGN, customerBounds);
		}
		
		private void addProductName() {
			addText(productName.toUpperCase(), Font.BOLD, Alignment.LEFT_ALIGN, prodNameBounds);
		}
		
		private void addUnit() {
			addText(unit, Font.BOLD, Alignment.LEFT_ALIGN, unitBounds);
		}
		
		private void addDateLabel() {
			addText(PACK_DATE, Font.PLAIN, Alignment.LEFT_ALIGN, dateLabelBounds);
		}
		
		private void addPackDate() {
			addText(packDate.getAsPackDate(), Font.BOLD, Alignment.LEFT_ALIGN, packDateBounds);
			int x = packDateBounds.getStartX() - 5;
			int y = packDateBounds.getStartY() - 5;
			int width = packDateBounds.getEndX() - x;
			int height = packDateBounds.getEndY() - y;
			g2.drawRect(x, y, width, height);
		}
		
		/**
		 * Add text to this label.
		 * @param startX the top left x coordinate
		 * @param startY the top left y coordinate
		 * @param text the text to add
		 * @param fontSize the font size for the text
		 * @param fontStyle the font style for the text
		 */
		private void addText(String text, int fontStyle, Alignment align, RectangleBounds bounds) {
			Font font = new Font("SansSerif", fontStyle, bounds.getEndY()-bounds.getStartY());
	        g2.setFont(font);
	        g2.setColor(Color.black);
	        FontRenderContext frc = g2.getFontRenderContext();
			TextLayout tl = new TextLayout(text, font, frc);
	        Rectangle2D textBounds = tl.getBounds(); 
			int fontStartY = bounds.getStartY() + (int) textBounds.getHeight();
			int fontStartX = bounds.getStartX();
			if (align == Alignment.RIGHT_ALIGN) {
				fontStartX = bounds.getEndX() - (int) textBounds.getWidth();
			}
	        g2.drawString(text, fontStartX, fontStartY);
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
