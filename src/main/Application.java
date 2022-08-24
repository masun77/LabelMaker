package main;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import database.DataClient;
import database.RefreshFunction;
import database.SocketClient;
import localBackup.DataSaver;
import printing.PrintManager;
import uiDisplay.EntryForm;
import uiDisplay.OrderDisplay;
import uiDisplay.RDFInterface;
import uiLogic.DeleteOrderFunction;
import uiLogic.DeselectAllFunction;
import uiLogic.EditOrderFunction;
import uiLogic.ExcelImportFunction;
import uiLogic.LabelViewLogic;
import uiLogic.SelectAllFunction;
import uiLogic.UserInterface;

public class Application {
	private UserInterface ui;
	
	public Application() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		
		ui = new RDFInterface();
		ui.addHomeFunction(new OrderDisplay());
		ui.addFunction(new SelectAllFunction(), "Select All Orders", "");
		ui.addFunction(new DeselectAllFunction(), "Deselect All Orders", "");
		ui.addBreakBetweenFunctions();
		ui.addFunction(new LabelViewLogic(), "View/Print Labels for selected", "resources/printer.png");
		ui.addBreakBetweenFunctions();
		ui.addFunction(new EntryForm(), "+ New Order", "");
		ui.addFunction(new EditOrderFunction(), "Edit Selected Order", "resources/edit.png");
		ui.addFunction(new DeleteOrderFunction(), "- Delete selected orders", "");
		ui.addBreakBetweenFunctions();
		ui.addFunction(new RefreshFunction(), "Update orders from server", "resources/reload.png");
		ui.addFunction(new ExcelImportFunction(), "Import invoices from Excel file", "resources/import.png");
		AppState.setLastListener((RDFInterface)ui);
	}
	
	public void run() {		
		ui.showInterface();	
		ServerUpdater updater = new ServerUpdater();
		updater.setDaemon(true);
		updater.start();
	}
	
	private class ServerUpdater extends Thread {
		@Override
	    public void run(){
			while (true) {
				ExecutorService executor = Executors.newSingleThreadExecutor();
		        Future<Integer> future = executor.submit(new Task());
		        try {
		            System.out.println("Started..");
		            System.out.println(future.get(3, TimeUnit.SECONDS));
		            System.out.println("Finished!");
		        } catch (Exception e) {
		            future.cancel(true);
		            System.out.println("Terminated!");
		        }
		        executor.shutdownNow();
		        try {
				    Thread.sleep(300L * 1000L);
				} catch (InterruptedException e) {
				    e.printStackTrace();
				}
			}
	    }
	}
	
	private class Task implements Callable<Integer> {
	    @Override
	    public Integer call() throws Exception {
			DataClient dc = AppState.getDataClient();
			ArrayList<Order> orders =  dc.getOrders();
			if (orders.size() > 0) {
				AppState.setOrders(orders);				}
			
	        return 77;
	    }
	}
}