package uiLogic;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import uiSubcomponents.CompanyCheckBox;
import uiSubcomponents.PrintCheckBox;

public class SelectAllFunction implements SideFunction {

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
		ArrayList<Boolean> itemBoxes = AppState.getItemSelectedArray();
		ArrayList<ArrayList<Boolean>> indivItemBoxes = AppState.getIndivItemSelectedArray();
		for (int c = 0; c < companyBoxes.size(); c++) {
			companyBoxes.set(c, true);
		}
		for (int c = 0; c < itemBoxes.size(); c++) {
			itemBoxes.set(c, true);
		}
		for (int r = 0; r < indivItemBoxes.size(); r++) {
			ArrayList<Boolean> row = indivItemBoxes.get(r);
			for (int c = 0; c < row.size(); c++) {
				row.set(c, true);
			}
		}
		AppState.notifyListeners();
	}
}
