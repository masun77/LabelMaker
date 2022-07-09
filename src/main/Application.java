package main;
import java.util.ArrayList;

import database.RefreshFunction;
import database.SocketClient;
import localBackup.DataSaver;
import printing.PrintManager;
import userInterface.EntryForm;
import userInterface.LabelViewerImp;
import userInterface.OrderDisplay;
import userInterface.RDFInterface;
import userInterface.UserInterface;

public class Application {
	private UserInterface ui;
	
	public Application() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		
		ui = new RDFInterface();
		ui.addHomeFunction(new OrderDisplay());
		ui.addFunction(new LabelViewerImp(), "View/Print Labels");
		ui.addFunction(new EntryForm(), "New Order");
		ui.addFunction(new RefreshFunction(), "Pull orders from server");
	}
	
	public void run() {		
		ui.showInterface();		
	}
}


//JButton getOrders = new JButton("Get Orders from Server");
//getOrders.addActionListener(new GetOrdersListener());
//Utilities.setMinMax(getOrders, BTN_SIZE);
//buttonPanel.add(getOrders);
//buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));

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
