package database;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import userInterface.SideFunction;

public class RefreshFunction implements SideFunction {

	@Override
	public void resetOrders(ArrayList<Order> ords) {
		AppState.getDataClient().saveOrders(ords);
	}

	@Override
	public void addOrder(Order o) {
		AppState.getDataClient().addOrder(o);
	}

	@Override
	public void removeOrder(Order o) {
		AppState.getDataClient().removeOrder(o);
	}

	@Override
	public void showFunction() {
		ArrayList<Order> orders = AppState.getDataClient().getOrders();
		if (orders.size() > 0) {
			AppState.setOrders(orders);
		}
	}

}
