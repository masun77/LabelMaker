package userInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import export.DataSaver;
import export.FileBackup;
import main.ApplicationState;
import main.Order;

public class RDFInterface implements UserInterface {
	// Application variables
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ApplicationState state;
	
	// Display variables
	private JFrame orderDisplay = new JFrame("Label Program");
	private JFrame orderEntry;
	private OrderDisplay homePanel;
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	private final FileBackup fb;
	
	public RDFInterface(ApplicationState s) {
		state = s;
		fb = s.getFileBackup();
		if (orders.size() == 0) {
			orders = fb.readSavedOrders();
		}
	}
	
	@Override
	public void showInterface() {
		initializeOrderDisplay();	
		orderDisplay.setVisible(true);
	}
	
	private void initializeOrderDisplay() {
		orderDisplay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homePanel = new OrderDisplay(orders, new OrderEntryCreator(), this);
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
		fb.saveOrders(orders);
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
