package userInterface;

import java.awt.Component;
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
import javax.swing.JPanel;

import labels.Date;
import labels.DateImp;
import main.Item;
import main.Order;
import main.RDFItem;

public class EntryForm extends JPanel {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ActionListener homeListener;
	private ActionListener saveListener;
	private TextField date = new TextField(10);
	private TextField company = new TextField(30);
	private TextField purchaseOrder = new TextField(15);
	private TextField shipVia = new TextField(15);
	private final int NUM_ITEMS = 20;
	private JPanel itemPanel = new JPanel();
	private Label totalLabel = new Label();
	private ArrayList<TextField> amounts = new ArrayList<TextField>();

	public EntryForm(ActionListener homeLstn, ActionListener saveLstn, ArrayList<Order> ords) {
		orders = ords;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		homeListener = homeLstn;
		saveListener = saveLstn;
		initialize();
	}
	
	private void initialize() {
		addHomeButton(homeListener);
		addOrderDate();
		addOrderCompany();
		addPurchaseOrder();
		addShipVia();
		addItems();
		addTotal();
		addSaveButton(saveListener);
		
		DisplayUtilities.localPack(this);
	}
	
	private void addLabelTextFieldPair(String label, TextField tf) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(new Label(label));
		panel.add(tf);
		tf.setMaximumSize(new Dimension(30,15));
		add(panel);
	}
	
	private void addOrderDate() {
		addLabelTextFieldPair("Date: ", date);
	}
	
	private void addOrderCompany() {
		addLabelTextFieldPair("Company: ", company);
	}
	
	private void addPurchaseOrder() {
		addLabelTextFieldPair("PO num: ", purchaseOrder);
	}
	
	private void addShipVia() {
		addLabelTextFieldPair("Ship Via: ", shipVia);
	}
	
	private void addItems() {
		addItemHeaders();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		for (int i = 0; i < NUM_ITEMS; i++) {
			addItemRow();
		}
		add(itemPanel);
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
				description.setText("Description!");					
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
	
	private class SaveOrderListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Save data to new order, if any data entered.");
			ArrayList<Item> items = new ArrayList<Item>();
			Component[] itemRows = itemPanel.getComponents();
			for (int i = 0; i < itemRows.length; i++) {
				JPanel row = (JPanel) itemRows[i];
				TextField amount = (TextField) row.getComponents()[4];
				if (amount.getText().length() > 0) {
					items.add(getItemFromRow(row));
				}
			}
			Order newOrder = new Order(items);
			newOrder.printItems();
			clearForm();
			orders.add(newOrder);			
		}
		
		private Item getItemFromRow(JPanel row) {   // todo: where do the prices go? and amounts? and PO num and shipvia? 
			return new RDFItem(company.getText(), "Name__", "unit__", "GTIN__", 
					DateImp.parseDate(date.getText()), Integer.parseInt(((TextField)row.getComponents()[0]).getText()));
		}
		
		private void clearForm() {
			removeAll();
			initialize();
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
		totalLabel.setText(Integer.toString(total));
	}
	
	private void addItemRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		TextField quantity = new TextField(5);
		TextField code = new TextField(20);
		TextField description = new TextField(30);
		TextField price = new TextField(10);
		TextField amount = new TextField(10);
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
		panel.add(new Label("Quantity"));
		panel.add(new Label("Item Code"));
		panel.add(new Label("Description"));
		panel.add(new Label("Price"));
		panel.add(new Label("Amount"));
		add(panel);
	}
	
	private void addTotal() {
		add(totalLabel);
	}
	
	private void addSaveButton(ActionListener saveListener) {
		JButton saveButton = new JButton("Save Order");
		saveButton.addActionListener(saveListener);
		saveButton.addActionListener(new SaveOrderListener());
		saveButton.setMinimumSize(new Dimension(50,10));
		add(saveButton);
	}
	
	private void addHomeButton(ActionListener homeButton) {
		JButton returnHome = new JButton("Return to order display");
		returnHome.addActionListener(homeButton);
		returnHome.setMinimumSize(new Dimension(100,50));
		add(returnHome);
	}
}
