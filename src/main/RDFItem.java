package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import freshStart.BarCodeGenerator;
import freshStart.BarCodeImp;
import freshStart.Date;
import freshStart.VoicePickCodeGenerator;
import freshStart.VoicePickImp;

public class RDFItem extends Item {

	
	/**
	 * Displayable label representing this item. 
	 * @author mayaj
	 *
	 */
	public class RDFLabel extends Component {
//		private Graphics2D g2 = null;
//		private final Dimension viewSize = new Dimension(410,300);  // label size: 400 x 175; 400x300 for whole label
//		private final RectangleBounds customerBounds = new RectangleBounds(300,5,380,25);
//		private final RectangleBounds borderBounds = new RectangleBounds(3,27,390,160);
//		private final RectangleBounds barCodeBounds = new RectangleBounds(5,30,300,80);
//		private final RectangleBounds humanReadableBounds = new RectangleBounds(100,80,350,95);
//		private final RectangleBounds prodNameBounds = new RectangleBounds(5,105,300,125);
//		private final RectangleBounds unitBounds = new RectangleBounds(5,130,200,150);
//		private final RectangleBounds dateLabelBounds = new RectangleBounds(305,80,380,93);
//		private final RectangleBounds packDateBounds = new RectangleBounds(315,98,380,112);
//		private final RectangleBounds packDateBox = new RectangleBounds(305,93,370,113);
//		private final RectangleBounds voicePickCodeBounds = new RectangleBounds(307,114,390,157);
//		private final RectangleBounds vpcSmallBounds = new RectangleBounds(310,131,340,153);
//		private final RectangleBounds vpcLargeBounds = new RectangleBounds(342,117,380,157);
//		private final RectangleBounds attnBounds = new RectangleBounds(210,130,300,150);   // todo: add to order? or indiv item? or indiv labels?
//		private final String PACK_DATE = "Pack Date";
//		private final String AI_CODE = "(01)";
//		private final int BAR_HEIGHT = 45;
//		private final int BAR_WIDTH = 2;
//		private BufferedImage image = new BufferedImage(400,300,BufferedImage.TYPE_INT_RGB);
//		
//		public RDFLabel() {
//			super();
//			this.setMinimumSize(viewSize);
//			this.setPreferredSize(viewSize);
//			this.setSize(viewSize);
//			
//			checkItemData();
//		}
//		
//		
//		
//		@Override
//		public void paint(Graphics g) {			
//			Graphics2D imgG = image.createGraphics();
//			setBackgroundWhite(imgG);
//			g2 = imgG;
//			createLabel();
//			
//			g.drawImage(image, 0, 0, null);
//		}
//		
//		
//		
//		/**
//		 * Create a label in the RDF format. 
//		 */
//		private void createLabel() {
//			addCustomer();
//			addOuterBorder();
//			addBarCode();
//			addProductName();
//			addUnit();
//			addDateLabel();
//			addPackDate();
//			addVoicePickCode();
//		}
//		
//		private void addCustomer() {
//			addText(customer.toUpperCase(), Font.BOLD, Alignment.RIGHT_ALIGN, Color.black,  customerBounds,34);
//		}
//		
//		private void addOuterBorder() {
//			g2.drawRect(borderBounds.getStartX(), borderBounds.getStartY(), borderBounds.getWidth(), borderBounds.getHeight());
//		}
//		
//		private void addProductName() {
//			addText(productName.toUpperCase(), Font.BOLD, Alignment.LEFT_ALIGN, Color.black,  prodNameBounds,22);
//		}
//		
//		private void addUnit() {
//			addText(unit, Font.PLAIN, Alignment.LEFT_ALIGN, Color.black,  unitBounds,25);
//		}
//		
//		private void addDateLabel() {
//			addText(PACK_DATE, Font.PLAIN, Alignment.LEFT_ALIGN, Color.black,  dateLabelBounds,10);
//		}
//		
//		private void addPackDate() {
//			addText(packDate.getAsLabelDate(), Font.BOLD, Alignment.LEFT_ALIGN, Color.black, packDateBounds,10);
//			g2.drawRect(packDateBox.getStartX(), packDateBox.getStartY(), packDateBox.getWidth(), packDateBox.getHeight());
//		}
//		
//		/**
//		 * Add text to this label.
//		 * @param startX the top left x coordinate
//		 * @param startY the top left y coordinate
//		 * @param text the text to add
//		 * @param fontSize the font size for the text
//		 * @param fontStyle the font style for the text
//		 */
//		private void addText(String text, int fontStyle, Alignment align, Color color, RectangleBounds bounds, int maxLength) {
//			int fontSize = bounds.getEndY()-bounds.getStartY();
//			if (text.length() > maxLength) {
//				double ml = maxLength;
//				double fraction = ml / text.length();
//				fontSize = (int)Math.round(fontSize * fraction);
//			}
//			Font font = new Font("SansSerif", fontStyle, fontSize);
//	        g2.setFont(font);
//	        g2.setColor(color);
//	        FontRenderContext frc = g2.getFontRenderContext();
//			TextLayout tl = new TextLayout(text, font, frc);
//	        Rectangle2D textBounds = tl.getBounds(); 
//			int fontStartY = bounds.getStartY() + (int) textBounds.getHeight();
//			int fontStartX = bounds.getStartX();
//			if (align == Alignment.RIGHT_ALIGN) {
//				fontStartX = bounds.getEndX() - (int) textBounds.getWidth();
//			}
//	        g2.drawString(text, fontStartX, fontStartY);
//		}
//		
//		private void addBarCode() {
//			BarCodeGenerator bcg = new BarCodeImp();
//			ArrayList<Integer> barCode = bcg.getBarCode(gtin);
//			addBarRectangles(barCode);
//			appendHumanReadableVersion();
//		}
//		
//		
//		
	}

	@Override
	public Component getLabel() {
		// TODO Auto-generated method stub
		return null;
	}
}
