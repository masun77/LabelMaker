package main;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import labels.Labelable;

public class Utilities {
	public static ArrayList<Item> getItemArrayList(Item i) {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(i);
		return items;
	}
	
	public static ArrayList<Labelable> getLabelableArrayList(Item i) {
		ArrayList<Labelable> items = new ArrayList<Labelable>();
		items.add(i);
		return items;
	}
	
	public static ArrayList<Order> getOrderArrayList(Order o) {
		ArrayList<Order> ord = new ArrayList<Order>();
		ord.add(o);
		return ord;
	}

	/**
	 * Set the frame's size to the maximum minimum width and combined height of its children
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
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));
	}
	
	/**
	 * Set the frame's size to the maximum minimum height and combined width of its children
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
		frame.setSize(width, height);
		frame.setMinimumSize(new Dimension(width, height));
		frame.setPreferredSize(new Dimension(width, height));
	}
	
	public static void setMinMax(Component comp, Dimension dim) {
		comp.setMaximumSize(dim);
		comp.setMinimumSize(dim);
	}
}
