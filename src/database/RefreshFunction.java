package database;

import java.util.ArrayList;

import main.AppListenerMessage;
import main.AppState;
import main.Order;
import uiLogic.SideFunction;

public class RefreshFunction implements SideFunction {
	@Override
	public void executeFunction() {
		ArrayList<Order> orders = AppState.getDataClient().getOrders();
		if (orders.size() > 0) {
			AppState.setOrders(orders);
		}
	}

}
