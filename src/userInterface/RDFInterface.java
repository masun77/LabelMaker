package userInterface;

import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import export.DataSaver;
import export.FileBackup;
import main.ApplicationState;
import main.Order;

public class RDFInterface implements UserInterface {
	// Application variables
	private ArrayList<Order> orders = new ArrayList<Order>();
	private ApplicationState state;
	private final FileBackup fb;
	
	// Display variables
	private JFrame homeFrame = new JFrame("Label Program");
	private final Dimension WINDOW_SIZE = new Dimension(1000,700);
	JPanel homePanel;
	JPanel functionPanel;
	
	public RDFInterface(ApplicationState s) {
		state = s;
		fb = s.getFileBackup();
		if (orders.size() == 0) {
			orders = fb.readSavedOrders();
			state.setOrders(orders);
			state.notifyListeners();
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
		state.addListener(hf);
		homePanel.add(hf.getMainContent(),0);
		Utilities.localHPack(homePanel);
	}
	
	@Override
	public void addFunction(AppFunction af, String btnName) {
		state.addListener(af);
		JButton funcBtn = new JButton(btnName);
		funcBtn.addActionListener(new FunctionListener(af));
		
		functionPanel.add(funcBtn);
		functionPanel.add(Box.createRigidArea(new Dimension(1,10)));
		Utilities.localVPack(functionPanel);
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
 }
