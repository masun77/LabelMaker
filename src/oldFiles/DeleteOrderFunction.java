package oldFiles;

import java.util.ArrayList;

public class DeleteOrderFunction implements SideFunction {
	@Override
	public void executeFunction() {
		ArrayList<Boolean> companyArray = AppState.getCompanySelectedArray();
		ArrayList<Order> orders = AppState.getOrders();
		ArrayList<Order> filteredOrders = AppState.getFilteredOrders();
		for (int c = companyArray.size() - 1; c >= 0; c--) {
			if (companyArray.get(c)) {
				orders.remove(filteredOrders.get(c));
			}
		}
		AppState.setOrders(orders);
	}

}