package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class SetSizeLabel extends JLabel {
	private Dimension dimension = new Dimension(200,50);
	
	public void setAllSizes(Dimension d) {
		dimension = d;
		this.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));	
		this.setFont(new Font("SansSerif", Font.PLAIN, 20));
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
