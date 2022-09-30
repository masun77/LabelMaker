package oldFiles;

import java.util.ArrayList;

public interface DataClient {
	public ArrayList<Order> getOrders();
	public void saveOrders(ArrayList<Order> orders);
	
	public void setIPAddress(String ipAddr);
	public String getServerIPAddress();
	
	public String getItemDescription(String itemCode);
	public ArrayList<String> getItemData(String itemCode);
}
