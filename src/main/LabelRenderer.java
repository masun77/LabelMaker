package main;

import javax.swing.JFrame;

public class LabelRenderer implements GraphicsRenderer {

	@Override
	public void renderLabel(Order o) {

		 JFrame f = new JFrame("View Labels");
	     f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	     f.setSize(600,200); // todo - size or pack or?
	     
	     f.add("Center", new Label(o));
	     f.setVisible(true);
	}
}
