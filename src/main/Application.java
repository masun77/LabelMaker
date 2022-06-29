package main;

import java.util.ArrayList;

import export.DataClient;
import export.DataSaver;
import export.FileBackup;
import export.SocketClient;
import printing.LabelPrinter;
import printing.PrintManager;
import userInterface.RDFInterface;
import userInterface.UserInterface;

public class Application {
	private UserInterface ui;
	private ApplicationState appState;
	
	public Application() {
		appState = new AppState(new ArrayList<Order>(), new SocketClient(), new DataSaver(), new PrintManager());
		ui = new RDFInterface(appState);
	}
	
	public void run() {		
		ui.showInterface();		
	}
}
