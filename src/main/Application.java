package main;
import java.util.ArrayList;

import database.RefreshFunction;
import database.SocketClient;
import localBackup.DataSaver;
import printing.PrintManager;
import userInterface.DeleteOrderFunction;
import userInterface.EntryForm;
import userInterface.LabelViewerImp;
import userInterface.OrderDisplay;
import userInterface.RDFInterface;
import userInterface.UserInterface;

public class Application {
	private UserInterface ui;
	
	public Application() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		
		ui = new RDFInterface();
		ui.addHomeFunction(new OrderDisplay());
		ui.addFunction(new EntryForm(), "New Order");
		ui.addFunction(new LabelViewerImp(), "View/Print Labels for selected");
		ui.addFunction(new DeleteOrderFunction(), "Delete selected orders");
		ui.addFunction(new RefreshFunction(), "Pull orders from server");
	}
	
	public void run() {		
		ui.showInterface();		
	}
}