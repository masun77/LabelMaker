package database;

import java.util.ArrayList;

import main.Order;

public interface DataClient {
	public ArrayList<Order> getOrders();
	public void saveOrders(ArrayList<Order> orders);
	public void addOrder(Order o);
	public void removeOrder(Order o);
	
	public void setIPAddress(String ipAddr);
	public String getServerIPAddress();
	
	public String getItemDescription(String itemCode);
	public ArrayList<String> getItemData(String itemCode);
}
