package userInterface.graphicComponents;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class HPanel extends JPanel {
	
	public HPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(Color.white);
	}
}
