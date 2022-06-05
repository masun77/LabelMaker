package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import export.DataSaver;
import labels.DateImp;
import labels.LabelableItem;
import main.Item;
import main.Order;
import main.RDFItem;
import main.Utilities;

public class EntryForm extends JFrame {
	private final JPanel mainPanel = new JPanel();
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ActionListener saveListener;
	private TextField date = new TextField(10);
	private TextField company = new TextField(30);
	private TextField purchaseOrder = new TextField(15);
	private TextField shipVia = new TextField(15);
	private final int NUM_ITEMS = 20;
	private JPanel itemPanel = new JPanel();
	private Label totalLabel = new Label("$0");
	private ArrayList<TextField> amounts = new ArrayList<TextField>();
	private final Dimension LABEL_SIZE = new Dimension(100,15);
	private final Dimension QTY_SIZE = new Dimension(30,15);
	private final Dimension CODE_SIZE = new Dimension(80,15);
	private final Dimension DESCRIP_SIZE = new Dimension(200,15);
	private final Dimension PRICE_SIZE = new Dimension(40,15);
	private final Dimension AMOUNT_SIZE = new Dimension(60,15);
	private final Dimension BUTTON_SIZE = new Dimension(150,50);
	private final String ITEM_DATA_CSV_PATH = "resources/itemData.csv";

	public EntryForm(ActionListener saveLstn, ArrayList<Order> ords) {
		orders = ords;
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		saveListener = saveLstn;
		initialize();
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		this.add(scrollPane);
	}
	
	private void initialize() {
		addOrderDate();
		addOrderCompany();
		addPurchaseOrder();
		addShipVia();
		addItems();
		addTotal();
		addSaveButton(saveListener);
		
		Utilities.localVPack(mainPanel);
	}
	
	private void addLabelComponentPair(String label, Component tf) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		Label labelComp = new Label(label);
		Utilities.setMinMax(labelComp, LABEL_SIZE);
		panel.add(labelComp);
		panel.add(tf);
		Utilities.setMinMax(tf, LABEL_SIZE);
		mainPanel.add(panel);
	}
	
	private void addOrderDate() {
		addLabelComponentPair("Date: ", date);
	}
	
	private void addOrderCompany() {
		addLabelComponentPair("Company: ", company);
	}
	
	private void addPurchaseOrder() {
		addLabelComponentPair("PO num: ", purchaseOrder);
	}
	
	private void addShipVia() {
		addLabelComponentPair("Ship Via: ", shipVia);
	}
	
	private void addItems() {
		addItemHeaders();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < NUM_ITEMS; i++) {
			addItemRow();
		}
		Utilities.localVPack(itemPanel);
		mainPanel.add(itemPanel);
	}
	
	private class CodeListener implements FocusListener {
		private TextField description;
		private TextField itemCode;
		
		public CodeListener(TextField ic, TextField descrip) {
			description = descrip;
			itemCode = ic;
		}

		@Override
		public void focusGained(FocusEvent e) {
			// do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (itemCode.getText().length() > 0) {
				String descrip = DataSaver.getDescriptionFromCSV(itemCode.getText(), ITEM_DATA_CSV_PATH);
				description.setText(descrip);					
			}		
		}
	}
	
	private class AmountListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			updateTotal();
		}
	}
	
	private class PriceListener implements FocusListener {
		private TextField amount;
		private TextField quantity;
		private TextField price;
		
		public PriceListener(TextField prc, TextField qty, TextField amt) {
			amount = amt;
			quantity = qty;
			price = prc;
		}

		@Override
		public void focusGained(FocusEvent e) {
			// do nothing
		}

		@Override
		public void focusLost(FocusEvent e) {
			String priceText = price.getText();
			String qtyText = quantity.getText();
			if (priceText.length() > 0 && qtyText.length() > 0) {
				amount.setText(Integer.toString(
						Integer.parseInt(qtyText) * Integer.parseInt(priceText)));
			}		
			else {
				amount.setText("");
			}
			updateTotal();
		}
	}
	
	private void clearForm() {
		date.setText("");
		company.setText("");
		purchaseOrder.setText("");
		totalLabel.setText("$0");
		shipVia.setText("");
		itemPanel.removeAll();
		amounts.removeAll(amounts);
		for (int i = 0; i < NUM_ITEMS; i++) {
			addItemRow();
		}
	}
	
	private class SaveOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList<LabelableItem> items = new ArrayList<LabelableItem>();
			Component[] itemRows = itemPanel.getComponents();
			for (int i = 0; i < itemRows.length; i++) {
				JPanel row = (JPanel) itemRows[i];
				TextField amount = (TextField) row.getComponents()[4];
				if (amount.getText().length() > 0) {
					items.add(getItemFromRow(row));
				}
			}
			Order newOrder = new Order(items, purchaseOrder.getText(), shipVia.getText(), DateImp.parseDate(date.getText()));
			newOrder.printItems();
			clearForm();
			orders.add(newOrder);			
		}
		
		private Item getItemFromRow(JPanel row) { 
			Component[] rowData = row.getComponents();
			ArrayList<String> itemData = DataSaver.getItemData(((TextField)rowData[1]).getText(), ITEM_DATA_CSV_PATH);
			return new RDFItem(company.getText(), itemData.get(1), itemData.get(2), itemData.get(0), 
					DateImp.parseDate(date.getText()), Integer.parseInt(((TextField)rowData[0]).getText()),
					Float.parseFloat(((TextField)rowData[3]).getText()),
					((TextField)rowData[1]).getText());
		}
		
	}
	
	private void updateTotal() {
		int total = 0;
		for (int a = 0; a < amounts.size(); a++) {
			String amt = amounts.get(a).getText();
			if (amt.length() > 0) {
				total += Integer.parseInt(amt);	
			}
		}
		totalLabel.setText("$" + Integer.toString(total));
	}
	
	private void addItemRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		TextField quantity = new TextField();
		TextField code = new TextField();
		TextField description = new TextField();
		TextField price = new TextField();
		TextField amount = new TextField();
		Utilities.setMinMax(quantity, QTY_SIZE);
		Utilities.setMinMax(code, CODE_SIZE);
		Utilities.setMinMax(description, DESCRIP_SIZE);
		Utilities.setMinMax(price, PRICE_SIZE);
		Utilities.setMinMax(amount, AMOUNT_SIZE);
		code.addFocusListener(new CodeListener(code, description));
		price.addFocusListener(new PriceListener(price, quantity, amount));
		quantity.addFocusListener(new PriceListener(price, quantity, amount));
		amount.addFocusListener(new AmountListener());
		amounts.add(amount);
		panel.add(quantity);    
		panel.add(code);    
		panel.add(description);    
		panel.add(price);    
		panel.add(amount);    
		panel.setPreferredSize(new Dimension(75,5));
		itemPanel.add(panel);
	}
	
	private void addItemHeaders() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		Label qty = new Label("Qty"); 
		Utilities.setMinMax(qty, QTY_SIZE);
		Label code = new Label("Code");
		Utilities.setMinMax(code, CODE_SIZE);
		Label descrip = new Label("Description");
		Utilities.setMinMax(descrip, DESCRIP_SIZE);
		Label price = new Label("Price");
		Utilities.setMinMax(price, PRICE_SIZE);
		Label amt = new Label("Amount");
		Utilities.setMinMax(amt, AMOUNT_SIZE);
		panel.add(qty);
		panel.add(code);
		panel.add(descrip);
		panel.add(price);
		panel.add(amt);
		mainPanel.add(panel);
	}
	
	private void addTotal() {
		addLabelComponentPair("Total: ", totalLabel);
	}
	
	private void addSaveButton(ActionListener saveListener) {
		JButton saveButton = new JButton("Save Order");
		saveButton.addActionListener(saveListener);
		saveButton.addActionListener(new SaveOrderListener());
		Utilities.setMinMax(saveButton, BUTTON_SIZE);
		mainPanel.add(saveButton);
	}
}
