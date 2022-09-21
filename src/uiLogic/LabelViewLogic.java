package uiLogic;

import java.util.ArrayList;

import main.AppState;
import main.LabelableItem;
import uiDisplay.LabelDisplayInterface;
import uiDisplay.LabelViewDisplay;

public class LabelViewLogic implements SideFunction {
	private ArrayList<LabelableItem> items;
	private LabelDisplayInterface lvd = new LabelViewDisplay();
	
	public LabelViewLogic() {
		lvd.setPrinter(AppState.getPrinter());
	}

	@Override
	public void executeFunction() {
		lvd.clearOldLabels();
		items = getCheckedItems();
		lvd.showNewLabels(items);		
	}
	
	private ArrayList<LabelableItem> getCheckedItems() {
		ArrayList<ArrayList<Boolean>> checkBoxArray = AppState.getIndivItemSelectedArray();
		ArrayList<ArrayList<LabelableItem>> appItemArray = AppState.getItemArray();
		ArrayList<LabelableItem> items = new ArrayList<LabelableItem>();

		if (checkBoxArray.size() > 0) {
			int cols = checkBoxArray.get(0).size();
			for (int col = 0; col < checkBoxArray.get(0).size(); col++) {
				for (int row = 0; row < checkBoxArray.size(); row++) {
					if (checkBoxArray.get(row).get(col)) {
						LabelableItem item = appItemArray.get(row).get(col);
						if (item != null) {
							items.add(item);
						}
					}
				}
			}		
		}
		return items;
	}
}
