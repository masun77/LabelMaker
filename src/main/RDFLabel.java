package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RDFLabel extends Component {
	private Item item;

	public RDFLabel(Item o) {
		item = o;
		this.setMinimumSize(new Dimension(1000,400));
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		createLabel(g2);
	}
	
	/**
	 * Create
	 * @param g2
	 */
	private void createLabel(Graphics2D g2) {
		LabelUtilities.createGS1_128GTINBarCode(g2, 10,10, item.getGtin());

		// todo: working on making label using graphics library
	}
}
