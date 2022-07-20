package database;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import userInterface.SideFunction;

public class RefreshFunction implements SideFunction {

	@Override
	public void resetOrders() {
		// do nothing
	}

	@Override
	public void addOrder(Order o) {
		// do nothing
	}

	@Override
	public void removeOrder(Order o) {
		// do nothing
	}

	@Override
	public void showFunction() {
		ArrayList<Order> orders = AppState.getDataClient().getOrders();
		if (orders.size() > 0) {
			AppState.setOrders(orders);
		}
	}

}
