package userInterface;

import java.util.ArrayList;

import main.AppState;
import main.Order;
import userInterface.graphicComponents.CompanyCheckBox;
import userInterface.graphicComponents.ItemCheckBox;
import userInterface.graphicComponents.PrintCheckBox;

public class DeselectAllFunction implements SideFunction {

	@Override
	public void resetOrders(ArrayList<Order> ords) {
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
		ArrayList<ItemCheckBox> itemBoxes = AppState.getItemArray();
		ArrayList<ArrayList<PrintCheckBox>> printBoxArray = AppState.getCheckBoxArray();
		for (int c = 0; c < companyBoxes.size(); c++) {
			CompanyCheckBox box = companyBoxes.get(c);
			if (box.isEnabled()) {
				box.setSelected(false);
			}
		}
		for (int c = 0; c < itemBoxes.size(); c++) {
			ItemCheckBox box = itemBoxes.get(c);
			if (box.isEnabled()) {
				box.setSelected(false);
			}
		}
		for (int r = 0; r < printBoxArray.size(); r++) {
			ArrayList<PrintCheckBox> row = printBoxArray.get(r);
			for (int c = 0; c < row.size(); c++) {
				PrintCheckBox box = row.get(c);
				if (box.isEnabled()) {
					box.setSelected(false);
				}
			}
		}
	}
}
