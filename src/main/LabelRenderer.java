package main;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;

public class LabelRenderer implements GraphicsRenderer {

	@Override
	public void renderLabel(Item item) {
		 JFrame f = new JFrame("View Labels");
	     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
	     
	     f.add("Center", new RDFLabel(item));
	     localPack(f);
	     f.setVisible(true);
	}
	
	private void localPack(JFrame frame) {
		Component[] children = frame.getComponents();
		int height = 0;
		int width = 0;
		Component current = null;
		for (int i = 0; i < children.length; i++) {
			current = children[i];
			Dimension d = current.getMinimumSize();
			height += d.getHeight();
			if (d.getWidth() > width) {
				width = (int) d.getWidth();
			}
		}
		frame.setSize(width, height);
	}
}
