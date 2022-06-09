package userInterface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
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
import javax.swing.JFrame;
import javax.swing.JPanel;

import export.DataSaver;
import export.SocketClient;
import labels.LabelView;
import labels.LabelViewerImp;
import labels.LabelableItem;
import main.Order;
import main.Utilities;

public class OrderDisplay extends JPanel {
	private ArrayList<Order> orders = null;
	private JPanel orderPanel = null;
	private final Dimension NAME_SIZE = new Dimension(80,15);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension TRASH_BTN_SIZE = new Dimension(20,15);
	private final Dimension BTN_SIZE = new Dimension(200,25);
	private final Dimension NUMBER_SIZE = new Dimension(110,15);
	private final Dimension CHECK_SIZE = new Dimension(15,15);
	private final Dimension ITEM_NAME_SIZE = new Dimension(100,15);
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();
	private ActionListener entryListener;
	private String saveFileName;
	ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = new ArrayList<ArrayList<PrintCheckBox>>();;
	private SocketClient sock = new SocketClient();

	public OrderDisplay(ArrayList<Order> ords, ActionListener entryList, String saveFile) {
		orders = ords;
		entryListener = entryList;
		saveFileName = saveFile;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addButtons();
		add(Box.createRigidArea(new Dimension(1,10)));
		addOrderArray();
		Utilities.localVPack(this);
	}
	
	public void refresh() {
		remove(2);
		for(int o =0; o < orders.size(); o++) {
			orders.get(o).printOrder();
		}
		addOrderArray();
		Utilities.localVPack(this);
		DataSaver.writeOrdersToCSV(orders, saveFileName);
	}
	
	private void addOrderArray() {
		orderPanel = new JPanel();
		orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
		orderPanel.add(getColumnNames());
		ArrayList<ArrayList<Float>> displayArray = getDisplayArray();
		addRows(displayArray);
		Utilities.localVPack(orderPanel);
		add(orderPanel);
	}
	
	private void addButtons() {
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
		buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
		
		JButton updateButton = new JButton("Send Orders to Server");
		updateButton.addActionListener(new UpdateListener());
		Utilities.setMinMax(updateButton, BTN_SIZE);
		buttonPanel.add(updateButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
		
		JButton getOrders = new JButton("Get Orders from Server");
		getOrders.addActionListener(new GetOrdersListener());
		Utilities.setMinMax(getOrders, BTN_SIZE);
		buttonPanel.add(getOrders);
		buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
		
		JButton setServerIP = new JButton("Set Server IP");
		setServerIP.addActionListener(new SetIPListener());
		Utilities.setMinMax(setServerIP, BTN_SIZE);
		buttonPanel.add(setServerIP);
		
		Utilities.localHPack(buttonPanel);
		add(buttonPanel);
	}
	
	private class UpdateListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			sock.sendOrders(orders);
		}	
	}
	
	private class GetOrdersListener implements ActionListener {	
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<Order> returned = sock.getOrders();
			if (returned.size() > 0) {
				orders = returned;
			}
			refresh();
		}	
	}
	
	private class SetIPListener implements ActionListener {	
		TextField ipAddr = new TextField();
		Label ipLabel = new Label("Server ip address: ");
		JFrame frame;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = new JButton("Save ip address");
			frame = new JFrame();
			JPanel panel = new JPanel();
			ipLabel.setPreferredSize(new Dimension(300,20));
			ipAddr.setPreferredSize(new Dimension(200,20));
			panel.add(ipLabel);
			panel.add(ipAddr);
			button.setSize(new Dimension(100,20));
			button.addActionListener(new IPListener());
			panel.add(button);
			frame.add(panel);
			frame.setSize(new Dimension(600,200));
			frame.setVisible(true);
		}	
		
		private class IPListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				sock.setServer(ipAddr.getText());
				frame.dispose();
			}
		}
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<LabelableItem> items = getCheckedItems();
			LabelView lv = new LabelViewerImp();
			lv.showLabels(items);
		}
	}
	
	private ArrayList<LabelableItem> getCheckedItems() {
		ArrayList<LabelableItem> items = new ArrayList<LabelableItem>();

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
			Utilities.setMinMax(this, CHECK_SIZE);
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
			Utilities.setMinMax(this, CHECK_SIZE);
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
		LabelableItem item;
		
		public PrintCheckBox(LabelableItem it) {
			item = it;
			Utilities.setMinMax(this, CHECK_SIZE);
		}
		
		public LabelableItem getItem() {
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
		}
		
	}
	
	private ArrayList<String> getCompanyNames() {
		ArrayList<String> colNames = new ArrayList<String>();
		ArrayList<String> display = new ArrayList<String>();
		for (int ord = 0; ord < orders.size(); ord++) {
			String name = orders.get(ord).getCompany();
			display.add(name);
			String nameDate = name.substring(0, 7 > name.length()? name.length(): 7) + ", " + orders.get(ord).getShipDate().getMMDD();
			if (!colNames.contains(nameDate)) {
				colNames.add(nameDate);
			}
			display.add(nameDate);
		}
		return colNames;
	}
	
	private void displayAll(ArrayList<String> strs) {
		JPanel p = new JPanel();
		
		for (int i = 0; i < strs.size(); i++) {
			p.add(new Label(strs.get(i)));
		}
		
		JFrame f = new JFrame();
		f.setSize(new Dimension(400,400));
		f.add(p);
		f.setVisible(true);
	}
	
	private ArrayList<ArrayList<Float>> getDisplayArray() {
		gtins = new ArrayList<String>();
		prodNames = new ArrayList<String>();
		ArrayList<ArrayList<Float>> displayArray = new ArrayList<ArrayList<Float>>();
		for (int ord = 0; ord < orders.size(); ord++) {
			Order order = orders.get(ord);
			ArrayList<LabelableItem> items = order.getItems();
			for (int it = 0; it < items.size(); it++) {
				LabelableItem item = items.get(it);
				int index = gtins.indexOf(item.getGtin());
				ArrayList<Float> currArr = new ArrayList<Float>();
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
	
	private void setCheckBoxes(ArrayList<ArrayList<Float>> array) {
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
	
	private ArrayList<Float> createZeroArray(int numZeros) {
		ArrayList<Float> arr = new ArrayList<Float>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(0.0f);
		}
		return arr;
	}
	
	private void addRows(ArrayList<ArrayList<Float>> displayArray) {
		for (int r = 0; r < displayArray.size(); r++) {
			ArrayList<Float> row = displayArray.get(r);
			JPanel rowPanel = new JPanel();
			rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
			Label prodName = new Label(prodNames.get(r));
			Utilities.setMinMax(prodName, ITEM_NAME_SIZE);
			rowPanel.add(new ItemCheckBox(r));
			rowPanel.add(prodName);
			for (int q = 0; q < row.size(); q++) {
				Label qty = new Label(Float.toString(row.get(q)));
				Utilities.setMinMax(qty, NUMBER_SIZE);
				rowPanel.add(checkBoxArray.get(r).get(q));
				rowPanel.add(qty);
			}
			Utilities.localHPack(rowPanel);
			orderPanel.add(rowPanel);
		}
	}
}
