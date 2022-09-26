/**
 * Test the order display
 */

package freshStart;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class OrderDisplayTester {
	private static volatile boolean windowOpen = true;

	//@Test
	void test() {
		OrderDisplay od = new OrderDisplay();
		od.displayOrders(new ArrayList<Order>());
		
		while (true) {
			
		}
	}
	
	// @Test
	void testWcontent() {
		OrderDisplay od = new OrderDisplay();
		ArrayList<Order> ords = new ArrayList<Order>();
		Order o = new Order("Maya", "1234", "RDF 2", 57832, new DateImp(9,20,2022));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 2, 25));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Garlic", 
				"GRG10", "818181110", "10 lbs/cs", 1, 90));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Carrots", 
				"CAR25", "818182220", "25 lbs/cs", 3, 75));
		ords.add(o);
		Order o2 = new Order("Joe", "AAA", "RDF 1", 1111, new DateImp(9,20,2022));
		o2.addItem(new Item("Joe", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 1, 13));
		o2.addItem(new Item("Maya", new DateImp(9,20,22), "Leeks", 
				"KRL20", "818183330", "20 lbs/cs", 1, 50));
		ords.add(o2);
		
		od.displayOrders(ords);
		
		while (true) {
			
		}
	}
	
	//@Test
	void testOrderSelection() {
		OrderDisplay od = new OrderDisplay();
		ArrayList<Order> ords = new ArrayList<Order>();
		Order o = new Order("Maya", "1234", "RDF 2", 57832, new DateImp(9,20,2022));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 2, 25));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Garlic", 
				"GRG10", "818181110", "10 lbs/cs", 1, 90));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Carrots", 
				"CAR25", "818182220", "25 lbs/cs", 3, 75));
		ords.add(o);
		Order o2 = new Order("Joe", "AAA", "RDF 1", 1111, new DateImp(9,20,2022));
		o2.addItem(new Item("Joe", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 1, 13));
		o2.addItem(new Item("Maya", new DateImp(9,20,22), "Leeks", 
				"KRL20", "818183330", "20 lbs/cs", 1, 50));
		ords.add(o2);
		
		JFrame f = od.displayOrders(ords);  // You have to select the Maya order, only
		while (windowOpen) {	// Cool! not sure I'm using volatile right there, but it works!
			windowOpen = f.isVisible();
		}

		ArrayList<Order> ordsSelected = od.getOrdersSelected();
		assertEquals(1, ordsSelected.size());
		assertTrue("Maya".equals(ordsSelected.get(0).getCompany()));
	}
	
	@Test
	void testItemSelection() {
		OrderDisplay od = new OrderDisplay();
		ArrayList<Order> ords = new ArrayList<Order>();
		Order o = new Order("Maya", "1234", "RDF 2", 57832, new DateImp(9,20,2022));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 2, 25));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Garlic", 
				"GRG10", "818181110", "10 lbs/cs", 1, 90));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Carrots", 
				"CAR25", "818182220", "25 lbs/cs", 3, 75));
		ords.add(o);
		Order o2 = new Order("Joe", "AAA", "RDF 1", 1111, new DateImp(9,20,2022));
		o2.addItem(new Item("Joe", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 1, 13));
		o2.addItem(new Item("Maya", new DateImp(9,20,22), "Leeks", 
				"KRL20", "818183330", "20 lbs/cs", 1, 50));
		ords.add(o2);
		
		JFrame f = od.displayOrders(ords);
		while (windowOpen) {	
			windowOpen = f.isVisible();
		}

		ArrayList<Item> itemsSelected = od.getItemsSelected();
		assertEquals(3, itemsSelected.size());    // You have to select lettuce, garlic, and leeks in order, only
		assertTrue("Lettuce".equals(itemsSelected.get(0).getProductName()));
		assertTrue("Garlic".equals(itemsSelected.get(1).getProductName()));
		assertTrue("Leeks".equals(itemsSelected.get(2).getProductName()));
	}
}
