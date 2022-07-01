package export;

import java.util.ArrayList;

import main.Order;
import printing.PrinterDescription;

public interface FileBackup {
	public void saveOrders(ArrayList<Order> orders);
	public ArrayList<Order> readSavedOrders();
	public String getItemDescription(String itemCode);
	public ArrayList<String> getItemData(String itemCode);
	public PrinterDescription getPrinterDescription();
	public String getServerIPAddress();
	public int getServerPort();
}
