package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import labels.DateImp;
import labels.LabelableItem;
import localBackup.LocalFileBackup;
import main.AppState;
import main.Item;
import main.Order;
import main.RDFItem;
import userInterface.graphicComponents.AmountListener;
import userInterface.graphicComponents.CodeListener;
import userInterface.graphicComponents.HPanel;
import userInterface.graphicComponents.PriceListener;
import userInterface.graphicComponents.SaveOrderListener;
import userInterface.graphicComponents.VPanel;

public class EntryForm implements SideFunction {
	private ArrayList<Order> orders;
	private LocalFileBackup fb;
	
	// Display variables
	private JFrame frame = new JFrame();
	private JPanel mainPanel = new VPanel();
	private JPanel itemPanel = new VPanel();
	private JLabel totalLabel = new JLabel("$0");
	private TextField date = new TextField(10);
	private TextField company = new TextField(30);
	private TextField purchaseOrder = new TextField(15);
	private TextField shipVia = new TextField(15);
	private ArrayList<TextField> amounts = new ArrayList<TextField>();
	private boolean editingOrder = false;
	private Order editOrder = null;
	
	// Constants
	private final int NUM_ITEMS = 25;
	private final int ROW_HEIGHT = 20;
	private final Dimension LABEL_SIZE = new Dimension(120,ROW_HEIGHT);
	private final Dimension QTY_SIZE = new Dimension(30,ROW_HEIGHT);
	private final Dimension CODE_SIZE = new Dimension(80,ROW_HEIGHT);
	private final Dimension DESCRIP_SIZE = new Dimension(200,ROW_HEIGHT);
	private final Dimension PRICE_SIZE = new Dimension(40,ROW_HEIGHT);
	private final Dimension AMOUNT_SIZE = new Dimension(60,ROW_HEIGHT);
	private final Dimension BUTTON_SIZE = new Dimension(150,30);

	public EntryForm() {
		orders = AppState.getOrders();
		fb = AppState.getFileBackup();
		frame.setSize(new Dimension(700,700));

		initialize();
	}
	
	public void setEditingOrder(boolean b, Order order) {
		editingOrder = b;
		editOrder = order;
		date.setText(order.getItems().get(0).getPackDate().getDateMMDDYYYY());
		company.setText(order.getCompany());
		purchaseOrder.setText(order.getPONum());
		shipVia.setText(order.getShipVia());
		ArrayList<LabelableItem> items = order.getItems();
		for (int i = 0; i < items.size(); i++) {
			LabelableItem currItem = items.get(i);
			HPanel row =(HPanel) itemPanel.getComponent(i);
			float qty = currItem.getQuantity();
			float price = currItem.getPrice();
			((TextField) row.getComponent(0)).setText(Float.toString(qty));
			((TextField) row.getComponent(1)).setText(currItem.getItemCode());
			((TextField) row.getComponent(2)).setText(fb.getItemDescription(currItem.getItemCode()));
			((TextField) row.getComponent(3)).setText(Float.toString(price));
			amounts.get(i).setText(Float.toString(price * qty));
		}
		updateTotal();			
	}

	@Override
	public void showFunction() {
		frame.setVisible(true);
	}
	
	private void initialize() {		
		addOrderDate();
		addOrderCompany();
		addPurchaseOrder();
		addShipVia();
		addItems();
		addTotal();
		addSaveButton();
		
		Utilities.localVPack(mainPanel);
		JScrollPane scrollPane = new JScrollPane(mainPanel);
		frame.add(scrollPane);
	}
	
	private void addOrderDate() {
		addLabelComponentPair("Date (mm/dd/yy): ", date);
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
		
	private void addLabelComponentPair(String label, Component tf) {
		JPanel panel = new HPanel();
		JLabel labelComp = new JLabel(label);
		Utilities.setMinMax(labelComp, LABEL_SIZE);
		Utilities.setMinMax(tf, LABEL_SIZE);
		panel.add(labelComp);
		panel.add(tf);
		mainPanel.add(panel);
	}
	
	private void addItems() {
		addItemHeaders();
		for (int i = 0; i < NUM_ITEMS; i++) {
			addItemRow();
		}
		Utilities.localVPack(itemPanel);
		mainPanel.add(itemPanel);
	}
	
	private void addItemHeaders() {
		JPanel panel = new HPanel();
		addLabelOfSizeToPanel("Qty", QTY_SIZE, panel);
		addLabelOfSizeToPanel("Code", CODE_SIZE, panel);
		addLabelOfSizeToPanel("Description", DESCRIP_SIZE, panel);
		addLabelOfSizeToPanel("Price", PRICE_SIZE, panel);
		addLabelOfSizeToPanel("Amount", AMOUNT_SIZE, panel);
		Utilities.localHPack(panel);
		mainPanel.add(panel);
	}
	
	private void addLabelOfSizeToPanel(String text, Dimension size, Container panel) {
		JLabel l = new JLabel(text);
		Utilities.setMinMax(l, size);
		panel.add(l);
	}
	
	private void addItemRow() {
		JPanel panel = new HPanel();
		panel.setPreferredSize(new Dimension(75,5));

		TextField quantity = addTextFieldOfSizeToPanel("", QTY_SIZE, panel);
		TextField code = addTextFieldOfSizeToPanel("", CODE_SIZE, panel);
		TextField description = addTextFieldOfSizeToPanel("", DESCRIP_SIZE, panel);
		TextField price = addTextFieldOfSizeToPanel("", PRICE_SIZE, panel);
		TextField amount = addTextFieldOfSizeToPanel("", AMOUNT_SIZE, panel);
		code.addFocusListener(new CodeListener(code, description));
		price.addFocusListener(new PriceListener(price, quantity, amount, this));
		amount.addFocusListener(new AmountListener(this));
		quantity.addFocusListener(new PriceListener(price, quantity, amount, this));
		amounts.add(amount);
		itemPanel.add(panel);
	}
	
	private TextField addTextFieldOfSizeToPanel(String text, Dimension size, Container panel) {
		TextField tf = new TextField(text);
		Utilities.setMinMax(tf, size);
		panel.add(tf);
		return tf;
	}
	
	private void addTotal() {
		addLabelComponentPair("Total: ", totalLabel);
	}
	
	private void addSaveButton() {
		JButton saveButton = new JButton("Save Order");
		saveButton.addActionListener(new SaveOrderListener(this)); 
		Utilities.setMinMax(saveButton, BUTTON_SIZE);
		mainPanel.add(saveButton);
	}
	
	public void updateTotal() {
		float total = 0;
		for (int a = 0; a < amounts.size(); a++) {
			String amt = amounts.get(a).getText();
			if (amt.length() > 0) {
				total += Float.parseFloat(amt);	
			}
		}
		totalLabel.setText("$" + Float.toString(total));
	}
	
	private void resetFields() {
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	        	 company.setText("");
	        	 shipVia.setText("");
	        	 purchaseOrder.setText("");
	        	 date.setText("");
	        	 resetItemPanel();
		     }
		});
	}
	
	private void resetItemPanel() {
		for (int i = 0; i < itemPanel.getComponentCount(); i++) {
			JPanel panel = (JPanel) itemPanel.getComponent(i);
			for (int j = 0; j < panel.getComponentCount(); j++) {
				((TextField) panel.getComponent(j)).setText("");
			}
		}
	}
	
	public void onSave() {
		frame.setVisible(false);
		ArrayList<LabelableItem> items = new ArrayList<LabelableItem>();
		Component[] itemRows = itemPanel.getComponents();
		for (int i = 0; i < itemRows.length; i++) {
			JPanel row = (JPanel) itemRows[i];
			TextField amount = (TextField) row.getComponents()[4];
			if (amount.getText().length() > 0) {
				items.add(getItemFromRow(row));
			}
		}
		Order newOrder = new Order(company.getText(), items, purchaseOrder.getText(), 
				shipVia.getText());
		if (editingOrder) {
			AppState.removeOrder(editOrder);
		}
		AppState.addOrder(newOrder);
		orders = AppState.getOrders();
		AppState.getFileBackup().saveOrders(orders);
		resetFields();
	}
	
	private Item getItemFromRow(JPanel row) { 
		Component[] rowData = row.getComponents();
		ArrayList<String> itemData = fb.getItemData(((TextField)rowData[1]).getText());
		return new RDFItem(company.getText(), itemData.get(1), itemData.get(2), itemData.get(0), 
				DateImp.parseDate(date.getText()), Float.parseFloat(((TextField)rowData[0]).getText()),
				Float.parseFloat(((TextField)rowData[3]).getText()),
				((TextField)rowData[1]).getText());
	}

	@Override
	public void resetOrders() {
		// do nothing
	}

	@Override
	public void addOrder(Order o) {
		// do nothing
	}

	@Override
	public void removeOrder(Order o) {
		// do nothing
	} 
}
