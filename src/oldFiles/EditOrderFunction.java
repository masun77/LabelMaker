package oldFiles;

import java.util.ArrayList;

public class EditOrderFunction implements SideFunction {
	@Override
	public void executeFunction() {
		ArrayList<Boolean> companyBoxes = AppState.getCompanySelectedArray();
		Order currOrder = null;
		for (int c = 0; c < companyBoxes.size(); c++) {
			if (companyBoxes.get(c)) {
				currOrder = AppState.getFilteredOrders().get(c);
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
