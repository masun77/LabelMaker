package main;

import java.util.ArrayList;

import database.DataClient;
import localBackup.LocalFileBackup;
import printing.LabelPrinter;
import userInterface.AppListener;
import userInterface.UserInterface;
import userInterface.graphicComponents.CompanyCheckBox;
import userInterface.graphicComponents.ItemCheckBox;
import userInterface.graphicComponents.PrintCheckBox;

public class SingletonState {
	private AppListener lastListener;
	private ArrayList<AppListener> functions = new ArrayList<AppListener>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private DataClient dataClient;
	private LocalFileBackup fileBackup;
	private LabelPrinter printer;
	private ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();
	private ArrayList<CompanyCheckBox> companyBoxes = new ArrayList<>();
	private ArrayList<ItemCheckBox> itemBoxes = new ArrayList<>();

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

	public ArrayList<ArrayList<PrintCheckBox>> getCheckBoxArray() {
		return checkBoxArray;
	}

	public void setCheckBoxArray(ArrayList<ArrayList<PrintCheckBox>> checkBoxArray) {
		this.checkBoxArray = checkBoxArray;
	}
	
	public void setCompanyBoxArray(ArrayList<CompanyCheckBox> boxes) {
		companyBoxes = boxes;
	}

	public ArrayList<CompanyCheckBox> getCompanyArray() {
		return companyBoxes;
	}
	
	public void setItemArray(ArrayList<ItemCheckBox> boxes) {
		itemBoxes = boxes;
	}
	
	public ArrayList<ItemCheckBox> getItemArray() {
		return itemBoxes;
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
