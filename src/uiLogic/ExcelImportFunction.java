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
			    System.out.println("orders: " + orders.size());
			    orders.get(0).printOrder();
			    AppState.addOrders(orders);
		    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
	}
}
