package uiDisplay;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import uiLogic.SideFunction;
import uiSubcomponents.CompanyCheckBox;

public class EditOrderFunction implements SideFunction {

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
	public void executeFunction() {
		ArrayList<Boolean> companyBoxes = AppState.getCompanySelectedArray();
		Order currOrder = null;
		for (int c = 0; c < companyBoxes.size(); c++) {
			if (companyBoxes.get(c)) {
				currOrder = AppState.getOrders().get(c);
				c = companyBoxes.size() + 1;
			}
		}
		if (currOrder != null) {
			EntryForm ef = new EntryForm();
			ef.setEditingOrder(true, currOrder);
			ef.executeFunction();
		}
	}

}
