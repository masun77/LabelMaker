package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class OverallTester {
	private volatile boolean windowOpen = true;

	@Test
	void test() {
		ExcelFormatGetter efg = new ExcelFormatGetter();
		efg.readExcelFormats();
		ExcelReader reader = new ExcelReader();
		ArrayList<Order> orders = reader.getOrdersFromFile("src/freshStart/7.27.xlsx", efg.getFormats().get(0));
		OrderDisplay display = new OrderDisplay();

		MainFrame main = new MainFrame();
		LabelFormat lf = new LabelFormatReader().readLabelFormats().get(0);
		main.showOrderDisplay(display, orders, lf);
		
		while(windowOpen) {
			windowOpen = main.isWindowOpen();
		}
		System.out.println("done");
	}

}
