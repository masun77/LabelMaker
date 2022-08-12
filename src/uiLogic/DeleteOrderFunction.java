package uiLogic;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import uiSubcomponents.CompanyCheckBox;

public class DeleteOrderFunction implements SideFunction {
	@Override
	public void executeFunction() {
		ArrayList<Boolean> companyArray = AppState.getCompanySelectedArray();
		ArrayList<Order> orders = AppState.getOrders();
		for (int c = companyArray.size() - 1; c >= 0; c--) {
			if (companyArray.get(c)) {
				orders.remove(c);
			}
		}
		AppState.setOrders(orders);
	}

}
