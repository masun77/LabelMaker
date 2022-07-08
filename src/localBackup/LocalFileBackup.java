package localBackup;

import java.util.ArrayList;

import main.Order;
import printing.PrinterDescription;

public interface LocalFileBackup {
	public PrinterDescription getPrinterDescription();
	
	public String getServerIPAddress();
	public int getServerPort();
	public void setIPAddress(String ipAddr);
	
	public void saveOrders(ArrayList<Order> orders);
	public ArrayList<Order> getOrders();
	
	public String getItemDescription(String itemCode);
	public ArrayList<String> getItemData(String itemCode);
}
