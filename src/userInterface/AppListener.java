package userInterface;

import main.Order;

public interface AppListener {
	public void resetOrders();
	public void addOrder(Order o);
	public void removeOrder(Order o);
}
