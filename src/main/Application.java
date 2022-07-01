package main;
import java.util.ArrayList;

import export.DataSaver;
import export.SocketClient;
import labels.LabelViewerImp;
import printing.PrintManager;
import userInterface.OrderDisplay;
import userInterface.RDFInterface;
import userInterface.UserInterface;

public class Application {
	private UserInterface ui;
	private ApplicationState appState;
	
	public Application() {
		appState = new AppState(new ArrayList<Order>(), new SocketClient(), new DataSaver());
		PrintManager pm = new PrintManager(appState);
		appState.setPrinter(pm);
		ui = new RDFInterface(appState);
		ui.addHomeFunction(new OrderDisplay(appState));
		ui.addFunction(new LabelViewerImp(appState), "View/Print Labels");
		
//			JButton enterOrderButton = new JButton("Add new order");
//			enterOrderButton.addActionListener(entryListener);
//			Utilities.setMinMax(enterOrderButton, BTN_SIZE);
//			buttonPanel.add(enterOrderButton);		
//			buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
//			JButton updateButton = new JButton("Send Orders to Server");
//			updateButton.addActionListener(new UpdateListener());
//			Utilities.setMinMax(updateButton, BTN_SIZE);
//			buttonPanel.add(updateButton);
//			buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
//			
//			JButton getOrders = new JButton("Get Orders from Server");
//			getOrders.addActionListener(new GetOrdersListener());
//			Utilities.setMinMax(getOrders, BTN_SIZE);
//			buttonPanel.add(getOrders);
//			buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
//			
//			JButton setServerIP = new JButton("Set Server IP");
//			setServerIP.addActionListener(new SetIPListener());
//			Utilities.setMinMax(setServerIP, BTN_SIZE);
//			buttonPanel.add(setServerIP);
	}
	
	public void run() {		
		ui.showInterface();		
	}
}

//private class UpdateListener implements ActionListener {	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		sock.sendOrders(orders);
//	}	
//}
//
//private class GetOrdersListener implements ActionListener {	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		ArrayList<Order> returned = sock.getOrders();
//		if (returned.size() > 0) {
//			orders = returned;
//		}
//		refresh();
//	}	
//}
//
//private class SetIPListener implements ActionListener {	
//	TextField ipAddr = new TextField(sock.getIPAddress());
//	Label ipLabel = new Label("Server ip address: ");
//	JFrame frame;
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		JButton button = new JButton("Save ip address");
//		frame = new JFrame();
//		JPanel panel = new JPanel();
//		ipLabel.setPreferredSize(new Dimension(300,20));
//		ipAddr.setPreferredSize(new Dimension(200,20));
//		panel.add(ipLabel);
//		panel.add(ipAddr);
//		button.setSize(new Dimension(100,20));
//		button.addActionListener(new IPListener());
//		panel.add(button);
//		frame.add(panel);
//		frame.setSize(new Dimension(600,200));
//		frame.setVisible(true);
//	}	
//	
//	private class IPListener implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			sock.setIPAddress(ipAddr.getText());
//			frame.dispose();
//		}
//	}
//}
//
//private class PrintListener implements ActionListener {
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		ArrayList<LabelableItem> items = getCheckedItems();
//		LabelView lv = new LabelViewerImp();
//		lv.showLabels(items);
//	}
//}
