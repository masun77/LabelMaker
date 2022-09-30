package oldFiles;

import java.util.ArrayList;

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
	public String getGTIN(String itemCode);
	public String getProdName(String itemCode);
	public String getUnit(String itemCode);
}
