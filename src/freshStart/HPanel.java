package freshStart;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HPanel extends JPanel {
	private static Border blackline = BorderFactory.createLineBorder(Color.black);
	
	public HPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(Color.white);
		this.setBorder(blackline);
	}
}
