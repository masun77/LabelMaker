package oldFiles;

import java.util.ArrayList;

public interface LabelDisplayInterface {
	public void clearOldLabels();
	public void setPrinter(LabelPrinter lp);
	public void showNewLabels(ArrayList<LabelableItem> items);
}
