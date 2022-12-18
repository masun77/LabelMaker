package main;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import display.MainFrame;

class OverallTester {
	private volatile boolean windowOpen = true;

	@Test
	void test() {

		MainFrame main = new MainFrame(JFrame.DISPOSE_ON_CLOSE);
		main.showOrderDisplay();
		
		while(windowOpen) {
			windowOpen = main.isWindowOpen();
		}
		System.out.println("done");
	}

}
