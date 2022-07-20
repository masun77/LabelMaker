package userInterface;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import userInterface.graphicComponents.CompanyCheckBox;

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
	public void showFunction() {
		ArrayList<CompanyCheckBox> companyBoxes = AppState.getCompanyArray();
		Order currOrder = null;
		for (int c = 0; c < companyBoxes.size(); c++) {
			if (companyBoxes.get(c).isSelected()) {
				currOrder = AppState.getOrders().get(c);
				c = companyBoxes.size() + 1;
			}
		}
		if (currOrder != null) {
			EntryForm ef = new EntryForm();
			ef.setEditingOrder(true, currOrder);
			ef.showFunction();
		}
	}

}
