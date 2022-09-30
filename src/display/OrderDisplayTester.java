/**
 * Test the order display
 */

package display;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import labels.DateImp;
import main.Item;
import main.Order;

class OrderDisplayTester {
	private static volatile boolean windowOpen = true;
	private OrderDisplay od;
	private ArrayList<Order> orders = new ArrayList<Order>();
	
	@BeforeEach
	public void setup() {
		od = new OrderDisplay();
		Order o = new Order("Maya", "1234", "RDF 2", 57832, new DateImp(9,20,2022));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 2, 25));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Garlic", 
				"GRG10", "818181110", "10 lbs/cs", 1, 90));
		o.addItem(new Item("Maya", new DateImp(9,20,22), "Carrots", 
				"CAR25", "818182220", "25 lbs/cs", 3, 75));
		orders.add(o);
		Order o2 = new Order("Joe", "AAA", "RDF 1", 1111, new DateImp(9,20,2022));
		o2.addItem(new Item("Joe", new DateImp(9,20,22), "Lettuce", 
				"LG10", "818180000", "10 lbs/cs", 1, 13));
		o2.addItem(new Item("Maya", new DateImp(9,20,22), "Leeks", 
				"KRL20", "818183330", "20 lbs/cs", 1, 50));
		orders.add(o2);
	}

	//@Test
	void test() {
		OrderDisplay od = new OrderDisplay();
		od.displayOrders(new ArrayList<Order>());
		
		while (true) {
			
		}
	}
	
	// @Test
	void testWcontent() {
		od.displayOrders(orders);
		
		while (true) {
			
		}
	}
	
	// @Test
	// To pass, before closing the window, 
	// select the Maya order but nothing else
	void testOrderSelection() {
		JFrame f = od.displayOrders(orders);
		while (windowOpen) {	// Cool! not sure I'm using volatile right there, but it works!
			windowOpen = f.isVisible();   
		}

		ArrayList<Order> ordsSelected = od.getOrdersSelected();
		assertEquals(1, ordsSelected.size());
		assertTrue("Maya".equals(ordsSelected.get(0).getCompany()));
	}
	
	// To pass, before closing the window,
	// select the lettuce and garlic items, in that order, but nothing else
	//@Test
	void testIndivItemSelection() {
		JFrame f = od.displayOrders(orders);
		while (windowOpen) {	
			windowOpen = f.isVisible();   
		}

		ArrayList<Item> items = od.getItemsSelected();
		assertEquals(2, items.size());
		assertTrue("Lettuce".equals(items.get(0).getProductName()));
		assertTrue("Garlic".equals(items.get(1).getProductName()));
	}
	
	// To pass, before closing the window,
	// select the lettuce and garlic items, in that order, but nothing else
	@Test
	void testItemRowSelection() {
		JFrame f = od.displayOrders(orders);
		while (windowOpen) {	
			windowOpen = f.isVisible();   
		}

		ArrayList<Item> items = od.getItemsSelected();
		
		System.out.println(items);
		
		assertEquals(2, items.size());
		assertTrue("Lettuce".equals(items.get(0).getProductName()));
		assertTrue("Maya".equals(items.get(0).getCompany()));
		assertTrue("Lettuce".equals(items.get(1).getProductName()));
		assertTrue("Joe".equals(items.get(1).getCompany()));
		
		
	}
}
