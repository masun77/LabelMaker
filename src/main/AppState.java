package main;

import java.util.ArrayList;

import database.DataClient;
import localBackup.DataSaver;
import localBackup.LocalFileBackup;
import printing.LabelPrinter;
import userInterface.AppListener;
import userInterface.graphicComponents.CompanyCheckBox;
import userInterface.graphicComponents.ItemCheckBox;
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

	public static LocalFileBackup getFileBackup() {
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

	public static void setFileBackup(LocalFileBackup f) {
		singleton.setFileBackup(f);
	}

	public static void addListener(AppListener listener) {
		singleton.addListener(listener);
	}
	
	public static void addLastListener(AppListener listener) {
		singleton.addLastListener(listener);
	}

	public static void removeListener(AppListener listener) {
		singleton.removeListener(listener);
	}

	public static ArrayList<AppListener> getFunctions() {
		return singleton.getFunctions();
	}

	public static void setFunctions(ArrayList<AppListener> functions) {
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
	
	public static void setCompanyBoxArray(ArrayList<CompanyCheckBox> boxes) {
		singleton.setCompanyBoxArray(boxes);
	}
	
	public static ArrayList<CompanyCheckBox> getCompanyArray() {
		return singleton.getCompanyArray();
	}
	
	public static void setItemArray(ArrayList<ItemCheckBox> boxes) {
		singleton.setItemArray(boxes);
	}
	
	public static ArrayList<ItemCheckBox> getItemArray() {
		return singleton.getItemArray();
	}

}
