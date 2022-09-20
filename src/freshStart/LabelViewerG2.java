package freshStart;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelViewerG2 {
	public void showLabel(Item i, LabelFormat lf) {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(lf.getLabelDimensions().getxMax(), 
				lf.getLabelDimensions().getyMax()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel tester = new LabelPanel(i, lf);
		frame.add(tester);
		
	}
	
	private class LabelPanel extends JPanel {
		private Item item;
		private LabelFormat format;

	    public LabelPanel(Item i, LabelFormat lf) {
	    	item = i;
	    	format = lf;
	    }

	    public Dimension getPreferredSize() {
	        return new Dimension(410,300);
	    }

	    public void paintComponent(Graphics g) {
	        super.paintComponent(g); 
	        Graphics2D g2 = (Graphics2D) g;

	        // Draw Text
	        for (TextObject t: format.getTextObjects()) {
	        	g.drawString(getItemField(item, t.getFieldType()),t.getBounds().getxMin(),
	        			t.getBounds().getyMax());
			}
	    }  
	    
	    private String getItemField(Item item, LabelFieldOption fieldType) {
			switch (fieldType) {
			case COMPANY:
				return item.getCompany();
			case HUMAN_READABLE_GTIN:
				return item.getGtin();
			case PRODUCT_NAME:
				return item.getProductName();
			case UNIT:
				return item.getUnit();
			case DATE_LABEL:
				return format.getFieldTypesUsed().get(LabelFieldOption.DATE_LABEL);
			case SHIP_DATE:
				return item.getShipDate().getAsLabelDate();
			case VPC_SMALL:
				return "vs";
			case VPC_LARGE:
				return "vL";
			default:
				return "";
			}
		}
	}
}
