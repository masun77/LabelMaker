package uiDisplay;

import java.util.ArrayList;

import labels.LabelableItem;
import printing.LabelPrinter;

public interface LabelDisplayInterface {
	public void clearOldLabels();
	public void setPrinter(LabelPrinter lp);
	public void showNewLabels(ArrayList<LabelableItem> items);
}