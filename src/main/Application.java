package main;
import java.util.ArrayList;

import database.RefreshFunction;
import database.SocketClient;
import localBackup.DataSaver;
import printing.PrintManager;
import userInterface.DeleteOrderFunction;
import userInterface.DeselectAllFunction;
import userInterface.EntryForm;
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
		ui.addFunction(new EntryForm(), "New Order");
		ui.addFunction(new LabelViewerImp(), "View/Print Labels for selected");
		ui.addFunction(new DeleteOrderFunction(), "Delete selected orders");
		ui.addFunction(new RefreshFunction(), "Pull orders from server");
		ui.addFunction(new SelectAllFunction(), "Select All Orders");
		ui.addFunction(new DeselectAllFunction(), "Deselect All Orders");
	}
	
	public void run() {		
		ui.showInterface();		
	}
}