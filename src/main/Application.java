package main;
import java.util.ArrayList;

import database.DataClient;
import database.RefreshFunction;
import database.SocketClient;
import localBackup.DataSaver;
import printing.PrintManager;
import userInterface.DeleteOrderFunction;
import userInterface.DeselectAllFunction;
import userInterface.EditOrderFunction;
import userInterface.EntryForm;
import userInterface.ExcelImportFunction;
import userInterface.LabelViewerImp;
import userInterface.OrderDisplay;
import userInterface.RDFInterface;
import userInterface.SelectAllFunction;
import userInterface.UserInterface;

public class Application {
	private UserInterface ui;
	
	public Application() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		
		ui = new RDFInterface();
		ui.addHomeFunction(new OrderDisplay());
		ui.addFunction(new SelectAllFunction(), "Select All Orders");
		ui.addFunction(new DeselectAllFunction(), "Deselect All Orders");
		ui.addBreakBetweenFunctions();
		ui.addFunction(new LabelViewerImp(), "View/Print Labels for selected");
		ui.addBreakBetweenFunctions();
		ui.addFunction(new EntryForm(), "New Order");
		ui.addFunction(new EditOrderFunction(), "Edit Selected Order");
		ui.addFunction(new DeleteOrderFunction(), "Delete selected orders");
		ui.addBreakBetweenFunctions();
		ui.addFunction(new RefreshFunction(), "Update orders from server");
		ui.addFunction(new ExcelImportFunction(), "Import invoices from Excel file");
	}
	
	public void run() {		
		ui.showInterface();	
		ServerUpdater updater = new ServerUpdater();
		updater.start();
	}
	
	private class ServerUpdater extends Thread {

		@Override
	    public void run(){
			while (true) {
				System.out.println("updating");
				DataClient dc = AppState.getDataClient();
				ArrayList<Order> orders =  dc.getOrders();
				if (orders.size() > 0) {
					AppState.setOrders(orders);				}
				try {
				    Thread.sleep(300L * 1000L);
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
			}
	    }
	}
}