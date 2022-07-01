package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import export.FileBackup;
import labels.LabelableItem;
import main.AppState;
import main.Order;
import userInterface.graphicComponents.CompanyCheckBox;
import userInterface.graphicComponents.HPanel;
import userInterface.graphicComponents.ItemCheckBox;
import userInterface.graphicComponents.PrintCheckBox;
import userInterface.graphicComponents.VPanel;

public class OrderDisplay implements AppFunction {
	// Application variables from Application state
	private ArrayList<Order> orders;
	private FileBackup fb;
	
	// Display variations
	private JFrame frame = new JFrame();
	private JPanel mainPanel = new VPanel();
	private final Dimension NAME_SIZE = new Dimension(80,15);
	private final Dimension SPACE = new Dimension(10,15);
	private final Dimension TRASH_BTN_SIZE = new Dimension(20,15);
	private final Dimension NUMBER_SIZE = new Dimension(110,15);
	private final Dimension ITEM_NAME_SIZE = new Dimension(100,15);
	private ArrayList<String> gtins = new ArrayList<String>();
	private ArrayList<String> prodNames = new ArrayList<String>();

	public OrderDisplay() {
		orders = AppState.getOrders();
		fb = AppState.getFileBackup();
		
		addOrderArray();
	}
	
	@Override
	public void refresh() {
		mainPanel.removeAll();
		orders = AppState.getOrders();
		addOrderArray();
	}

	@Override
	public void showFunction() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(mainPanel);
		frame.setSize(new Dimension(500,500));
		frame.setVisible(true);
	}

	@Override
	public Container getMainContent() {
		return mainPanel;
	}
	
	private void addOrderArray() {
		mainPanel.add(getColumnPanel());
		ArrayList<ArrayList<Float>> displayArray = getDisplayArray();
		addRows(displayArray);
		Utilities.localVPack(mainPanel);
		mainPanel.validate();
	}
	
	private Component getColumnPanel() {
		JPanel headerRow = new HPanel();
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
		
	private ArrayList<String> getCompanyNames() {
		ArrayList<String> colNames = new ArrayList<String>();
		for (int ord = 0; ord < orders.size(); ord++) {
			String name = orders.get(ord).getCompany();
			String nameDate = name.substring(0, 7 > name.length()? name.length(): 7) + ", " + orders.get(ord).getShipDate().getMMDD();
			if (!colNames.contains(nameDate)) {
				colNames.add(nameDate);
			}
		}
		return colNames;
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
				ArrayList<Float> currArr = new ArrayList<Float>();
				int index = gtins.indexOf(item.getGtin());
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
		ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = null;
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
		AppState.setCheckBoxArray(checkBoxArray);  
	}
	
	private ArrayList<Float> createZeroArray(int numZeros) {
		ArrayList<Float> arr = new ArrayList<Float>();
		for (int n = 0; n < numZeros; n++) {
			arr.add(0.0f);
		}
		return arr;
	}
	
	private void addRows(ArrayList<ArrayList<Float>> displayArray) {
		ArrayList<ArrayList<PrintCheckBox>> checkBoxArray = AppState.getCheckBoxArray();
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
			mainPanel.add(rowPanel);
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
}
