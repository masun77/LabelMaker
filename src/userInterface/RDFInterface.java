package userInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import export.DataSaver;
import export.SocketClient;
import main.Order;

public class RDFInterface implements UserInterface {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private JFrame orderDisplay = new JFrame("Label Program");
	private JFrame orderEntry = new JFrame("Enter New Order");
	private OrderDisplay homePanel;
	private final String SAVE_FILE_NAME = "resources/Orders1.csv";
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	
	public RDFInterface(ArrayList<Order> ords) {
		orders.addAll(ords); 
		showInterface();
	}
	
	public RDFInterface(String csvname) {
		orders = DataSaver.readOrdersFromCSV(csvname);
		showInterface();
	}
	
	public RDFInterface() {
		orders = new SocketClient().getOrders();
		if (orders.size() == 0) {
			orders = DataSaver.readOrdersFromCSV(SAVE_FILE_NAME);
		}
		showInterface();
	}
	
	@Override
	public void showInterface() {
		
		initializeOrderDisplay();		
		initializeEntryForm();
		
		orderDisplay.setVisible(true);
		orderEntry.setVisible(false);
	}
	
	private void initializeOrderDisplay() {
		orderDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homePanel = new OrderDisplay(orders, new EntryButtonListener(), SAVE_FILE_NAME);
		JScrollPane scrollPane = new JScrollPane(homePanel);
		orderDisplay.add(scrollPane);
		orderDisplay.setSize(WINDOW_SIZE);
	}
	
	private void initializeEntryForm() {
		JPanel entryForm = new EntryForm(new HomeButtonListener(), new OrderSaveButtonListener(), orders);
		JScrollPane scrollPane = new JScrollPane(entryForm);
		orderEntry.add(scrollPane);
		orderEntry.setSize(WINDOW_SIZE);
		orderEntry.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class EntryButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orderDisplay.setVisible(false);
			orderEntry.setVisible(true);
		}
	}
	
	private class HomeButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orderEntry.setVisible(false);
			orderDisplay.setVisible(true);
		}
	}
	
	private class OrderSaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orderEntry.setVisible(false);
			orderDisplay.setVisible(true);
			DataSaver.writeOrdersToCSV(orders, SAVE_FILE_NAME);
			homePanel.refresh();
		}
	}
}
