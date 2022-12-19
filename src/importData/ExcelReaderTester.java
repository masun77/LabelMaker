package importData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import main.Order;

class ExcelReaderTester {
	private ExcelReader er = new ExcelReader();
	
	@Test
	void testReadOrders() {
		ArrayList<Order> orders = er.getOrdersFromFile("", null);
		assertEquals(0, orders.size());
		ExcelFormatGetter efg = new ExcelFormatGetter();
		efg.readExcelFormats();
		System.out.println(efg.getFormatNames());
		orders = er.getOrdersFromFile("resources/7.27.xlsx", efg.getFormats().get(1));
//		for (Order o: orders) {
//			o.printOrder();
//			System.out.println();
//		}
		assertEquals(8, orders.size());
		assertEquals("Boulette's LLC", orders.get(0).getCompany());
		
		orders = er.getOrdersFromFile("resources/test.xlsx", efg.getFormats().get(0));
		assertEquals(4, orders.size());
		System.out.println(orders.toString());
	}
	
	@Test
	void testBadOrders() {
		ExcelFormatGetter efg = new ExcelFormatGetter();
		efg.readExcelFormats();
		ArrayList<Order>orders = er.getOrdersFromFile("resources/test.xlsx", efg.getFormats().get(0));
		for (Order o: orders) {
			o.printOrder();
			System.out.println();
		}
		assertEquals(4, orders.size());
		assertEquals("Boulette's LLC", orders.get(0).getCompany());
	}
	
	@Test
	void testGetFile() {
		assertEquals(null, er.getFileInputStream(""));
		assertNotEquals(null, er.getFileInputStream("resources/7.27.xlsx"));
		assertEquals(null, er.getFileInputStream("hello.xlsx"));
	}
}
