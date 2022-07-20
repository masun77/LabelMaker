package userInterface;

import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
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
	private final Dimension NAME_SIZE = new Dimension(80,30);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension TRASH_BTN_SIZE = new Dimension(20,15);
	private final Dimension NUMBER_SIZE = new Dimension(110,15);
	private final Dimension ITEM_NAME_SIZE = new Dimension(100,15);
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private ArrayList<ArrayList<Float>> displayArray = new ArrayList<ArrayList<Float>>();
	private ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();
	private ArrayList<CompanyCheckBox> companyBoxes;
	private ArrayList<ItemCheckBox> itemBoxes;

	public OrderDisplay() {
		orders = AppState.getOrders();
		
		addOrderArray();
	}
	
	private void addOrderArray() {
		companyBoxes = new ArrayList<>();
		itemBoxes = new ArrayList<>();
		addCompanyNameRow();
		addRows();
		Utilities.localVPack(mainPanel);
		mainPanel.validate();
	}
	
	private void addCompanyNameRow() {
		headerRow = new HPanel();
		ArrayList<String> companyNames = getCompanyNames();
		if (companyNames.size() > 0) {
			JLabel compLabel = new JLabel("Company:");
			Utilities.setMinMax(compLabel, NAME_SIZE);
			headerRow.add(compLabel);			
		}
		for(int n = 0; n < companyNames.size(); n++) {
			addCompanyToHeaderRow(companyNames.get(n), n);
		}
		AppState.setCompanyBoxArray(companyBoxes);
		Utilities.localHPack(headerRow);
		mainPanel.add(headerRow);
	}
	
	private void addCompanyToHeaderRow(String name, int n) {
		JLabel nameLabel = new JLabel(name); 
		Utilities.setMinMax(nameLabel, NAME_SIZE);
		CompanyCheckBox currCompany = new CompanyCheckBox(n);
		headerRow.add(currCompany);
		companyBoxes.add(currCompany);
		headerRow.add(nameLabel);
		JLabel spacer = new JLabel();
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
		int length = 12;
		String name = ord.getCompany();
		if (name.length() > length) {
			name = name.substring(0, length - 1) + "..";
		}
		return "<html>" +  name + "<br>" + ord.getShipDate().getMMDD() + "</html>";
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
			JLabel prodName = new JLabel(prodNames.get(r));
			Utilities.setMinMax(prodName, ITEM_NAME_SIZE);
			ItemCheckBox itemBox = new ItemCheckBox(r);
			rowPanel.add(itemBox);
			itemBoxes.add(itemBox);
			rowPanel.add(prodName);
			for (int orderNum = 0; orderNum < row.size(); orderNum++) {
				addItemToRow(rowPanel, r, orderNum);
			}
			Utilities.localHPack(rowPanel);
			mainPanel.add(rowPanel);
		}
		AppState.setItemArray(itemBoxes);
	}
	
	private void addItemToRow(HPanel rowPanel, int rowNum, int orderNum) {
		ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = AppState.getCheckBoxArray();
		JLabel qtyLabel = new JLabel(Float.toString(displayArray.get(rowNum).get(orderNum)));
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
	public void resetOrders() {
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
		resetOrders();
	}

	@Override
	public Container getMainContent() {
		return mainPanel;
	}
}
