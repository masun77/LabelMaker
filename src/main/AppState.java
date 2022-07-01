package main;

import java.util.ArrayList;

import export.DataClient;
import export.DataSaver;
import export.FileBackup;
import export.SocketClient;
import printing.LabelPrinter;
import printing.PrintManager;
import userInterface.AppFunction;
import userInterface.PrintCheckBox;

public class AppState implements ApplicationState {
	private ArrayList<AppFunction> functions = new ArrayList<AppFunction>();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private DataClient client;
	private FileBackup fb;
	private LabelPrinter lp = null;
	private ArrayList<ArrayList<PrintCheckBox>> checkBoxes = new ArrayList<ArrayList<PrintCheckBox>>();
	
	public AppState(ArrayList<Order> ords, DataClient c, FileBackup f) {
		orders = ords;
		client = c; 
		fb = f; 
	}

	@Override
	public ArrayList<Order> getOrders() {
		return orders;
	}

	@Override
	public DataClient getDataClient() {
		return client;
	}

	@Override
	public LabelPrinter getPrinter() {
		return lp;
	}

	@Override
	public FileBackup getFileBackup() {
		return fb;
	}

	@Override
	public void setOrders(ArrayList<Order> ords) {
		orders = ords;
	}

	@Override
	public void setDataClient(DataClient dc) {
		client = dc;
	}

	@Override
	public void setPrinter(LabelPrinter printer) {
		lp = printer;
	}

	@Override
	public void setFileBackup(FileBackup f) {
		fb = f;
	}

	@Override
	public void addListener(AppFunction listener) {
		functions.add(listener);
	}

	@Override
	public void removeListener(AppFunction listener) {
		functions.remove(listener);
	}

	@Override
	public void notifyListeners() {
		for (int f = 0; f < functions.size(); f++) {
			functions.get(f).refresh();
		}
	}

	@Override
	public ArrayList<ArrayList<PrintCheckBox>> getCheckBoxArray() {
		return checkBoxes;
	}

	@Override
	public void setCheckBoxArray(ArrayList<ArrayList<PrintCheckBox>> pcb) {
		checkBoxes = pcb;
	}

}
