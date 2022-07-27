package database;

import java.util.ArrayList;

import main.Order;

public interface DataImporter {
	public ArrayList<Order> readInvoices();
	public void setResourcePath(String filePath);
}
