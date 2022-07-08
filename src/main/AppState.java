package main;

import java.util.ArrayList;

import export.DataClient;
import export.DataSaver;
import export.FileBackup;
import export.SocketClient;
import printing.LabelPrinter;
import printing.PrintManager;
import userInterface.AppFunction;
import userInterface.UserInterface;
import userInterface.graphicComponents.PrintCheckBox;

public class AppState {
	private static SingletonState singleton;
	
	public static void initializeAppState(ArrayList<Order> orders, DataSaver ds, LabelPrinter lp, DataClient dc) {
		singleton = new SingletonState(orders, ds, lp, dc);
	}
	
	private AppState() {	}

	public static ArrayList<Order> getOrders() {
		return singleton.getOrders();
	}

	public static DataClient getDataClient() {
		return singleton.getDataClient();
	}

	public static LabelPrinter getPrinter() {
		return singleton.getPrinter();
	}

	public static FileBackup getFileBackup() {
		return singleton.getFileBackup();
	}

	public static void setOrders(ArrayList<Order> ords) {
		singleton.setOrders(ords);
	}
	
	public static void addOrder(Order o) {
		singleton.addOrder(o);
	}
	
	public static void removeOrder(Order o) {
		singleton.removeOrder(o);
	}

	public static void setDataClient(DataClient dc) {
		singleton.setDataClient(dc);
	}

	public static void setPrinter(LabelPrinter printer) {
		singleton.setPrinter(printer);
	}

	public static void setFileBackup(FileBackup f) {
		singleton.setFileBackup(f);
	}

	public static void addListener(AppFunction listener) {
		singleton.addListener(listener);
	}

	public static void removeListener(AppFunction listener) {
		singleton.removeListener(listener);
	}

	public static ArrayList<AppFunction> getFunctions() {
		return singleton.getFunctions();
	}

	public static void setFunctions(ArrayList<AppFunction> functions) {
		singleton.setFunctions(functions);
	}

	public static void notifyListeners() {
		singleton.notifyListeners();
	}

	public static ArrayList<ArrayList<PrintCheckBox>> getCheckBoxArray() {
		return singleton.getCheckBoxArray();
	}

	public static void setCheckBoxArray(ArrayList<ArrayList<PrintCheckBox>> pcb) {
		singleton.setCheckBoxArray(pcb);
	}

}
