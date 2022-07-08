package userInterface;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import export.FileBackup;
import labels.DateImp;
import labels.LabelableItem;
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
	private FileBackup fb;
	
	// Display variables
	private JFrame frame = new JFrame();
	private JPanel mainPanel = new VPanel();
	private JPanel itemPanel = new VPanel();
	private Label totalLabel = new Label("$0");
	private TextField date = new TextField(10);
	private TextField company = new TextField(30);
	private TextField purchaseOrder = new TextField(15);
	private TextField shipVia = new TextField(15);
	private ArrayList<TextField> amounts = new ArrayList<TextField>();
	
	// Constants
	private final int NUM_ITEMS = 20;
	private final Dimension LABEL_SIZE = new Dimension(100,15);
	private final Dimension QTY_SIZE = new Dimension(30,15);
	private final Dimension CODE_SIZE = new Dimension(80,15);
	private final Dimension DESCRIP_SIZE = new Dimension(200,15);
	private final Dimension PRICE_SIZE = new Dimension(40,15);
	private final Dimension AMOUNT_SIZE = new Dimension(60,15);
	private final Dimension BUTTON_SIZE = new Dimension(150,50);

	public EntryForm() {
		orders = AppState.getOrders();
		fb = AppState.getFileBackup();
		frame.setSize(new Dimension(700,700));

		initialize();
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
		
	private void addLabelComponentPair(String label, Component tf) {
		JPanel panel = new HPanel();
		Label labelComp = new Label(label);
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
		Label l = new Label(text);
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
				shipVia.getText(), DateImp.parseDate(date.getText()));
		AppState.addOrder(newOrder);
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
	public void resetOrders(ArrayList<Order> ords) {
		orders = ords;
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
	     		orders = AppState.getOrders();
	    		fb = AppState.getFileBackup();
	    		initialize();
		     }
		});
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
