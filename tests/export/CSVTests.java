package export;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import labels.DateImp;
import labels.LabelableItem;
import localBackup.DataSaver;
import localBackup.LocalFileBackup;
import main.Item;
import main.Order;
import main.RDFItem;
import userInterface.Utilities;

public class CSVTests {
	private static ArrayList<Order> orders = new ArrayList<Order>();
	private static LocalFileBackup fb = new DataSaver();
	
	@BeforeAll
	private static void setup() {
		ArrayList<LabelableItem> items1 = new ArrayList<LabelableItem>();
		items1.add(new RDFItem("Long company name", "Arugula", "3 lbs", "00000123456789", new DateImp(4,2,2022), 1f, 0f,""));
		items1.add(new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022), 5f, 0f,""));
		Order o1 = new Order(items1);
		
		ArrayList<LabelableItem> items2 = new ArrayList<LabelableItem>();
		items2.add(new RDFItem("Long company name", "Arugula", "3 lbs", "00000123456789", new DateImp(4,2,2022), 1f, 0f,""));
		items2.add(new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022), 5f, 0f,""));
		Order o2 = new Order(items2);
		items2.add(new RDFItem("Kris", "Kale", "12 bunches", "474747", new DateImp(4,20,2022), 4f, 0f,""));
		items2.add(new RDFItem("Yao", "Kale", "10 lb case", "474747", new DateImp(1,1,2022), 2f, 0f,""));
		items2.add(new RDFItem("Yao", "Arugula", "10 lb case", "00000123456789", new DateImp(1,1,2022), 3f, 0f,""));
		
		
		orders.add(o1);
		orders.add(o2);
	}

	@Test
	void testWrite() {
		fb.saveOrders(orders);
	}
	
	@Test
	void testRead() {
		//DataSaver.readOrdersFromCSV("resources/Orders2.csv");
	}
}
