package main;

import java.util.ArrayList;

import export.DataClient;
import export.DataSaver;
import export.FileBackup;
import export.SocketClient;
import printing.LabelPrinter;
import printing.PrintManager;

public class AppState implements ApplicationState {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private DataClient client;
	private FileBackup fb;
	private LabelPrinter lp;
	
	public AppState(ArrayList<Order> ords, DataClient c, FileBackup f, LabelPrinter printer) {
		orders = ords;
		client = c; 
		fb = f; 
		lp = printer;
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
	public void setOrders(ArrayList<Order> orders) {
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

}
