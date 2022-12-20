package display;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.border.MatteBorder;

public class ItemNameLabel extends JLabel {
	private int textSize = 20;
	private int maxLength = 250;
	
	public ItemNameLabel(String s, int size) {
		super(s);
		this.setAlignmentY(Component.TOP_ALIGNMENT);
		textSize = size;
	}
	
	@Override
    public void paintComponent(Graphics g) {
		Font font = getFontForText(this.getText(), g);
		g.setFont(font);
		this.setFont(font);	
		this.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        super.paintComponent(g);
    }
	
	/**
	 * Get an appropriate font size for the given text within the given bounds
	 * so that it fills the space but doesn't overflow it.
	 * @param text the text to get the font size for
	 * @param b the bounds within which to fit the text
	 * @return a font size to fit the text to the bounds
	 */
	private Font getFontForText(String text, Graphics g) {
		int size = textSize;
		int maxHeight = 48;
		Font font = new Font("SansSerif", Font.BOLD, size);
		return font;
	}
}
