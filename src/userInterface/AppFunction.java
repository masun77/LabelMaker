package userInterface;

import java.util.ArrayList;
import java.util.List;

import main.Order;

public interface AppFunction {
	public void resetOrders(ArrayList<Order> ords);
	public void addOrder(Order o);
	public void removeOrder(Order o);
}
