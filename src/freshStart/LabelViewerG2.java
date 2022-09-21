package freshStart;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelViewerG2 {
	public void showLabel(Item i, LabelFormat lf) {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(lf.getLabelDimensions().getxMax(), 
				lf.getLabelDimensions().getyMax()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel tester = new LabelPanel(i, lf);
		frame.add(tester);
		frame.setVisible(true);
	}
	
	private class LabelPanel extends JPanel {
		private Item item;
		private LabelFormat format;
		private ArrayList<Integer> barCode;
		private String smallVPC;
		private String largeVPC;

	    LabelPanel(Item i, LabelFormat lf) {
	    	item = i;
	    	format = lf;
	    	checkItemData();
	    	BarCodeGenerator bcg = new BarCodeImp();
			barCode = bcg.getBarCode(item.getGtin());
			VoicePickCodeGenerator vpcg = new VoicePickImp();
			String vpc = vpcg.calculateVoicePickCode(item.getGtin(), 
					item.getShipDate().getDateYYMMDD());
			smallVPC = vpc.substring(0,2);
			largeVPC = vpc.substring(2);
	    }
		
		public void checkItemData() {
			if (item.getCompany().equals("")) {
				item.setCompany(" ");
			}
			if (item.getProductName().equals("")) {
				item.setProductName(" "); 
			}
			if (item.getGtin().equals("")) {
				item.setGtin("818181020000");
			}
			if (item.getUnit().equals("")) {
				item.setUnit(" ");
			}
		}

	    public Dimension getPreferredSize() {
	        return new Dimension(410,300);
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g); 
	        
	        Graphics2D g2 = (Graphics2D) g;
	        setBackgroundWhite(g2);
	        
	        for (RectangleObject r: format.getRectangles()) {
	        	Bounds b = r.getBounds();
	        	if (r.getName().equals("barCode")) {
	        		addBarRectangles(r, g2);
	        	}
	        	else if (r.isFilled()) {
	        		g2.fillRect(b.getxMin(), b.getyMin(), 
		        			b.getWidth(), b.getHeight());
	        	}
	        	else {
	        		g2.drawRect(b.getxMin(), b.getyMin(), 
	        			b.getWidth(), b.getHeight());
	        	}
	        }

	        for (TextObject t: format.getTextObjects()) {
	        	addText(t, g2);
			}
	    }  
	    
	    /**
		 * Set the background to white by filling it with a white rectangle.
		 * @param g
		 */
		private void setBackgroundWhite(Graphics2D g) {
			this.setBackground(Color.white);
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(getForeground());
		}
		
		private void addBarRectangles(RectangleObject r, Graphics2D g2) {
			Bounds b = r.getBounds();
			int x = b.getxMin();
			int y = b.getyMin();
			int barHeight = b.getHeight();
			int barWidth = (int) b.getWidth() / 100;
			Color[] colors = {Color.white, Color.black};
			int currColor = 0;
			for (int block = 0; block < barCode.size(); block++) {
				int numMods = barCode.get(block);
				for (int mod = 0; mod < numMods; mod++) {
					g2.setColor(colors[currColor]);
					g2.fillRect(x, y, barWidth, barHeight);
					x += barWidth;
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
		 * Add text to this label.
		 * @param startX the top left x coordinate
		 * @param startY the top left y coordinate
		 * @param text the text to add
		 * @param fontSize the font size for the text
		 * @param fontStyle the font style for the text
		 */
		private void addText(TextObject t, Graphics2D g2) {
			Bounds b = t.getBounds();
			int fontSize = b.getHeight();
			String text = getItemField(item, t.getFieldType());
			double ml = b.getWidth()/(6);
			if (text.length() > ml) {
				double fraction = ml / text.length();
				fontSize = (int)Math.round(fontSize * fraction);
			}
			Font font = new Font("SansSerif", Font.BOLD, fontSize);
	        g2.setFont(font);
        	g2.setColor(t.getColor());
	        FontRenderContext frc = g2.getFontRenderContext();
			TextLayout tl = new TextLayout(text, font, frc);
	        Rectangle2D textBounds = tl.getBounds(); 
			int fontStartY = b.getyMin() + (int) textBounds.getHeight();
			int fontStartX = b.getxMin();
			if (t.getFieldType() == LabelFieldOption.COMPANY) {
				fontStartX = b.getxMax() - (int) b.getWidth();
			}
	        g2.drawString(text, fontStartX, fontStartY);
		}
	    
	    private String getItemField(Item item, LabelFieldOption fieldType) {
			switch (fieldType) {
			case COMPANY:
				return item.getCompany().toUpperCase();
			case HUMAN_READABLE_GTIN:
				return "(01)" + item.getGtin();
			case PRODUCT_NAME:
				return item.getProductName().toUpperCase();
			case UNIT:
				return item.getUnit();
			case DATE_LABEL:
				return format.getFieldTypesUsed().get(LabelFieldOption.DATE_LABEL);
			case SHIP_DATE:
				return item.getShipDate().getAsLabelDate();
			case VPC_SMALL:
				return smallVPC;
			case VPC_LARGE:
				return largeVPC;
			default:
				return "";
			}
		}
	}
}
