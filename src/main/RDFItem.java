package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import labels.Alignment;
import labels.BarCodeGenerator;
import labels.BarCodeImp;
import labels.Date;
import labels.RectangleBounds;
import labels.VoicePickCodeGenerator;
import labels.VoicePickImp;

public class RDFItem extends Item {

	public RDFItem() {
		super();
	}
	
	public RDFItem(String cust, String prodName, String unit, String gtin, Date packDate, float qty, float price, String itemCode) {
		super(cust, prodName, unit, gtin, packDate, qty, price, itemCode);
	}
	
	public RDFItem(String cust, String itemCode, String prodName, String gtin, String unit,
			float qty, float price,
			Date pickDate, Date packDate, Date shipDate) {
		super(cust, itemCode, prodName, gtin, unit, qty, price, pickDate, packDate, shipDate);
	}

	@Override
	public Component getLabel() {
		return new RDFLabel();
	}
	
	/**
	 * Displayable label representing this item. 
	 * @author mayaj
	 *
	 */
	public class RDFLabel extends Component {
		private Graphics2D g2 = null;
		private final Dimension viewSize = new Dimension(410,300);  // label size: 400 x 175; 400x300 for whole label
		private final RectangleBounds customerBounds = new RectangleBounds(300,5,380,25);
		private final RectangleBounds borderBounds = new RectangleBounds(3,27,390,160);
		private final RectangleBounds barCodeBounds = new RectangleBounds(5,30,300,80);
		private final RectangleBounds humanReadableBounds = new RectangleBounds(100,80,350,95);
		private final RectangleBounds prodNameBounds = new RectangleBounds(5,105,300,125);
		private final RectangleBounds unitBounds = new RectangleBounds(5,130,300,150);
		private final RectangleBounds dateLabelBounds = new RectangleBounds(305,80,380,93);
		private final RectangleBounds packDateBounds = new RectangleBounds(315,98,380,112);
		private final RectangleBounds packDateBox = new RectangleBounds(305,93,370,113);
		private final RectangleBounds voicePickCodeBounds = new RectangleBounds(307,114,380,157);
		private final RectangleBounds vpcSmallBounds = new RectangleBounds(310,131,340,153);
		private final RectangleBounds vpcLargeBounds = new RectangleBounds(335,117,380,157);
		private final String PACK_DATE = "Pack Date";
		private final String AI_CODE = "(01)";
		private final int BAR_HEIGHT = 45;
		private final int BAR_WIDTH = 2;
		private BufferedImage image = new BufferedImage(400,300,BufferedImage.TYPE_INT_RGB);
		
		public RDFLabel() {
			super();
			this.setMinimumSize(viewSize);
			this.setPreferredSize(viewSize);
			this.setSize(viewSize);
			
			checkItemData();
		}
		
		private void checkItemData() {
			if (customer.equals("")) {
				customer = " ";
			}
			if (productName.equals("")) {
				productName = " ";
			}
			if (gtin.equals("")) {
				gtin = "818181020000";
			}
			if (unit.equals("")) {
				unit = " ";
			}
		}
		
		@Override
		public void paint(Graphics g) {			
			Graphics2D imgG = image.createGraphics();
			setBackgroundWhite(imgG);
			g2 = imgG;
			g2.setRenderingHint(
			        RenderingHints.KEY_TEXT_ANTIALIASING,
			        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
			createLabel();
			
			g.drawImage(image, 0, 0, null);
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
			addOuterBorder();
			addBarCode();
			addProductName();
			addUnit();
			addDateLabel();
			addPackDate();
			addVoicePickCode();
		}
		
		private void addCustomer() {
			addText(customer.toUpperCase(), Font.BOLD, Alignment.RIGHT_ALIGN, Color.black,  customerBounds,34);
		}
		
		private void addOuterBorder() {
			g2.drawRect(borderBounds.getStartX(), borderBounds.getStartY(), borderBounds.getWidth(), borderBounds.getHeight());
		}
		
		private void addProductName() {
			addText(productName.toUpperCase(), Font.BOLD, Alignment.LEFT_ALIGN, Color.black,  prodNameBounds,25);
		}
		
		private void addUnit() {
			addText(unit, Font.BOLD, Alignment.LEFT_ALIGN, Color.black,  unitBounds,25);
		}
		
		private void addDateLabel() {
			addText(PACK_DATE, Font.PLAIN, Alignment.LEFT_ALIGN, Color.black,  dateLabelBounds,10);
		}
		
		private void addPackDate() {
			addText(packDate.getAsPackDate(), Font.BOLD, Alignment.LEFT_ALIGN, Color.black, packDateBounds,10);
			g2.drawRect(packDateBox.getStartX(), packDateBox.getStartY(), packDateBox.getWidth(), packDateBox.getHeight());
		}
		
		/**
		 * Add text to this label.
		 * @param startX the top left x coordinate
		 * @param startY the top left y coordinate
		 * @param text the text to add
		 * @param fontSize the font size for the text
		 * @param fontStyle the font style for the text
		 */
		private void addText(String text, int fontStyle, Alignment align, Color color, RectangleBounds bounds, int maxLength) {
			int fontSize = bounds.getEndY()-bounds.getStartY();
			if (text.length() > maxLength) {
				double ml = maxLength;
				double fraction = ml / text.length();
				fontSize = (int)Math.round(fontSize * fraction);
			}
			System.out.println("fs:" + fontSize);
			Font font = new Font("SansSerif", fontStyle, fontSize);
	        g2.setFont(font);
	        g2.setColor(color);
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
		
		private void addBarCode() {
			BarCodeGenerator bcg = new BarCodeImp();
			ArrayList<Integer> barCode = bcg.getBarCode(gtin);
			addBarRectangles(barCode);
			appendHumanReadableVersion();
		}
		
		private void addBarRectangles(ArrayList<Integer> barCode) {
			int x = barCodeBounds.getStartX();
			int y = barCodeBounds.getStartY();
			Color[] colors = {Color.white, Color.black};
			int currColor = 0;
			for (int block = 0; block < barCode.size(); block++) {
				int numMods = barCode.get(block);
				for (int mod = 0; mod < numMods; mod++) {
					g2.setColor(colors[currColor]);
					g2.fillRect(x, y, BAR_WIDTH, BAR_HEIGHT);
					x += BAR_WIDTH;
				}
				if (currColor == 0) {
					currColor = 1;
				} 
				else {
					currColor = 0;
				}
			}
		}
				
		/**
		 * Append a text version of the GTIN under the bar code in the given graphics context. 
		 * @param g2 the graphics context
		 * @param startX the starting x coordinate of the bar code
		 * @param startY the starting y coordinate of the bar code
		 * @param gtin the GTIN to represent in the text
		 * @param fontSize the size of the font to use
		 */
		private void appendHumanReadableVersion() {
	        addText(AI_CODE + gtin, Font.BOLD, Alignment.LEFT_ALIGN, Color.black, humanReadableBounds, 40);
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
		private void addVoicePickCode() {
			VoicePickCodeGenerator vpcg = new VoicePickImp();
			String vpc = vpcg.calculateVoicePickCode(gtin, packDate.getDateYYMMDD());
			String smallNums = vpc.substring(0,2);
			String bigNums = vpc.substring(2);
						 
	    	g2.fillRect(voicePickCodeBounds.getStartX(), voicePickCodeBounds.getStartY(), voicePickCodeBounds.getWidth(), voicePickCodeBounds.getHeight());
	    	
	    	addText(smallNums, Font.BOLD, Alignment.LEFT_ALIGN, Color.white, vpcSmallBounds,2);
	    	addText(bigNums, Font.BOLD, Alignment.LEFT_ALIGN, Color.white, vpcLargeBounds,2);
		}
		
		public BufferedImage getBufferedImage() {
			BufferedImage img = new BufferedImage(300,410,BufferedImage.TYPE_INT_RGB);
			Graphics2D imgG = img.createGraphics();
			g2 = imgG;
			g2.rotate(Math.PI/2);
			g2.translate(0, -300);
			setBackgroundWhite(imgG);
			createLabel();

			return img;
		}
	}
}
