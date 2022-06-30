package main;

import java.util.ArrayList;

import export.DataClient;
import export.FileBackup;
import printing.LabelPrinter;
import userInterface.AppFunction;
import userInterface.PrintCheckBox;

public interface ApplicationState {

	public ArrayList<Order> getOrders();
	public DataClient getDataClient();
	public LabelPrinter getPrinter();
	public FileBackup getFileBackup();
	public ArrayList<ArrayList<PrintCheckBox>> getCheckBoxArray();
	public void setOrders(ArrayList<Order> orders);
	public void setDataClient(DataClient dc);
	public void setPrinter(LabelPrinter lp);
	public void setFileBackup(FileBackup fb);
	public void setCheckBoxArray(ArrayList<ArrayList<PrintCheckBox>> pcb);
	public void addListener(AppFunction listener);
	public void removeListener(AppFunction listener);
	public void notifyListeners();
}
