package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class OverallTester {

	@Test
	void test() {
		ExcelFormatGetter efg = new ExcelFormatGetter();
		efg.readExcelFormats();
		ExcelReader reader = new ExcelReader();
		ArrayList<Order> orders = reader.getOrdersFromFile("src/freshStart/7.27.xlsx", efg.getFormats().get(0));
		OrderDisplay display = new OrderDisplay();
		display.displayOrders(orders);
		
		while(true) {}
	}

}
