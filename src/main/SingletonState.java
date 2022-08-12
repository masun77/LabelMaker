package main;

import java.util.ArrayList;

import database.DataClient;
import labels.LabelableItem;
import localBackup.LocalFileBackup;
import printing.LabelPrinter;
import uiLogic.AppListener;
import uiLogic.UserInterface;
import uiSubcomponents.CompanyCheckBox;
import uiSubcomponents.ItemCheckBox;
import uiSubcomponents.PrintCheckBox;

public class SingletonState {
	private AppListener lastListener;
	private ArrayList<AppListener> functions = new ArrayList<AppListener>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private DataClient dataClient;
	private LocalFileBackup fileBackup;
	private LabelPrinter printer;
	private ArrayList<ArrayList<Boolean>> indivItemSelectedBooleanArray = new ArrayList<>();
	private ArrayList<Boolean> companySelectedBooleanArray = new ArrayList<>();
	private ArrayList<Boolean> itemSelectedBooleanArray = new ArrayList<>();
	private ArrayList<ArrayList<LabelableItem>> itemArray = new ArrayList<>();

	public SingletonState(ArrayList<Order> ords, LocalFileBackup f, LabelPrinter lp, DataClient dc) {
		orders = ords;
		fileBackup = f; 
		printer = lp;
		dataClient = dc;
	}

	public ArrayList<AppListener> getFunctions() {
		return functions;
	}
	
	public void addLastListener(AppListener list) {
		lastListener = list;
	}

	public void setFunctions(ArrayList<AppListener> functions) {
		this.functions = functions;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(Order o) {
		orders.add(o);	
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).addOrder(o);
		}
		if (lastListener != null) {
			lastListener.addOrder(o);
		}
		fileBackup.saveOrders(orders);
		dataClient.saveOrders(orders);
	}
	
	public void removeOrder(Order o) {
		orders.remove(o);
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).removeOrder(o);
		}
		if (lastListener != null) {
			lastListener.removeOrder(o);
		}
		fileBackup.saveOrders(orders);
		dataClient.saveOrders(orders);
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
		fileBackup.saveOrders(orders);
		notifyListeners();
		dataClient.saveOrders(orders);
	}
	
	public void notifyLastListener() {
		lastListener.resetOrders();
	}

	public DataClient getDataClient() {
		return dataClient;
	}

	public void setDataClient(DataClient dataClient) {
		this.dataClient = dataClient;
	}

	public LocalFileBackup getFileBackup() {
		return fileBackup;
	}

	public void setFileBackup(LocalFileBackup fileBackup) {
		this.fileBackup = fileBackup;
	}

	public LabelPrinter getPrinter() {
		return printer;
	}

	public void setPrinter(LabelPrinter printer) {
		this.printer = printer;
	}

	public ArrayList<ArrayList<Boolean>> getIndivItemSelectedArray() {
		return indivItemSelectedBooleanArray;
	}
	
	public void setItemArray(ArrayList<ArrayList<LabelableItem>> itemArray) {
		this.itemArray = itemArray;
	}

	public ArrayList<ArrayList<LabelableItem>> getItemArray() {
		return itemArray;
	}

	public void setIndivItemSelectedArray(ArrayList<ArrayList<Boolean>> checkBoxArray) {
		this.indivItemSelectedBooleanArray = checkBoxArray;
	}
	
	public void setCompanySelectedArray(ArrayList<Boolean> boxes) {
		companySelectedBooleanArray = boxes;
	}

	public ArrayList<Boolean> getCompanySelectedArray() {
		return companySelectedBooleanArray;
	}
	
	public void setItemSelectedArray(ArrayList<Boolean> boxes) {
		itemSelectedBooleanArray = boxes;
	}
	
	public ArrayList<Boolean> getItemSelectedArray() {
		return itemSelectedBooleanArray;
	}
	
	public void addListener(AppListener listener) {
		functions.add(listener);
	}
	
	public void removeListener(AppListener listener) {
		functions.remove(listener);
	}

	public void notifyListeners() {
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).resetOrders();
		}
		if (lastListener != null) {
			lastListener.resetOrders();
		}
	}
}
