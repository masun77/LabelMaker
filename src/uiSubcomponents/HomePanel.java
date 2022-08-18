package uiSubcomponents;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomePanel extends HPanel {
	private Dimension minimumSize = new Dimension(1000,700);
	private JFrame frame;
	
	public HomePanel(JFrame f) {
		frame = f;
		System.out.println("homep");
	}

	public HomePanel() {
		this.setMinimumSize(minimumSize);
		
		this.addComponentListener(new ComponentAdapter(){
	        public void componentResized(ComponentEvent e){
	        	System.out.println("resized");
	            Dimension d = HomePanel.this.getSize();
	            if(d.width<minimumSize.width || d.height<minimumSize.height) {
		            HomePanel.this.setSize(minimumSize);
		            frame.setSize(minimumSize);
		            System.out.println("fix");
	            }
	        }
	    });
	}
}
