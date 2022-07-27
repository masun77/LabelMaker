package userInterface;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import database.SocketClient;
import localBackup.DataSaver;
import main.AppState;
import main.Order;
import printing.PrintManager;

class ExcelFunctionTest {

	@Test
	void test() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		ExcelImportFunction eif = new ExcelImportFunction();
		eif.showFunction();
	}

}
