package uiLogic;

import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import database.DataImporter;
import database.ExcelInvoiceReader;
import main.AppState;
import main.Order;

public class ExcelImportFunction implements SideFunction {

	@Override
	public void resetOrders() {
		// do nothing
	}

	@Override
	public void addOrder(Order o) {
		// do nothing
	}

	@Override
	public void removeOrder(Order o) {
		// do nothing
	}

	@Override
	public void executeFunction() {
		DataImporter di = new ExcelInvoiceReader();
		
		try {
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Excel files", "xlsx");
		    chooser.setFileFilter(filter);
		    chooser.setApproveButtonText("Upload invoices from this file");
		    int returnVal = chooser.showOpenDialog(new JFrame());
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    di.setResourcePath(chooser.getSelectedFile().getAbsolutePath());
			    ArrayList<Order> orders = di.readInvoices();
			    
			    ArrayList<Order> currOrders = AppState.getOrders();
			    currOrders.addAll(orders);
			    AppState.setOrders(currOrders);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
