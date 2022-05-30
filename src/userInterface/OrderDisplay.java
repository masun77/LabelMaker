package userInterface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import export.DataSaver;
import main.Item;
import main.Order;
import main.Utilities;

public class OrderDisplay extends JPanel {
	private ArrayList<Order> orders = null;
	private JPanel orderPanel = null;
	private final Dimension NAME_SIZE = new Dimension(80,15);
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private ActionListener entryListener;
	private String saveFileName;

	public OrderDisplay(ArrayList<Order> ords, ActionListener entryList, String saveFile) {
		orders = ords;
		entryListener = entryList;
		saveFileName = saveFile;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setOrderArray();
		add(addButtons());
		add(orderPanel);
		Utilities.localVPack(this);
	}
	
	public void refresh() {
		remove(1);
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
		enterOrderButton.setMinimumSize(new Dimension(100,50));
		buttonPanel.add(enterOrderButton);
		Utilities.localHPack(buttonPanel);
		return buttonPanel;
	}
	
	private Component getColumnNames() {
		JPanel headerRow = new JPanel();
		headerRow.setLayout(new BoxLayout(headerRow, BoxLayout.X_AXIS));
		ArrayList<String> colNames = getCompanyNames();
		Label compLabel = new Label("Company:");
		Utilities.setMinMax(compLabel, NAME_SIZE);
		headerRow.add(compLabel);
		for(int n = 0; n < colNames.size(); n++) {
			Label nameLabel = new Label(colNames.get(n));
			Utilities.setMinMax(nameLabel, NAME_SIZE);
			headerRow.add(new TrashButton(n));
			headerRow.add(nameLabel);
		}
		Utilities.localHPack(headerRow);
		return headerRow;
	}
	
	private class TrashButton extends JButton {
		private final Dimension TRASH_BTN_SIZE = new Dimension(20,20);
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
		return displayArray;
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
			rowPanel.add(prodName);
			for (int it = 0; it < row.size(); it++) {
				Label qty = new Label(Integer.toString(row.get(it)));
				Utilities.setMinMax(qty, NAME_SIZE);
				rowPanel.add(qty);
			}
			Utilities.localHPack(rowPanel);
			orderPanel.add(rowPanel);
		}
	}
}
