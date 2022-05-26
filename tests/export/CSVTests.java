package export;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import labels.DateImp;
import main.Item;
import main.Order;
import main.RDFItem;
import main.Utilities;

public class CSVTests {

	@Test
	void test() {
		ArrayList<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Utilities.getItemArrayList(
				new RDFItem("Long company name", "Arugula", "3 lbs", "00000123456789", new DateImp(4,2,2022), 1))));
		orders.add(new Order(Utilities.getItemArrayList(new RDFItem())));
		orders.add(new Order(Utilities.getItemArrayList(
				new RDFItem("Maya", "Kale", "10 lb case", "474747", new DateImp(5,1,2022), 5))));
		orders.add(new Order(Utilities.getItemArrayList(
				new RDFItem("Kris", "Kale", "12 bunches", "474747", new DateImp(4,20,2022), 4))));
		orders.add(new Order(new ArrayList<Item>(Arrays.asList(
				new RDFItem("Yao", "Kale", "10 lb case", "474747", new DateImp(1,1,2022), 2),
				new RDFItem("Yao", "Arugula", "10 lb case", "00000123456789", new DateImp(1,1,2022), 3)))));
		DataSaver.writeOrdersToCSV(orders, "resources/Orders.csv");
	}
}
