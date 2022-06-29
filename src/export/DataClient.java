package export;

import java.util.ArrayList;

import main.Order;

public interface DataClient {

	public ArrayList<Order> getOrders();
	public void sendOrders(ArrayList<Order> orders);
	public void setIPAddress(String ipAddr);
	public String getIPAddress();
}
