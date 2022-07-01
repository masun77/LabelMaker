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
		appState = new AppState(new ArrayList<Order>(), new DataSaver());
		appState.setPrinter(new PrintManager(appState));
		appState.setDataClient(new SocketClient(appState));
		
		ui = new RDFInterface(appState);
		ui.addHomeFunction(new OrderDisplay(appState));
		ui.addFunction(new LabelViewerImp(appState), "View/Print Labels");
		ui.addFunction(null, null);
		
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
//private class PrintListener implements ActionListener {
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		ArrayList<LabelableItem> items = getCheckedItems();
//		LabelView lv = new LabelViewerImp();
//		lv.showLabels(items);
//	}
//}
