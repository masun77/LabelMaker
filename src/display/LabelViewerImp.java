package display;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LabelViewerImp implements LabelView {

	@Override
	public void showLabels(ArrayList<Labelable> items) {
		JFrame f = new JFrame("View Labels");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		for (int i = 0; i < items.size(); i++) {
			mainPanel.add(items.get(i).getLabel());
		}

		f.add(mainPanel);
		localPack(f);
		f.setVisible(true);
	}
	
	/**
	 * Set the frame's size to the maximum minimum width and height of its children
	 * 
	 * @param frame the frame to set the size of
	 */
	private void localPack(Container frame) {
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
