package userInterface;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EntryForm extends JPanel {
	private TextField date = new TextField(10);
	private TextField company = new TextField(30);
	private TextField purchaseOrder = new TextField(15);
	private TextField shipVia = new TextField(15);
	private final int NUM_ITEMS = 20;
	private JPanel itemPanel = new JPanel();
	private Label total = new Label();

	public EntryForm(ActionListener homeListener, ActionListener saveListener) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		addHomeButton(homeListener);
		addOrderDate();
		addOrderCompany();
		addPurchaseOrder();
		addShipVia();
		addItems();
		addTotal();
		addSaveButton(saveListener);
		
		System.out.println("hmm");
		
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
	
	private void addItemRow() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(new TextField(5));     // Quantity
		panel.add(new TextField(20));    // Code 
		panel.add(new TextField(30));    // Descrip - auto-populate/update
		panel.add(new TextField(10));    // Price
		panel.add(new TextField(10));    // Amount - auto-populate/update
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
		add(total);
		// notify total when amount changed
	}
	
	private void addSaveButton(ActionListener saveListener) {
		JButton saveButton = new JButton("Save Order");
		saveButton.addActionListener(saveListener);
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
