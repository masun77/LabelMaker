package userInterface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import export.DataSaver;
import labels.LabelView;
import labels.LabelViewerImp;
import labels.Labelable;
import main.Item;
import main.Order;
import main.Utilities;

public class OrderDisplay extends JPanel {
	private ArrayList<Order> orders = null;
	private JPanel orderPanel = null;
	private final Dimension NAME_SIZE = new Dimension(80,15);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension TRASH_BTN_SIZE = new Dimension(20,20);
	private final Dimension BTN_SIZE = new Dimension(200,25);
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private ActionListener entryListener;
	private String saveFileName;
	ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();;

	public OrderDisplay(ArrayList<Order> ords, ActionListener entryList, String saveFile) {
		orders = ords;
		entryListener = entryList;
		saveFileName = saveFile;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setOrderArray();
		add(addButtons());
		add(Box.createRigidArea(new Dimension(1,10)));
		add(orderPanel);
		Utilities.localVPack(this);
	}
	
	public void refresh() {
		remove(2);
		setOrderArray();
		add(orderPanel);
		Utilities.localVPack(this);
	}
	
	private void setOrderArray() {
		orderPanel = new JPanel();
		orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
		orderPanel.add(getColumnNames());
		ArrayList<ArrayList<Integer>> displayArray = getDisplayArray();
		addRows(displayArray);
		Utilities.localVPack(orderPanel);
	}
	
	private Component addButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		JButton enterOrderButton = new JButton("Add new order");
		enterOrderButton.addActionListener(entryListener);
		Utilities.setMinMax(enterOrderButton, BTN_SIZE);
		buttonPanel.add(enterOrderButton);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
		
		JButton printButton = new JButton("View/Print Selected Labels");
		printButton.addActionListener(new PrintListener());
		Utilities.setMinMax(printButton, BTN_SIZE);
		buttonPanel.add(printButton);
		
		Utilities.localHPack(buttonPanel);
		return buttonPanel;
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Labelable> items = getCheckedItems();
			LabelView lv = new LabelViewerImp();
			lv.showLabels(items);
		}
	}
	
	private ArrayList<Labelable> getCheckedItems() {
		ArrayList<Labelable> items = new ArrayList<Labelable>();

		if (checkBoxArray.size() > 0) {
			int cols = checkBoxArray.get(0).size();
			for (int row = 0; row < checkBoxArray.size(); row++) {
				for (int col = 0; col < cols; col++) {
					PrintCheckBox check = checkBoxArray.get(row).get(col);
					if (check.isSelected()) {
						items.add(check.getItem());
					}
				}
			}		
		}
		return items;
	}
	
	private Component getColumnNames() {
		JPanel headerRow = new JPanel();
		headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
		ArrayList<String> colNames = getCompanyNames();
		if (colNames.size() > 0) {
			Label compLabel = new Label("Company:");
			Utilities.setMinMax(compLabel, NAME_SIZE);
			headerRow.add(compLabel);			
		}
		for(int n = 0; n < colNames.size(); n++) {
			Label nameLabel = new Label(colNames.get(n));
			Utilities.setMinMax(nameLabel, NAME_SIZE);
			headerRow.add(new CompanyCheckBox(n));
			headerRow.add(nameLabel);
			headerRow.add(new TrashButton(n));
			Label spacer = new Label();
			Utilities.setMinMax(spacer, SPACE);
			headerRow.add(spacer);
		}
		Utilities.localHPack(headerRow);
		return headerRow;
	}
	
	private class CompanyCheckBox extends JCheckBox {
		private int orderNum;
		
		public CompanyCheckBox(int o) {
			orderNum = o;
			addItemListener(new CompanyCheckListener(orderNum, this));
		}
	}
	
	private class CompanyCheckListener implements ItemListener {
		private int orderIndex;
		private JCheckBox button;
		
		public CompanyCheckListener(int ind, JCheckBox btn) {
			orderIndex = ind;
			button = btn;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			boolean state = button.isSelected();
			for (int r = 0; r < checkBoxArray.size(); r++) {
				PrintCheckBox box = checkBoxArray.get(r).get(orderIndex);
				if (box.isEnabled()) {
					box.setSelected(state);
				}
			}
		}
		
	}
	
	private class ItemCheckBox extends JCheckBox {
		private int rowNum;
		
		public ItemCheckBox(int row) {
			rowNum = row;
			addItemListener(new ItemCheckListener(row, this));
		}
	}
	
	private class ItemCheckListener implements ItemListener {
		private int rowNum;
		private JCheckBox button;
		
		public ItemCheckListener(int rn, JCheckBox btn) {
			rowNum = rn;
			button = btn;
		}
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			boolean state = button.isSelected();
			ArrayList<PrintCheckBox> row = checkBoxArray.get(rowNum);
			for (int r = 0; r < row.size(); r++) {
				PrintCheckBox box = row.get(r);
				if (box.isEnabled()) {
					box.setSelected(state);
				}
			}
		}
	}
	
	private class PrintCheckBox extends JCheckBox {
		Item item;
		
		public PrintCheckBox(Item it) {
			item = it;
			Utilities.setMinMax(this, TRASH_BTN_SIZE);
		}
		
		public Labelable getItem() {
			return item;
		}
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
			refresh();
			DataSaver.writeOrdersToCSV(orders, saveFileName);
		}
		
	}
	
	private ArrayList<String> getCompanyNames() {
		ArrayList<String> colNames = new ArrayList<String>();
		for (int ord = 0; ord < orders.size(); ord++) {
			String comp = orders.get(ord).getCompany();
			if (!colNames.contains(comp)) {
				colNames.add(comp);
			}
		}
		return colNames;
	}
	
	private ArrayList<ArrayList<Integer>> getDisplayArray() {
		gtins = new ArrayList<String>();
		prodNames = new ArrayList<String>();
		ArrayList<ArrayList<Integer>> displayArray = new ArrayList<ArrayList<Integer>>();
		for (int ord = 0; ord < orders.size(); ord++) {
			Order order = orders.get(ord);
			ArrayList<Item> items = order.getItems();
			for (int it = 0; it < items.size(); it++) {
				Item item = items.get(it);
				int index = gtins.indexOf(item.getGtin());
				ArrayList<Integer> currArr = new ArrayList<Integer>();
				if (index < 0) {
					gtins.add(item.getGtin());
					prodNames.add(item.getProductName());
					currArr = createZeroArray(orders.size());
					displayArray.add(currArr);
				}
				else {
					currArr = displayArray.get(index);
				}
				currArr.set(ord, item.getQuantity());
			}
		}
		setCheckBoxes(displayArray);
		return displayArray;
	}
	
	private void setCheckBoxes(ArrayList<ArrayList<Integer>> array) {
		if (array.size() > 0) {
			int rows = array.size();
			int cols = array.get(0).size();
			checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>(rows);
			for (int r = 0; r < rows; r++) {
				ArrayList<PrintCheckBox> row = new ArrayList<PrintCheckBox>();
				for (int c = 0; c < cols; c++) {
					PrintCheckBox box = new PrintCheckBox(orders.get(c).getItem(prodNames.get(r)));
					if (array.get(r).get(c) == 0) {
						box.setEnabled(false);
					}
					row.add(box);
				}
				checkBoxArray.add(row);
			}						
		}
	}
	
	private ArrayList<Integer> createZeroArray(int numZeros) {
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(0);
		}
		return arr;
	}
	
	private void addRows(ArrayList<ArrayList<Integer>> displayArray) {
		for (int r = 0; r < displayArray.size(); r++) {
			ArrayList<Integer> row = displayArray.get(r);
			JPanel rowPanel = new JPanel();
			rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
			Label prodName = new Label(prodNames.get(r));
			Utilities.setMinMax(prodName, NAME_SIZE);
			rowPanel.add(new ItemCheckBox(r));
			rowPanel.add(prodName);
			for (int q = 0; q < row.size(); q++) {
				Label qty = new Label(Integer.toString(row.get(q)));
				Utilities.setMinMax(qty, NAME_SIZE);
				rowPanel.add(checkBoxArray.get(r).get(q));
				rowPanel.add(qty);
			}
			Utilities.localHPack(rowPanel);
			orderPanel.add(rowPanel);
		}
	}
}
