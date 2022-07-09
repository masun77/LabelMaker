package userInterface;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import labels.LabelableItem;
import main.AppState;
import main.Order;
import userInterface.graphicComponents.CompanyCheckBox;
import userInterface.graphicComponents.HPanel;
import userInterface.graphicComponents.ItemCheckBox;
import userInterface.graphicComponents.PrintCheckBox;
import userInterface.graphicComponents.VPanel;

public class OrderDisplay implements HomeFunction {
	// Application variables from Application state
	private ArrayList<Order> orders;
	
	// Display variables
	private JPanel mainPanel = new VPanel();
	private JPanel headerRow = new HPanel();
	private final Dimension NAME_SIZE = new Dimension(80,15);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension TRASH_BTN_SIZE = new Dimension(20,15);
	private final Dimension NUMBER_SIZE = new Dimension(110,15);
	private final Dimension ITEM_NAME_SIZE = new Dimension(100,15);
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private ArrayList<ArrayList<Float>> displayArray = new ArrayList<ArrayList<Float>>();
	ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();

	public OrderDisplay() {
		orders = AppState.getOrders();
		
		addOrderArray();
	}
	
	private void addOrderArray() {
		addCompanyNameRow();
		addRows();
		Utilities.localVPack(mainPanel);
		mainPanel.validate();
	}
	
	private void addCompanyNameRow() {
		headerRow = new HPanel();
		ArrayList<String> companyNames = getCompanyNames();
		if (companyNames.size() > 0) {
			Label compLabel = new Label("Company:");
			Utilities.setMinMax(compLabel, NAME_SIZE);
			headerRow.add(compLabel);			
		}
		for(int n = 0; n < companyNames.size(); n++) {
			addCompanyToHeaderRow(companyNames.get(n), n);
		}
		Utilities.localHPack(headerRow);
		mainPanel.add(headerRow);
	}
	
	private void addCompanyToHeaderRow(String name, int n) {
		Label nameLabel = new Label(name);
		Utilities.setMinMax(nameLabel, NAME_SIZE);
		headerRow.add(new CompanyCheckBox(n));
		headerRow.add(nameLabel);
		headerRow.add(new TrashButton(n));
		Label spacer = new Label();
		Utilities.setMinMax(spacer, SPACE);
		headerRow.add(spacer);
	}
		
	private ArrayList<String> getCompanyNames() {
		ArrayList<String> colNames = new ArrayList<String>();
		for (int ord = 0; ord < orders.size(); ord++) {
			colNames.add(getCompanyNameDate(orders.get(ord)));
		}
		return colNames;
	}
	
	private String getCompanyNameDate(Order ord) {
		int length = 7;
		String name = ord.getCompany();
		if (name.length() > length) {
			name = name.substring(0, length - 1) + "..";
		}
		name += ", ";
		return name + ord.getShipDate().getMMDD();
	}
	
	private void addRows() {
		getDisplayArray();
		AppState.setCheckBoxArray(checkBoxArray);
		addRowsToDisplay(0);
	}
	
	private void addRowsToDisplay(int start) {
		for (int r = start; r < displayArray.size(); r++) {
			ArrayList<Float> row = displayArray.get(r);
			HPanel rowPanel = new HPanel();
			Label prodName = new Label(prodNames.get(r));
			Utilities.setMinMax(prodName, ITEM_NAME_SIZE);
			rowPanel.add(new ItemCheckBox(r));
			rowPanel.add(prodName);
			for (int orderNum = 0; orderNum < row.size(); orderNum++) {
				addItemToRow(rowPanel, r, orderNum);
			}
			Utilities.localHPack(rowPanel);
			mainPanel.add(rowPanel);
		}
	}
	
	private void addItemToRow(HPanel rowPanel, int rowNum, int orderNum) {
		ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = AppState.getCheckBoxArray();
		Label qtyLabel = new Label(Float.toString(displayArray.get(rowNum).get(orderNum)));
		Utilities.setMinMax(qtyLabel, NUMBER_SIZE);
		rowPanel.add(checkBoxArray.get(rowNum).get(orderNum));
		rowPanel.add(qtyLabel);
	}
		
	private void getDisplayArray() {
		gtins = new ArrayList<String>();
		prodNames = new ArrayList<String>();
		displayArray = new ArrayList<ArrayList<Float>>();
		checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();
		
		for (int ord = 0; ord < orders.size(); ord++) {
			addEmptyArraysForOrderItems(orders.get(ord));
		}
		
		for (int orderIndex = 0; orderIndex < orders.size(); orderIndex++) {
			setDisplayArrayForOrder(orders.get(orderIndex), orderIndex);
		}
	}
		
	private void addEmptyArraysForOrderItems(Order order) {
		ArrayList<LabelableItem> items = order.getItems();
		for (int it = 0; it < items.size(); it++) {
			LabelableItem item = items.get(it);
			int index = gtins.indexOf(item.getGtin());
			if (index < 0) {
				gtins.add(item.getGtin());
				prodNames.add(item.getProductName());
				displayArray.add(createZeroArray(orders.size()));
				checkBoxArray.add(createPrintCheckBoxes(orders.size()));
			}
		}
	}
	
	private void setDisplayArrayForOrder(Order order, int orderIndex) {
		ArrayList<LabelableItem> items = order.getItems();
		for (int it = 0; it < items.size(); it++) {
			LabelableItem item = items.get(it);
			int index = gtins.indexOf(item.getGtin());
			displayArray.get(index).set(orderIndex, item.getQuantity());
			
			if (item.getQuantity() > 0) {
				PrintCheckBox box = new PrintCheckBox(item);
				checkBoxArray.get(index).set(orderIndex, box);
			}
		}
	}
	
	private ArrayList<Float> createZeroArray(int numZeros) {
		ArrayList<Float> arr = new ArrayList<Float>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(0.0f);
		}
		return arr;
	}
	
	private ArrayList<PrintCheckBox> createPrintCheckBoxes(int numZeros) {
		ArrayList<PrintCheckBox> arr = new ArrayList<>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(new PrintCheckBox());
		}
		return arr;
	}

	private class TrashButton extends JButton {
		private int index = 0;
		
		public TrashButton(int indx) {
			index = indx;
			ImageIcon imic = new ImageIcon("resources/trashBin.png");
			Image img = imic.getImage() ;  
			Image newimg = img.getScaledInstance( (int) TRASH_BTN_SIZE.getWidth(), (int) TRASH_BTN_SIZE.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
			imic = new ImageIcon( newimg );
			setIcon(imic);
			Utilities.setMinMax(this, TRASH_BTN_SIZE);
			addActionListener(new TrashButtonListener(index));
		}
	}
	
	private class TrashButtonListener implements ActionListener {
		private int index = 0;
		
		public TrashButtonListener(int i) {
			index = i;
		}		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			orders.remove(index);
			AppState.setOrders(orders);
			AppState.getDataClient().saveOrders(orders);
		}
	}
	
	private void addColumnValuesToDisplay(Order o, int end) {
		for (int i = 1; i  < end + 1; i++) {
			HPanel row = (HPanel) mainPanel.getComponent(i);
			addItemToRow(row, i - 1, orders.size() -1);
			Utilities.localHPack(row);
		}
	}

	private void addEndValuesForOrderItems(int end) {
		for (int r = 0; r < end; r++) {
			displayArray.get(r).add(0f);
			checkBoxArray.get(r).add(new PrintCheckBox());
		}
	}
	
	@Override
	public void resetOrders(ArrayList<Order> ords) {
		mainPanel.removeAll();
		orders = AppState.getOrders();
		addOrderArray();
	}

	@Override
	public void addOrder(Order o) {
		int oldNumRows = displayArray.size();
		orders = AppState.getOrders();
		
		addCompanyToHeaderRow(getCompanyNameDate(o), orders.size()-1);
		Utilities.localHPack(headerRow);
		
		addEmptyArraysForOrderItems(o);
		addEndValuesForOrderItems(oldNumRows);
		setDisplayArrayForOrder(o, orders.size()-1);
		AppState.setCheckBoxArray(checkBoxArray);
		
		addRowsToDisplay(oldNumRows);
		addColumnValuesToDisplay(o, oldNumRows);
		Utilities.localVPack(mainPanel);
	}

	@Override
	public void removeOrder(Order o) {
		// do nothing - currently done only by the display itself...
	}

	@Override
	public Container getMainContent() {
		return mainPanel;
	}
}
