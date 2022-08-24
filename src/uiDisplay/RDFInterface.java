package uiDisplay;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
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
import uiSubcomponents.HomePanel;
import uiSubcomponents.VPanel;

public class RDFInterface implements UserInterface, AppListener {
	// Application variables
	private ArrayList<Order> orders = new ArrayList<Order>();
	private LocalFileBackup fb = null;
	
	// Display variables
	private JFrame appFrame = new JFrame("Label Program");
	private Dimension appSize = new Dimension(1000,700);
	private final Dimension FUNCTION_SIZE = new Dimension(250,500);
	private Dimension homeSize = new Dimension(760, 700);
	private JPanel mainPanel;
	private JPanel functionPanel;
	private Container homePanel;
	private HomeFunction homeFunction;
	
	public RDFInterface() {
		getOrders();
		setupPanels();
	}
	
	private void getOrders() {
		fb = AppState.getFileBackup();
		if (orders.size() == 0) {
			orders = fb.getOrders();
			AppState.setOrders(orders);
		}
	}
	
	private void setupPanels() {
		appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		appFrame.setSize(appSize);

		mainPanel = new HPanel();
		homePanel = new JPanel();
		functionPanel = new VPanel();
		homePanel.setPreferredSize(homeSize);
		Utilities.setMinMax(functionPanel, FUNCTION_SIZE);

		functionPanel.setAlignmentY(0f);
		mainPanel.add(homePanel);
		mainPanel.add(functionPanel);
			
		appFrame.add(mainPanel);
	}
	
	private void updateHomePanel() {
		mainPanel.remove(0);
		homePanel = homeFunction.getMainContent();
		mainPanel.add(homePanel, 0);
	}
	
	@Override
	public void showInterface() {
		appFrame.setVisible(true);
	}
	
	@Override
	public void addHomeFunction(HomeFunction hf) {
		homeFunction = hf;
		AppState.addListener(hf);
		homePanel.add(hf.getMainContent());
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
				appFrame.validate(); // todo
				break;
			case ADD_ORDER:
				updateHomePanel();
				appFrame.validate(); 
				break;
			case REMOVE_ORDER:
				appFrame.validate(); 
				break;
			default:
				appFrame.validate();
				break;
		}
	}
 }
