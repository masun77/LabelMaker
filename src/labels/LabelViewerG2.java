/**
 * LabelViewerG2
 * Displays a label to the screen using the Java
 * Graphics2D and Swing libraries. 
 */

package labels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Item;

public class LabelViewerG2 implements LabelView {
	/**
	 * Show the label on the screen for the given item and label format
	 * @param i The item to show the label for
	 * @param lf the format to show the information in
	 */
	public void showLabel(Item i, LabelFormat lf) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // todo change when using
		frame.setSize(new Dimension(lf.getLabelDimensions().getxMax(), 
	        		lf.getLabelDimensions().getyMax()));
		JPanel tester = new LabelPanel(i, lf);
		frame.add(tester);
		frame.setVisible(true);
	}
	
	@Override
	public JPanel getSingleLabel(Item i, LabelFormat lf) {
		return new LabelPanel(i, lf);
	}

	@Override
	public ArrayList<JPanel> getLabelsForList(ArrayList<Item> items, LabelFormat lf) {
		ArrayList<JPanel> labels = new ArrayList<>();
		for (Item i: items) {
			for (int lab = 0; lab < i.getQuantity(); lab++) {
				labels.add(new LabelPanel(i, lf));
			}
		}
		return labels;
	}

	@Override
	public ArrayList<JPanel> getLabelsForItem(Item i, LabelFormat lf) {
		ArrayList<JPanel> labels = new ArrayList<>();
		for (int lab = 0; lab < i.getQuantity(); lab++) {
			labels.add(new LabelPanel(i, lf));
		}
		return labels;
	}
	
	/**
	 * Panel containing one label.
	 *
	 */
	private class LabelPanel extends JPanel {
		private Item item;
		private LabelFormat format;
		private ArrayList<Integer> barCode;
		private String smallVPC;
		private String largeVPC;

		/**
		 * Set up the instance variables and get the bar code and voice pick code.
		 * @param i the item to use for the label
		 * @param lf the label's format
		 */
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
		
	    /**
	     * For any string which could be empty, set it to a nonempty string.
	     */
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

		/**
		 * Set preferred size based on the label format.
		 */
	    public Dimension getPreferredSize() {
	        return new Dimension(format.getLabelDimensions().getxMax(), 
	        		format.getLabelDimensions().getyMax());
	    }

	    /**
	     * Set the background to white. Add the rectangles and text
	     * given in the label format.
	     */
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
		 * @param g the graphics context
		 */
		private void setBackgroundWhite(Graphics2D g) {
			this.setBackground(Color.white);
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(getForeground());
		}
		/**
		 * Add the rectangles for the bar code for this label
		 * @param r the bounds of the bar code
		 * @param g2 the graphics context
		 */
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
		 * Add the given text to the label.
		 * @param t the TextObject to add text from
		 * @param g2 the graphics context
		 */
		private void addText(TextObject t, Graphics2D g2) {
			Bounds b = t.getBounds();
			String text = getItemField(item, t.getFieldType());
			int size = 30;
			if (t.getFieldType() == LabelFieldOption.VPC_LARGE) {
				size = 50;
			}
			else if (t.getFieldType() == LabelFieldOption.VPC_SMALL) {
				size = 20;
			}
			
			Font font = getFontForText(text, b, g2, size);
	        g2.setFont(font);
        	g2.setColor(t.getColor());
			
			TextLayout tl = new TextLayout(text, font, g2.getFontRenderContext());
	        Rectangle2D textBounds = tl.getBounds(); 
			int fontStartY = b.getyMin() + (int) textBounds.getHeight();
			int fontStartX = b.getxMin();

			if (t.getFieldType() == LabelFieldOption.COMPANY) {   // Right align company
				fontStartX = b.getxMax() - g2.getFontMetrics(font).stringWidth(text) - 2;
			}
			g2.drawString(text, fontStartX, fontStartY);
		}
		
		/**
		 * Get an appropriate font size for the given text within the given bounds
		 * so that it fills the space but doesn't overflow it.
		 * @param text the text to get the font size for
		 * @param b the bounds within which to fit the text
		 * @return a font size to fit the text to the bounds
		 */
		private Font getFontForText(String text, Bounds b, Graphics2D g2, int size) {
			Font font = new Font("SansSerif", Font.BOLD, size);
			FontMetrics metrics = g2.getFontMetrics(font);
			int height = metrics.getHeight();
			int width = metrics.stringWidth(text);
			int boundHeight = b.getHeight();
			int boundWidth = b.getWidth();

			if (text.length() == 2) {   // For VPC, just leave it large
				return font;
			}
			
			while (boundHeight < height || boundWidth < width) {
				size -= 1;
				font = new Font("SansSerif", Font.BOLD, size);
				metrics = g2.getFontMetrics(font);
				height = metrics.getHeight();
				width = metrics.stringWidth(text);
				boundHeight = b.getHeight();
				boundWidth = b.getWidth();
			}
			
			return font;
		}
	    
		/**
		 * Get the info from the Item corresponding to the 
		 * fieldType specified by the label format
		 * @param item the item to get data from
		 * @param fieldType which data to get from the item
		 * @return a String representation of the data
		 */
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
