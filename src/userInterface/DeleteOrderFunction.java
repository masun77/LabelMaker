package userInterface;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import userInterface.graphicComponents.CompanyCheckBox;

public class DeleteOrderFunction implements SideFunction {

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
		ArrayList<CompanyCheckBox> companyArray = AppState.getCompanyArray();
		ArrayList<Order> orders = AppState.getOrders();
		for (int c = companyArray.size() - 1; c >= 0; c--) {
			if (companyArray.get(c).isSelected()) {
				orders.remove(c);
			}
		}
		AppState.setOrders(orders);
	}

}
