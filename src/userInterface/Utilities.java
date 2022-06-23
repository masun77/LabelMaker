package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import labels.LabelableItem;

public class Utilities {
	/**
	 * Set the frame's size to the maximum minimum width and the combined height of its children
	 * 
	 * @param frame the frame to set the size of
	 */
	public static void localVPack(Container frame) {
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
		setAllSizes(frame, width, height);
	}
	
	/**
	 * Set the frame's size to the maximum minimum height and the combined width of its children
	 * 
	 * @param frame the frame to set the size of
	 */
	public static void localHPack(Container frame) {
		Component[] children = frame.getComponents();
		int height = 0;
		int width = 0;
		Component current = null;
		for (int i = 0; i < children.length; i++) {
			current = children[i];
			Dimension d = current.getMinimumSize();
			width += d.getWidth();
			if (d.getHeight() > height) {
				height = (int) d.getHeight();
			}
		}
		setAllSizes(frame, width, height);
	}
	
	private static void setAllSizes(Container frame, int width, int height) {
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));
	}
	
	public static void setMinMax(Component comp, Dimension dim) {
		comp.setMaximumSize(dim);
		comp.setMinimumSize(dim);
	}
}
