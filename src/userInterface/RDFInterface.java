package userInterface;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import export.FileBackup;
import main.AppState;
import main.Order;
import userInterface.graphicComponents.HPanel;
import userInterface.graphicComponents.VPanel;

public class RDFInterface implements UserInterface {
	// Application variables
	private ArrayList<Order> orders = new ArrayList<Order>();
	private final FileBackup fb;
	
	// Display variables
	private JFrame homeFrame = new JFrame("Label Program");
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	JPanel homePanel;
	JPanel functionPanel;
	AppFunction homeFunction;
	
	public RDFInterface() {
		fb = AppState.getFileBackup();
		if (orders.size() == 0) {
			orders = fb.readSavedOrders();
			AppState.setOrders(orders);
			AppState.notifyListeners();
		}

		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		functionPanel = new VPanel();
		homePanel = new HPanel();
		homePanel.add(functionPanel);
		Utilities.localHPack(homePanel);
		
		JScrollPane scrollPane = new JScrollPane(homePanel);
		homeFrame.add(scrollPane);
		homeFrame.setSize(WINDOW_SIZE);
	}
	
	@Override
	public void showInterface() {
		homeFrame.setVisible(true);
	}
	
	@Override
	public void addHomeFunction(AppFunction hf) {
		homeFunction = hf;
		AppState.addListener(hf);
		homePanel.add(hf.getMainContent(),0);
		Utilities.localHPack(homePanel);
	}
	
	@Override
	public void addFunction(AppFunction af, String btnName) {
		AppState.addListener(af);
		JButton funcBtn = new JButton(btnName);
		funcBtn.addActionListener(new FunctionListener(af));
		
		functionPanel.add(funcBtn);
		functionPanel.add(Box.createRigidArea(new Dimension(1,10)));
		Utilities.localVPack(functionPanel);   // todo this is not currently in a scrollpane...
	}
	
	private class FunctionListener implements ActionListener {
		AppFunction func;
		
		public FunctionListener(AppFunction af) {
			func = af;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			func.showFunction();
		}
	}

	@Override
	public void refresh() {
		homePanel.remove(0);
		homeFunction.refresh();
		homePanel.add(homeFunction.getMainContent(),0);
		Utilities.localHPack(homePanel);
	}
 }
