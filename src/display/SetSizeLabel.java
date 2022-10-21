package display;

import java.awt.Dimension;

import javax.swing.JLabel;

public class SetSizeLabel extends JLabel {
	private Dimension dimension = new Dimension(200,50);
	
	public void setAllSizes(Dimension d) {
		dimension = d;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return dimension;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return dimension;
	}
	
	@Override
	public Dimension getMaximumSize() {
		return dimension;
	}
}
