package main;

import java.util.ArrayList;

import database.DataClient;
import labels.LabelableItem;
import localBackup.DataSaver;
import localBackup.LocalFileBackup;
import printing.LabelPrinter;

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

	public static void removeListener(AppListener listener) {
		singleton.removeListener(listener);
	}

	public static ArrayList<AppListener> getFunctions() {
		return singleton.getFunctions();
	}

	public static void setFunctions(ArrayList<AppListener> functions) {
		singleton.setFunctions(functions);
	}

	public static void sendMessage(AppListenerMessage m) {
		singleton.sendMessage(m);
	}

	public static ArrayList<ArrayList<Boolean>> getIndivItemSelectedArray() {
		return singleton.getIndivItemSelectedArray();
	}

	public static void setIndivItemSelectedArray(ArrayList<ArrayList<Boolean>> pcb) {
		singleton.setIndivItemSelectedArray(pcb);
	}
	
	public static void setCompanySelectedArray(ArrayList<Boolean> boxes) {
		singleton.setCompanySelectedArray(boxes);
	}
	
	public static ArrayList<Boolean> getCompanySelectedArray() {
		return singleton.getCompanySelectedArray();
	}
	
	public static void setItemSelectedArray(ArrayList<Boolean> boxes) {
		singleton.setItemSelectedArray(boxes);
	}
	
	public static ArrayList<Boolean> getItemSelectedArray() {
		return singleton.getItemSelectedArray();
	}
	
	public static ArrayList<ArrayList<LabelableItem>> getItemArray() {
		return singleton.getItemArray();
	}
	
	public static void setItemArray(ArrayList<ArrayList<LabelableItem>> items) {
		singleton.setItemArray(items);
	}
	
	public static void setLastListener(AppListener l) {
		singleton.setLastListener(l);
	}
	
	public static void addOrders(ArrayList<Order> ords) {
		singleton.addOrders(ords);
	}
	
	public static ArrayList<Order> getFilteredOrders() {
		return singleton.getFilteredOrders();
	}
	
	public static void setFilteredOrders(ArrayList<Order> ords) {
		singleton.setFilteredOrders(ords);
	}
}
