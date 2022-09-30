package freshStart;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

public class MainFrame {
	private OrderDisplay display;
	private LabelFormat labelFormat;
	private JFrame frame;
	private ExcelFormatGetter efg = new ExcelFormatGetter();
	private ArrayList<Order> orders;
	private ArrayList<Integer> invoiceNumbers = new ArrayList<>(); 
	private ExcelReader reader = new ExcelReader();
	
	public MainFrame() {
		efg.readExcelFormats();

		orders = reader.getOrdersFromFile("src/freshStart/7.27.xlsx", 
				efg.getFormats().get(0)); // todo initialize from backup
		addOrdersToInvoiceNumbers();
		display = new OrderDisplay();
		
		labelFormat = new LabelFormatReader().readLabelFormats().get(0);
	}
	
	private void addOrdersToInvoiceNumbers() {
		for (Order o: orders) {
			invoiceNumbers.add(o.getInvoiceNum());
		}
	}
	
	public void showOrderDisplay() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		HPanel panel = new HPanel();
		panel.add(display.getOrderDisplay(orders));
		addButtons(panel);
		
		frame.add(panel);
		frame.setSize(new Dimension(700,500));
		frame.setVisible(true);
	}
	
	private void addButtons(JPanel parent) {
		VPanel buttonPanel = new VPanel();
		
		JButton printButton = new JButton("Print Selected Labels");
		printButton.addActionListener(new PrintListener());
		buttonPanel.add(printButton);
		
		JButton importButton = new JButton("Import Orders From Excel File");
		importButton.addActionListener(new ImportListener());
		buttonPanel.add(importButton);
		
		parent.add(buttonPanel);
	}
	
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PrintSettingsDialog dialog = new PrintSettingsDialog(display.getItemsSelected(), labelFormat);
			dialog.showPrintDialog();
		}
		
	}
	
	private class ImportListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            String name = file.getName();
	            if (name.substring(name.length() - 5).equals(".xlsx")) {
	            	ArrayList<Order> newOrders = reader.getOrdersFromFile(name, efg.getFormats().get(0));
	            	
	            	System.out.println(newOrders.size());  // todo: order reading not working
	            	for (Order o: newOrders) {
	            		if (invoiceNumbers.contains(o.getInvoiceNum())) {
	            			for (Order oldOrder: orders) {
	            				if (oldOrder.getInvoiceNum() == o.getInvoiceNum()) {
	            					orders.remove(oldOrder);
	            				}
	            			}
	            		} 
	            		orders.add(o);
	            		invoiceNumbers.add(o.getInvoiceNum());
	            		frame.dispose();
	            		showOrderDisplay();
	            	}
	            }
	        } 
		}
		
	}
	
	public boolean isWindowOpen() {
		return frame.isVisible();
	}
}
