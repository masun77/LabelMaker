package main;

import java.util.ArrayList;

import export.DataClient;
import export.FileBackup;
import printing.LabelPrinter;
import userInterface.AppFunction;
import userInterface.UserInterface;
import userInterface.graphicComponents.PrintCheckBox;

public class SingletonState {
	private ArrayList<AppFunction> functions = new ArrayList<AppFunction>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private DataClient dataClient;
	private FileBackup fileBackup;
	private LabelPrinter printer;
	private ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();

	public SingletonState(ArrayList<Order> ords, FileBackup f, LabelPrinter lp, DataClient dc) {
		orders = ords;
		fileBackup = f; 
		printer = lp;
		dataClient = dc;
	}

	public ArrayList<AppFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(ArrayList<AppFunction> functions) {
		this.functions = functions;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}
	
	public void addOrder(Order o) {
		orders.add(o);//		
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).addOrder(o);
		}
	}
	
	public void removeOrder(Order o) {
		orders.remove(o);
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).removeOrder(o);
		}
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
		fileBackup.saveOrders(orders);
	}

	public DataClient getDataClient() {
		return dataClient;
	}

	public void setDataClient(DataClient dataClient) {
		this.dataClient = dataClient;
	}

	public FileBackup getFileBackup() {
		return fileBackup;
	}

	public void setFileBackup(FileBackup fileBackup) {
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

	public void addListener(AppFunction listener) {
		functions.add(listener);
	}
	
	public void removeListener(AppFunction listener) {
		functions.remove(listener);
	}

	public void notifyListeners() {
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).resetOrders(orders);
		}
	}
}
