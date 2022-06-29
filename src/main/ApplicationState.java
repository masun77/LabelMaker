package main;

import java.util.ArrayList;

import export.DataClient;
import export.FileBackup;
import printing.LabelPrinter;

public interface ApplicationState {

	public ArrayList<Order> getOrders();
	public DataClient getDataClient();
	public LabelPrinter getPrinter();
	public FileBackup getFileBackup();
	public void setOrders(ArrayList<Order> orders);
	public void setDataClient(DataClient dc);
	public void setPrinter(LabelPrinter lp);
	public void setFileBackup(FileBackup fb);
}
