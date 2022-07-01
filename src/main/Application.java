package main;
import java.util.ArrayList;

import export.DataSaver;
import export.SocketClient;
import labels.LabelViewerImp;
import printing.PrintManager;
import userInterface.EntryForm;
import userInterface.OrderDisplay;
import userInterface.RDFInterface;
import userInterface.UserInterface;

public class Application {
	private UserInterface ui;
	
	public Application() {
		AppState.initializeAppState(new ArrayList<Order>(), new DataSaver(), new PrintManager(),
				new SocketClient());
		
		ui = new RDFInterface();
		AppState.addListener(ui);
		ui.addHomeFunction(new OrderDisplay());
		ui.addFunction(new LabelViewerImp(), "View/Print Labels");
		ui.addFunction(new EntryForm(), "New Order");
		
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
