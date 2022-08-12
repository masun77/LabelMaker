package uiDisplay;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import labels.Date;
import labels.DateImp;
import labels.LabelableItem;
import main.AppListenerMessage;
import main.AppState;
import main.Order;
import uiLogic.HomeFunction;
import uiSubcomponents.CompanyCheckBox;
import uiSubcomponents.HPanel;
import uiSubcomponents.ItemCheckBox;
import uiSubcomponents.PrintCheckBox;
import uiSubcomponents.VPanel;

public class OrderDisplay implements HomeFunction {
	// Application variables from Application state
	private ArrayList<Order> orders;
	private ArrayList<Order> filteredOrders = new ArrayList<>();
	
	// Display variables
	private JPanel wholePanel = new VPanel();
	private JPanel displayPanel = new HPanel();
	private JPanel infoPanel = new VPanel();
	private JPanel headerRow = new HPanel();
	private JPanel itemColumn = new VPanel();
	private JTextField startField = new JTextField();
	private JTextField endField = new JTextField();
	private final Dimension NAME_SIZE = new Dimension(80,50);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension NUMBER_SIZE = new Dimension(90,30);
	private final Dimension ITEM_NAME_SIZE = new Dimension(150,30);
	private final Dimension HEADER_SIZE = new Dimension(165,50);
	private final Dimension FILTER_SIZE = new Dimension(390,30);
	private final Dimension DATE_SIZE = new Dimension(50,20);
	
	// Helper variables
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private ArrayList<String> unitCodes = new ArrayList<String>();
	private ArrayList<ArrayList<Float>> displayArray = new ArrayList<ArrayList<Float>>();
	private ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();
	private ArrayList<CompanyCheckBox> companyBoxes;
	private ArrayList<ItemCheckBox> itemBoxes;
	private ArrayList<ArrayList<LabelableItem>> itemArray = new ArrayList<>();
	private Date startDate;
	private Date endDate;

	public OrderDisplay() {
		orders = AppState.getOrders();
		
		Calendar cal = Calendar.getInstance();
		
		// todo startDate = DateImp.parseDate(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		//endDate = DateImp.parseDate(startDate.getDateMMDDYYYY());
		//endDate.addDays(2);
		startDate = new DateImp(1,1,2022);
		endDate = new DateImp(12,1,2022);
		
		addOrderArray();
	}
	
	private void addOrderArray() {
		addDateFilter();
		filterOrders();
		
		companyBoxes = new ArrayList<>();
		itemBoxes = new ArrayList<>();
		addCompanyNameRow();
		addRows();
		AppState.setItemArray(itemArray);
		
		itemColumn.add(Box.createVerticalGlue());
		Utilities.localVPack(itemColumn);
		Utilities.localVPack(infoPanel);
		displayPanel.add(itemColumn);
		JScrollPane scrollbar = new JScrollPane(infoPanel);
		displayPanel.add(scrollbar);		
		Utilities.localHPack(displayPanel);
		wholePanel.add(displayPanel);
		Utilities.localVPack(wholePanel);
		wholePanel.validate();
	}
	
	private void addDateFilter() {
		HPanel filterRow = new HPanel();
		Utilities.setMinMax(filterRow, FILTER_SIZE);
		startField.setSize(DATE_SIZE);
		startField.setText(startDate.getDateMMDDYYYY());
		startField.addFocusListener(new DateFilter());
		endField.setSize(DATE_SIZE);
		endField.setText(endDate.getDateMMDDYYYY());
		endField.addFocusListener(new DateFilter());
		
		filterRow.add(new JLabel("From: "));
		filterRow.add(startField);
		filterRow.add(new JLabel("To: "));
		filterRow.add(endField);
		
		wholePanel.add(filterRow);
	}
	
	private void filterOrders() {
		for (Order o: orders) {
			Date packDate = o.getItems().get(0).getPackDate();
			if (!packDate.dateEarlierThan(startDate) && !packDate.dateLaterThan(endDate)) {
				filteredOrders.add(o);
			}
		}
	}
	
	private class DateFilter implements FocusListener {			
		@Override
		public void focusGained(FocusEvent e) {
			// do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			Date newStart = DateImp.parseDate(startField.getText());
			Date newEnd = DateImp.parseDate(endField.getText());
			if (!newStart.dateEquals(startDate) || !newEnd.dateEquals(endDate)) {
				startDate = newStart;
				endDate = newEnd;
				resetOrders();
			}
		}
	}
	
	private void addCompanyNameRow() {
		headerRow = new HPanel();
		ArrayList<String> companyNames = getCompanyNames();
		
		for(int n = 0; n < companyNames.size(); n++) {
			addCompanyToHeaderRow(companyNames.get(n), n);
		}
		AppState.setCompanySelectedArray(checksToBooleanArray(companyBoxes));
		Utilities.localHPack(headerRow);
		infoPanel.add(headerRow);
	}
	
	private ArrayList<Boolean> checksToBooleanArray(ArrayList<? extends JCheckBox> boxes) {
		ArrayList<Boolean> booleanArr = new ArrayList<>();
		for (int b = 0; b < boxes.size(); b++) {
			booleanArr.add(boxes.get(b).isSelected());
		}
		return booleanArr;		
	}
	
	private ArrayList<ArrayList<Boolean>> checksToDoubleBooleanArray() {
		ArrayList<ArrayList<Boolean>> booleanArr = new ArrayList<>();
		for (int b = 0; b < checkBoxArray.size(); b++) {
			ArrayList<? extends JCheckBox> row = checkBoxArray.get(b);
			booleanArr.add(checksToBooleanArray(row));
		}
		return booleanArr;		
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
		for (int ord = 0; ord < filteredOrders.size(); ord++) {
			colNames.add(getCompanyNameDatePO(filteredOrders.get(ord)));
		}
		return colNames;
	}
	
	private String getCompanyNameDatePO(Order ord) {
		int length = 12;
		String name = ord.getCompany();
		if (name.length() > length) {
			name = name.substring(0, length - 1) + "..";
		}
		return "<html>" +  name + "<br>" + ord.getPONum() + "<br>" + ord.getItems().get(0).getPackDate().getMMDD() + "</html>";
	}
	
	private void addRows() {
		JLabel orderLabel = new JLabel("ORDERS");
		Utilities.setMinMax(orderLabel, HEADER_SIZE);
		itemColumn.add(orderLabel);
		getDisplayArray();
		AppState.setIndivItemSelectedArray(checksToDoubleBooleanArray());
		addRowsToDisplay(0);
	}
	
	private void addRowsToDisplay(int start) {
		for (int r = start; r < displayArray.size(); r++) {
			ArrayList<Float> row = displayArray.get(r);
			HPanel rowPanel = new HPanel();
			HPanel itemNamePanel = new HPanel();
			JLabel prodName = new JLabel("<html>" + prodNames.get(r) + "<br>" + unitCodes.get(r) +  "</html>");
			Utilities.setMinMax(prodName, ITEM_NAME_SIZE);
			ItemCheckBox itemBox = new ItemCheckBox(r);
			itemNamePanel.add(itemBox);
			itemBoxes.add(itemBox);
			itemNamePanel.add(prodName);
			Utilities.setMinMax(itemNamePanel, HEADER_SIZE);
			for (int orderNum = 0; orderNum < row.size(); orderNum++) {
				addItemToRow(rowPanel, r, orderNum);
			}
			Utilities.localHPack(itemNamePanel);
			Utilities.localHPack(rowPanel);
			itemColumn.add(itemNamePanel);
			infoPanel.add(rowPanel);
		}
		AppState.setItemSelectedArray(checksToBooleanArray(itemBoxes));
	}
	
	private void addItemToRow(HPanel rowPanel, int rowNum, int orderNum) {
		JLabel qtyLabel = new JLabel(Float.toString(displayArray.get(rowNum).get(orderNum)));
		Utilities.setMinMax(qtyLabel, NUMBER_SIZE);
		rowPanel.add(checkBoxArray.get(rowNum).get(orderNum));
		rowPanel.add(qtyLabel);
	}
		
	private void getDisplayArray() {
		gtins = new ArrayList<String>();
		prodNames = new ArrayList<String>();
		unitCodes = new ArrayList<>();
		displayArray = new ArrayList<ArrayList<Float>>();
		checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();
		itemArray = new ArrayList<ArrayList<LabelableItem>>();
		
		for (int ord = 0; ord < filteredOrders.size(); ord++) {
			addEmptyArraysForOrderItems(filteredOrders.get(ord));
		}
		
		for (int orderIndex = 0; orderIndex < filteredOrders.size(); orderIndex++) {
			setDisplayArrayForOrder(filteredOrders.get(orderIndex), orderIndex);
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
				unitCodes.add(item.getUnit());
				displayArray.add(createZeroArray(filteredOrders.size()));
				checkBoxArray.add(createPrintCheckBoxes(filteredOrders.size()));
				itemArray.add(createLabelableItemList(filteredOrders.size()));
			}
		}
	}
	
	private ArrayList<LabelableItem> createLabelableItemList(int length) {
		ArrayList<LabelableItem> list = new ArrayList<LabelableItem>();
		for (int i = 0; i < length; i++) {
			list.add(null);
		}
		return list;
	}
	
	private void setDisplayArrayForOrder(Order order, int orderIndex) {
		ArrayList<LabelableItem> items = order.getItems();
		for (int it = 0; it < items.size(); it++) {
			LabelableItem item = items.get(it);
			int index = gtins.indexOf(item.getGtin());
			displayArray.get(index).set(orderIndex, item.getQuantity());
			
			if (item.getQuantity() > 0) {
				PrintCheckBox box = new PrintCheckBox(item, orderIndex, index);
				checkBoxArray.get(index).set(orderIndex, box);
				itemArray.get(index).set(orderIndex, item);
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
	
	private void updateCheckBoxes() {
		ArrayList<Boolean> companiesSelected = AppState.getCompanySelectedArray();
		ArrayList<Boolean> itemsSelected = AppState.getItemSelectedArray();
		ArrayList<ArrayList<Boolean>> indivItemSelections = AppState.getIndivItemSelectedArray();
		for (int c = 0; c < companiesSelected.size(); c++) {
			companyBoxes.get(c).setSelected(companiesSelected.get(c));
		}
		for (int c = 0; c < itemsSelected.size(); c++) {
			itemBoxes.get(c).setSelected(itemsSelected.get(c));
		}
		for (int r = 0; r < checkBoxArray.size(); r++) {
			ArrayList<PrintCheckBox> row = checkBoxArray.get(r);
			ArrayList<Boolean> indivSelectionRow = indivItemSelections.get(r);
			for (int c = 0; c < row.size(); c++) {
				if (row.get(c).isEnabled()) {
					row.get(c).setSelected(indivSelectionRow.get(c));
				}
			}			
		}		
	}
	
	private void resetOrders() {
		wholePanel.removeAll();
		displayPanel.removeAll();
		itemColumn = new VPanel();
		infoPanel = new VPanel();
		orders = AppState.getOrders();
		filteredOrders = new ArrayList<>();
		addOrderArray();
	}

	@Override
	public Container getMainContent() {
		return wholePanel;
	}

	@Override
	public void sendMessage(AppListenerMessage m) {
		switch (m) {
			case UPDATE_CHECKBOXES:
				updateCheckBoxes();
				break;
			default:
				resetOrders();
				break;
		}
	}
}
