package freshStart;


import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelViewerG2 {
	public void showLabel(Item i, LabelFormat lf) {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(lf.getLabelDimensions().getxMax(), 
				lf.getLabelDimensions().getyMax()));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		JPanel tester = new JPanel();
		tester.setSize(new Dimension (410,300));
		for (TextObject t: lf.getTextObjects()) {
			tester.add(new JLabel(t.getText()));
		}
		frame.add(tester);
		
	}
}
