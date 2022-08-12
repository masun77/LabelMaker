package uiLogic;

import java.util.ArrayList;

import main.AppState;
import static main.AppListenerMessage.*;

public class DeselectAllFunction implements SideFunction {
	@Override
	public void executeFunction() {
		ArrayList<Boolean> companyBoxes = AppState.getCompanySelectedArray();
		ArrayList<Boolean> itemBoxes = AppState.getItemSelectedArray();
		ArrayList<ArrayList<Boolean>> printBoxArray = AppState.getIndivItemSelectedArray();
		for (int c = 0; c < companyBoxes.size(); c++) {
			companyBoxes.set(c, false);
		}
		for (int c = 0; c < itemBoxes.size(); c++) {
			itemBoxes.set(c, false);
		}
		for (int r = 0; r < printBoxArray.size(); r++) {
			ArrayList<Boolean> row = printBoxArray.get(r);
			for (int c = 0; c < row.size(); c++) {
				row.set(c, false);
			}
		}
		AppState.sendMessage(UPDATE_CHECKBOXES);
	}
}
