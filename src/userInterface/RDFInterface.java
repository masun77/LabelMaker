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

import localBackup.LocalFileBackup;
import main.AppState;
import main.Order;
import userInterface.graphicComponents.HPanel;
import userInterface.graphicComponents.VPanel;

public class RDFInterface implements UserInterface, AppListener {
	// Application variables
	private ArrayList<Order> orders = new ArrayList<Order>();
	private final LocalFileBackup fb;
	
	// Display variables
	private JFrame homeFrame = new JFrame("Label Program");
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	private final Dimension FUNCTION_SIZE = new Dimension(300,700);
	private JPanel mainPanel;
	private JPanel functionPanel;
	private JPanel homePanel;
	
	public RDFInterface() {
		AppState.addLastListener(this);
		fb = AppState.getFileBackup();
		if (orders.size() == 0) {
			orders = fb.getOrders();
			AppState.setOrders(orders);
		}

		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new HPanel();
		homePanel = new VPanel();
		functionPanel = new VPanel();
		Utilities.setMinMax(functionPanel, FUNCTION_SIZE);
		
		JScrollPane scrollPane = new JScrollPane(homePanel);
		JScrollPane funcScrollPane = new JScrollPane(functionPanel);
		mainPanel.add(scrollPane);
		mainPanel.add(funcScrollPane);
		
		homeFrame.add(mainPanel);
		homeFrame.setSize(WINDOW_SIZE);
	}
	
	@Override
	public void showInterface() {
		homeFrame.setVisible(true);
	}
	
	@Override
	public void addHomeFunction(HomeFunction hf) {
		AppState.addListener(hf);
		homePanel.add(hf.getMainContent());
		Utilities.localHPack(homePanel);
	}
	
	@Override
	public void addFunction(SideFunction af, String btnName) {
		AppState.addListener(af);
		JButton funcBtn = new JButton(btnName);
		funcBtn.addActionListener(new FunctionListener(af));
		
		functionPanel.add(funcBtn);
		functionPanel.add(Box.createRigidArea(new Dimension(1,10)));
		Utilities.localVPack(functionPanel);  
	}
	
	private class FunctionListener implements ActionListener {
		SideFunction func;
		
		public FunctionListener(SideFunction af) {
			func = af;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			func.showFunction();
		}
	}

	@Override
	public void resetOrders() {
		homeFrame.validate();
	}

	@Override
	public void addOrder(Order o) {
		homeFrame.validate();
	}

	@Override
	public void removeOrder(Order o) {
		homeFrame.validate();
	}
 }
