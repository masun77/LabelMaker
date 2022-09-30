package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OverallTester {
	private volatile boolean windowOpen = true;

	@Test
	void test() {

		MainFrame main = new MainFrame();
		main.showOrderDisplay();
		
		while(windowOpen) {
			windowOpen = main.isWindowOpen();
		}
		System.out.println("done");
	}

}
