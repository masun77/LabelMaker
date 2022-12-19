package display;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class TextSizeLabel extends JLabel {
	private int textSize = 20;
	
	public TextSizeLabel() {
		super();
		setup();
	}
	
	public TextSizeLabel(String s) {
		super(s);
		setup();
	}
	
	private void setup() {
		this.setFont(new Font("SansSerif", Font.PLAIN, textSize));
		this.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));	
		this.setHorizontalAlignment(SwingConstants.RIGHT);
	}
}
