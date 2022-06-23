package userInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import export.DataSaver;
import export.SocketClient;
import main.Order;

public class RDFInterface implements UserInterface {
	private ArrayList<Order> orders = new ArrayList<Order>();
	private JFrame orderDisplay = new JFrame("Label Program");
	private JFrame orderEntry;
	private OrderDisplay homePanel;
	private final String SAVE_FILE_NAME = "resources/Orders1.csv";
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	
	public RDFInterface(ArrayList<Order> ords) {
		orders.addAll(ords); 
	}
	
	public RDFInterface(String csvname) {
		orders = DataSaver.readOrdersFromCSV(csvname);
	}
	
	public RDFInterface() {
		if (orders.size() == 0) {
			orders = DataSaver.readOrdersFromCSV(SAVE_FILE_NAME);
		}
	}
	
	@Override
	public void setOrders(ArrayList<Order> ords) {
		orders = ords;
	}
	
	@Override
	public void showInterface() {
		initializeOrderDisplay();	
		orderDisplay.setVisible(true);
	}
	
	private void initializeOrderDisplay() {
		orderDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homePanel = new OrderDisplay(orders, new OrderEntryCreator(), SAVE_FILE_NAME, this);
		JScrollPane scrollPane = new JScrollPane(homePanel);
		orderDisplay.add(scrollPane);
		orderDisplay.setSize(WINDOW_SIZE);
	}
	
	public void createOrderEntry() {
		orderEntry = new EntryForm(new Refreshor(), orders);
		orderEntry.setSize(WINDOW_SIZE);
		orderEntry.setVisible(true);
	}

	public void refresh() {
		orderEntry.dispose();
		homePanel.setOrders(orders);
		homePanel.refresh();
		DataSaver.writeOrdersToCSV(orders, SAVE_FILE_NAME);
	}
	
	private class OrderEntryCreator implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			createOrderEntry();
		}
	}
	
	private class Refreshor implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			orders = ((EntryForm)orderEntry).getOrders();
			refresh();
		}
	}
 }
