package database;    // this error is because I'm running java 1.7, not a higher version

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import localBackup.DataSaver;
import main.AppState;
import main.Order;
import printing.PrintManager;

class ExcelReaderTest {

	@Test
	void test() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		
		DataImporter di = new ExcelInvoiceReader();
		di.setResourcePath("resources/7.27.xlsx");
		ArrayList<Order> orders = di.readInvoices();
		
		System.out.println("Orders: ");
		for (Order o: orders) {
			o.printOrder();
		}

	}

}
