package importData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Order;

class ExcelWriterTester {

	@Test
	void test() {
		ExcelFormatGetter efg = new ExcelFormatGetter();
		efg.readExcelFormats();
		ExcelReader er = new ExcelReader();
		ArrayList<Order> orders = er.getOrdersFromFile("resources/test.xlsx", efg.getFormats().get(0));
		
		ExcelWriter ew = new ExcelWriter();
		ew.writeOrders(orders, efg.getFormats().get(0));
	}

}
