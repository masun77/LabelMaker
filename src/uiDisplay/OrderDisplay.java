package uiDisplay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import freshStart.Date;
import freshStart.DateImp;
import freshStart.HPanel;
import freshStart.VPanel;
import main.AppListenerMessage;
import main.AppState;
import main.LabelableItem;
import main.Order;
import uiLogic.HomeFunction;
import uiSubcomponents.CompanyCheckBox;
import uiSubcomponents.CompanyHeader;
import uiSubcomponents.ItemCheckBox;
import uiSubcomponents.PrintCheckBox;
import uiSubcomponents.ProductInfo;

public class OrderDisplay implements HomeFunction {
	// Orders
	private ArrayList<Order> orders;
	private ArrayList<Order> filteredOrders = new ArrayList<>();
	
	// Helper variables
	private Date startDate;
	private Date endDate;	
	private ArrayList<ProductInfo> products = new ArrayList<>();
	private ArrayList<CompanyHeader> companyHeaders = new ArrayList<>();
	private ArrayList<ArrayList<Float>> quantities = new ArrayList<>();
	private ArrayList<ArrayList<LabelableItem>> itemArray = new ArrayList<>();
	
	// Display variables
	private JPanel wholePanel = new VPanel();
	private JPanel datePanel = new HPanel();
	private JPanel headerRow = new HPanel();
	private JPanel itemColumn = new VPanel();
	private JPanel qtysPanel = new VPanel(); 
	private JScrollPane scrollPane = new JScrollPane();
	private JTextField startDateField = new JTextField();
	private JTextField endDateField = new JTextField();
	private ArrayList<ArrayList<PrintCheckBox>> checkBoxArray;
	private ArrayList<CompanyCheckBox> companyBoxes;
	private ArrayList<ItemCheckBox> itemBoxes;
	
	// Sizes
	private final Dimension COMPANY_NAME_SIZE = new Dimension(80,70);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension NUMBER_SIZE = new Dimension(90,50);
	private final Dimension ITEM_NAME_SIZE = new Dimension(200,50);
	private final Dimension FILTER_SIZE = new Dimension(390,30);
	private final Dimension DATE_SIZE = new Dimension(50,20);


	public OrderDisplay() {
		orders = AppState.getOrders();
		
		//setDates();	 todo
		setPracticeDates();
		initialize();
	}
	
	private void initialize() {
		addDateFilter();
		setupPanels();

		filterOrders();
		if (filteredOrders.size() > 0) {
			createArrayValues();
			displayOrders();
		}  
	}
	
	private void setDates() {		
		Calendar cal = Calendar.getInstance();
		startDate = DateImp.parseDate(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		endDate = DateImp.parseDate(startDate.getDateMMDDYYYY());
		endDate.addDays(2);
	}
	
	private void setPracticeDates() {
		startDate = new DateImp(1,1,2022);
		endDate = new DateImp(12,1,2022);
	}
	
	private void setupPanels() {
		scrollPane = new JScrollPane(qtysPanel);
		scrollPane.setColumnHeaderView(headerRow);
		scrollPane.setRowHeaderView(itemColumn);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, new JLabel("     ORDERS"));
		wholePanel.add(scrollPane);
	}
	
	private void addDateFilter() {
		datePanel = new HPanel();
		Utilities.setMinMax(datePanel, FILTER_SIZE);
		startDateField.setSize(DATE_SIZE);
		startDateField.setText(startDate.getDateMMDDYYYY());
		startDateField.addFocusListener(new DateFilter());
		endDateField.setSize(DATE_SIZE);
		endDateField.setText(endDate.getDateMMDDYYYY());
		endDateField.addFocusListener(new DateFilter());
		
		datePanel.add(new JLabel("From: "));
		datePanel.add(startDateField);
		datePanel.add(new JLabel("To: "));
		datePanel.add(endDateField);
		
		wholePanel.add(datePanel);
	}
	
	private void filterOrders() {
		for (Order o: orders) {
			Date packDate = o.getItems().get(0).getPackDate();
			if (!packDate.dateEarlierThan(startDate) && !packDate.dateLaterThan(endDate)) {
				insertOrder(filteredOrders, o);
			}
		}
		AppState.setFilteredOrders(filteredOrders);
	}
	
	private int insertOrder(ArrayList<Order> ords, Order o) {
		int index = 0;
		if (ords.size() == 0) {
			ords.add(o);
			return index;
		}
		
		Order currOrder = ords.get(index);
		int max = 0;
		while (index < ords.size() && currOrder.getPackDate().dateEarlierThan(o.getPackDate())) {			
			index += 1;
			if (index == ords.size()) {
				break;
			}
			currOrder = ords.get(index);
		}
		while (index < ords.size() && currOrder.getCompany().compareTo(o.getCompany()) < 0
				&& o.getPackDate().dateEquals(currOrder.getPackDate())) {
			index += 1;
			if (index == ords.size()) {
				break;
			}
			currOrder = ords.get(index);
		}
		
		ords.add(index, o);
		return index;
	}
	
	private int insertProductInfo(ArrayList<ProductInfo> prods, ProductInfo newInfo) {
		int index = 0;
		if (prods.size() == 0) {
			prods.add(newInfo);
			return index;
		}
		ProductInfo currInfo = prods.get(index);
		while (currInfo.getDisplayName().compareTo(newInfo.getDisplayName()) < 0) {
			index += 1;
			currInfo = prods.get(index);
		}
		prods.add(index, newInfo);
		return index;
	}
	
	private void createArrayValues() {
		for (int ord = 0; ord < filteredOrders.size(); ord++) {
			Order currOrder = filteredOrders.get(ord);
			ArrayList<LabelableItem> items = currOrder.getItems();
			CompanyHeader ch = new CompanyHeader(getShortName(currOrder.getCompany(),19), currOrder.getPONum(), currOrder.getPackDate().getMMDD());
			companyHeaders.add(ch);
			for (int it = 0; it < items.size(); it++) {
				LabelableItem item = items.get(it);
				ProductInfo pi = new ProductInfo(item.getProductName(), item.getUnit(), item.getItemCode());
				if (!products.contains(pi)) {
					products.add(pi);
					ArrayList<Float> newQtyRow = createZeroArray(filteredOrders.size());
					newQtyRow.set(ord, item.getQuantity());
					quantities.add(newQtyRow);
					ArrayList<LabelableItem> newItemRow = createEmptyLabelArray(filteredOrders.size());
					newItemRow.set(ord, item);
					itemArray.add(newItemRow);
				}
				else {
					int row = products.indexOf(pi);
					quantities.get(row).set(ord, item.getQuantity());
					itemArray.get(row).set(ord, item);
				}
			}
		}
		AppState.setItemArray(itemArray);
	}
	
	private String getShortName(String name, int maxLength) {
		if (name.length() > maxLength) {
			return name.substring(0,maxLength -1) + "..";
		}
		return name;
	}
	
	private ArrayList<Float> createZeroArray(int numZeros) {
		ArrayList<Float> arr = new ArrayList<Float>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(0.0f);
		}
		return arr;
	}
	
	private ArrayList<LabelableItem> createEmptyLabelArray(int numZeros) {
		ArrayList<LabelableItem> arr = new ArrayList<>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(null);
		}
		return arr;
	}	
	
	private void displayOrders() {
		companyBoxes = new ArrayList<>();
		itemBoxes = new ArrayList<>();
		checkBoxArray = new ArrayList<>();
		
		addHeaderRow();
		addItemColumn();
		addQuantities();
		Utilities.localVPack(qtysPanel);
	}
	
	private void addHeaderRow() {
		for (int c = 0; c < companyHeaders.size(); c++) {
			CompanyHeader ch = companyHeaders.get(c);
			JLabel nameLabel = new JLabel("<html>" + ch.getCompanyName() + "<br>" + ch.getPONum() 
				+ "<br>" + ch.getDate() + "</html>");
			Utilities.setMinMax(nameLabel, COMPANY_NAME_SIZE);
			CompanyCheckBox currCompany = new CompanyCheckBox(c);
			headerRow.add(currCompany);
			companyBoxes.add(currCompany);
			headerRow.add(nameLabel);
			JLabel spacer = new JLabel();
			Utilities.setMinMax(spacer, SPACE);
			headerRow.add(spacer);
		}
		Utilities.localHPack(headerRow);
		AppState.setCompanySelectedArray(checksToBooleanArray(companyBoxes));
	}
	
	private void addItemColumn() {		
		for (int it = 0; it < products.size(); it++) {
			ProductInfo pi = products.get(it);
			HPanel itemNamePanel = new HPanel();
			JLabel prodName = new JLabel("<html>" + getShortName(pi.getDisplayName(),35) + "<br>" + pi.getUnit() +  "</html>");
			Utilities.setMinMax(prodName, ITEM_NAME_SIZE);
			ItemCheckBox itemBox = new ItemCheckBox(it);
			itemBoxes.add(itemBox);
			itemNamePanel.add(itemBox);
			itemNamePanel.add(prodName);
			Utilities.setMinMax(itemNamePanel, ITEM_NAME_SIZE);
			itemColumn.add(itemNamePanel);
		}
		Utilities.localVPack(itemColumn);		
		AppState.setItemSelectedArray(checksToBooleanArray(itemBoxes));
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
	
	private void addQuantities() {
		for (int row = 0; row < products.size(); row++) {
			HPanel rowPanel = new HPanel();
			rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
			ArrayList<PrintCheckBox> checkBoxRow = new ArrayList<>();
			checkBoxArray.add(checkBoxRow);
			for (int ord = 0; ord < filteredOrders.size(); ord++) {
				float qty = quantities.get(row).get(ord);
				JLabel qtyLabel = new JLabel(Float.toString(qty));
				Utilities.setMinMax(qtyLabel, NUMBER_SIZE);
				PrintCheckBox pcb = new PrintCheckBox(itemArray.get(row).get(ord), ord, row);
				if (qty == 0) {
					pcb.setEnabled(false);
					pcb.setBackground(Color.white);
				}
				checkBoxRow.add(pcb);
				rowPanel.add(pcb);
				rowPanel.add(qtyLabel);
			}
			Utilities.localHPack(rowPanel);
			qtysPanel.add(rowPanel);
		}
		AppState.setIndivItemSelectedArray(checksToDoubleBooleanArray());
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
		headerRow = new HPanel();
		itemColumn = new VPanel();
		qtysPanel = new VPanel();
		
		orders = AppState.getOrders();
		filteredOrders = new ArrayList<>();
		products = new ArrayList<>();
		companyHeaders = new ArrayList<>();
		quantities = new ArrayList<>();
		itemArray = new ArrayList<>();

		initialize();
		wholePanel.validate();
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
	
	private class DateFilter implements FocusListener {			
		@Override
		public void focusGained(FocusEvent e) {
			// do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			Date newStart = DateImp.parseDate(startDateField.getText());
			Date newEnd = DateImp.parseDate(endDateField.getText());
			if (!newStart.dateEquals(startDate) || !newEnd.dateEquals(endDate)) {
				startDate = newStart;
				endDate = newEnd;
				resetOrders();
			}
		}
	}

	@Override
	public void setScrollPaneSize(int width, int height) {
		scrollPane.setPreferredSize(new Dimension(width, height - (int) FILTER_SIZE.getHeight()));
		scrollPane.setSize(new Dimension(width, height - (int) FILTER_SIZE.getHeight()));
	}
}
