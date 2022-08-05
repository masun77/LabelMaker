package labels;

import static org.junit.Assert.*;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.junit.Test;

import main.RDFItem;

public class LabelDisplayTest {

	@Test
	public void test() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		LabelableItem label = new RDFItem();
		frame.add(label.getLabel());
		frame.setSize(new Dimension(500,500));
		frame.setVisible(true);
		try {
			Thread.sleep(60L * 1000L);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
