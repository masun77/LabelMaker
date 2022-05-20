package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

public class DisplayUtilities {
	/**
	 * Set the frame's size to the maximum minimum width and height of its children
	 * 
	 * @param frame the frame to set the size of
	 */
	public static void localPack(Container frame) {
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
		frame.setPreferredSize(new Dimension(width, height));
	}
}
