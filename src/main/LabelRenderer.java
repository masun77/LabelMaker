package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.print.*;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LabelRenderer implements GraphicsRenderer {
	private Container window = null;
	private ArrayList<Item> items = null;
	private final int LABEL_HEIGHT = 260;
	
	@Override
	public void renderLabels(ArrayList<Item> items) {
		this.items = items;
		JFrame f = new JFrame("View Labels");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setOpacity(1);
		
		JPanel mainPanel= new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		window = mainPanel;
		
		for (int i = 0; i < items.size(); i++) {
			RDFLabel label = new RDFLabel(items.get(i), 0, 0);
		    mainPanel.add(label);
		    System.out.println("Adding: " + items.get(i).getCustomer() + " " + mainPanel.getComponents().length);
		}
		
		f.add(mainPanel);
		
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
		height = height * 2;
		frame.setSize(width, height);
	}
	
	public void print() {
		PrinterJob  job = PrinterJob.getPrinterJob();
		Printable printer = new Printer(window);
		job.setPrintable(printer);
		boolean doPrint = job.printDialog();
		
		if (doPrint) {
			try {
		        job.print();
		    } catch (PrinterException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	
}
