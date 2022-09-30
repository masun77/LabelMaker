package oldFiles;

import java.util.ArrayList;

public class RefreshFunction implements SideFunction {
	@Override
	public void executeFunction() {
		ArrayList<Order> orders = AppState.getDataClient().getOrders();
		if (orders.size() > 0) {
			AppState.setOrders(orders);
		}
	}

}
