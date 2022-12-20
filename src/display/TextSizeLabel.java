package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

import labels.Bounds;

public class TextSizeLabel extends JLabel {
	private int textSize = 20;
	private boolean resizeText = false;
	private int maxLength = 0;
	
	public TextSizeLabel() {
		super();
		setup();
	}
	
	public TextSizeLabel(String s) {
		super(s);
		setup();
	}
	
	public TextSizeLabel(String s, boolean resize, int ml) {
		super(s);
		setup();
		resizeText = resize;
		maxLength = ml;
	}
	
	private void setup() {	
		this.setHorizontalAlignment(SwingConstants.RIGHT);
	}
	
	 @Override
    public void paintComponent(Graphics g) {
		if (resizeText) {
			Font font = getFontForText(this.getText(), g);
			g.setFont(font);
			this.setFont(font);	
			this.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
			
		}
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
		FontMetrics metrics = g.getFontMetrics(font);
		int height = metrics.getHeight();
		int width = metrics.stringWidth(text);
		
		while (maxHeight < height || maxLength < width) {
			size -= 1;
			font = new Font("SansSerif", Font.BOLD, size);
			metrics = g.getFontMetrics(font);
			height = metrics.getHeight();
			width = metrics.stringWidth(text);
		}
		
		return font;
	}
}
