/**
 * JPanel which stacks children vertically. 
 */

package display;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class VPanel extends JPanel {	
	public VPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.white);
	}
}
