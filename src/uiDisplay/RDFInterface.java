package uiDisplay;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.DataClient;
import localBackup.LocalFileBackup;
import main.AppListener;
import main.AppListenerMessage;
import main.AppState;
import main.Order;
import uiLogic.HomeFunction;
import uiLogic.SideFunction;
import uiLogic.UserInterface;
import uiSubcomponents.HPanel;
import uiSubcomponents.VPanel;

public class RDFInterface implements UserInterface, AppListener {
	// Application variables
	private ArrayList<Order> orders = new ArrayList<Order>();
	private final LocalFileBackup fb;
	
	// Display variables
	private JFrame homeFrame = new JFrame("Label Program");
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	private final Dimension FUNCTION_SIZE = new Dimension(240,700);
	private JPanel mainPanel;
	private JPanel functionPanel;
	private JPanel homePanel;
	private JScrollPane homeScrollPane;
	private HomeFunction homeFunction;
	
	public RDFInterface() {
		fb = AppState.getFileBackup();
		DataClient dc = AppState.getDataClient();
		if (orders.size() == 0) {
			orders = fb.getOrders();
			AppState.setOrders(orders);
		}

		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new HPanel();
		homePanel = new VPanel();
		functionPanel = new VPanel();
		
		homeScrollPane = new JScrollPane(homePanel);
		JScrollPane funcScrollPane = new JScrollPane(functionPanel);
		Utilities.setMinMax(funcScrollPane, FUNCTION_SIZE);
		mainPanel.add(homeScrollPane);
		mainPanel.add(funcScrollPane);
		
		homeFrame.add(mainPanel);
		homeFrame.setSize(WINDOW_SIZE);
	}
	
	private void updateHomePanel() {
		mainPanel.remove(0);
		homeScrollPane = new JScrollPane(homeFunction.getMainContent());
		mainPanel.add(homeScrollPane, 0);
	}
	
	@Override
	public void showInterface() {
		homeFrame.setVisible(true);
	}
	
	@Override
	public void addHomeFunction(HomeFunction hf) {
		homeFunction = hf;
		AppState.addListener(hf);
		homePanel.add(hf.getMainContent());
		Utilities.localHPack(homePanel);
	}
	
	public void addBreakBetweenFunctions() {
		functionPanel.add(Box.createRigidArea(new Dimension(1,30)));
	}
	
	@Override
	public void addFunction(SideFunction af, String btnName, String iconPath) {
		JButton funcBtn = new JButton(btnName);
		
		if (!iconPath.equals("")) {
			ImageIcon imic = new ImageIcon(iconPath);
			Image img = imic.getImage() ;  
			Image newimg = img.getScaledInstance( 20,20,  java.awt.Image.SCALE_SMOOTH ) ;  
			imic = new ImageIcon( newimg );
			funcBtn.setIcon(imic);
		}
		
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
			func.executeFunction();
		}
	}

	@Override
	public void sendMessage(AppListenerMessage m) {
		switch (m) {
			case SET_ORDERS:
				updateHomePanel();
				mainPanel.validate();
				homePanel.validate();
				homePanel.getComponent(0).validate();
				homeScrollPane.validate();
				break;
			case ADD_ORDER:
				updateHomePanel();
				mainPanel.validate();
				homePanel.validate();
				homePanel.getComponent(0).validate();
				homeScrollPane.validate();
				break;
			case REMOVE_ORDER:
				mainPanel.validate();
				homePanel.validate();
				homePanel.getComponent(0).validate();
				homeScrollPane.validate();
				break;
			default:
				homeFrame.validate();
				break;
		}
	}
 }
