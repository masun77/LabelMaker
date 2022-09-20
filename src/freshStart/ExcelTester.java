package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ExcelTester {
	private ExcelReader er = new ExcelReader();
	
	@Test
	void testReadOrders() {
		ArrayList<Order> orders = er.getOrdersFromFile("", null);
		assertEquals(0, orders.size());
		orders = er.getOrdersFromFile("src/freshStart/7.27.xlsx", null);
		assertEquals(7, orders.size());
		assertEquals("Boulette's LLC", orders.get(0).getCompany());
	}
	
	@Test
	void testGetFile() {
		assertEquals(null, er.getFileInputStream(""));
		assertNotEquals(null, er.getFileInputStream("src/freshStart/7.27.xlsx"));
		assertEquals(null, er.getFileInputStream("hello.xlsx"));
	}
}
