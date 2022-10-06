/**
 * MainFrame
 * Displays a main window with the current orders and options
 * to print them or import more orders. 
 */

package display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import importData.ExcelFormat;
import importData.ExcelFormatGetter;
import importData.ExcelReader;
import importData.ExcelWriter;
import labels.LabelFormat;
import labels.LabelFormatReader;
import main.Order;
import printing.PrintSettingsDialog;

public class MainFrame {
	private OrderDisplay display;
	private LabelFormat labelFormat;
	private JFrame frame = new JFrame();
	private ExcelFormatGetter efg = new ExcelFormatGetter();
	private ArrayList<Order> orders;
	private ArrayList<Integer> invoiceNumbers = new ArrayList<>(); 
	private ExcelReader reader = new ExcelReader();
	private ExcelWriter writer = new ExcelWriter();
	private JComboBox<String> excelFormatBox = null;
	
	public MainFrame() {
		efg.readExcelFormats();

		orders = reader.getOrdersFromFile("resources/OrderBackup.xlsx", 
				efg.getFormatByName("Backup"));
		addOrdersToInvoiceNumbers(orders);
		display = new OrderDisplay();
		
		labelFormat = new LabelFormatReader().readLabelFormats().get(0);
	}
	
	/**
	 * Add the invoice number of each of the given
	 * orders to the list. 
	 */
	private void addOrdersToInvoiceNumbers(ArrayList<Order> ords) {
		for (Order o: ords) {
			invoiceNumbers.add(o.getInvoiceNum());
		}
	}
	
	/**
	 * Display the default orders to the screen. 
	 */
	public void showOrderDisplay() {
		frame.getContentPane().removeAll();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		HPanel panel = new HPanel();
		panel.add(display.getOrderDisplay(orders));
		addButtons(panel);
		
		frame.add(panel);
		frame.setSize(new Dimension(700,500));
		frame.setVisible(true);
	}
	
	/**
	 * Add the side buttons to the frame, including the print and import buttons
	 * @param parent the panel to add the buttons to. 
	 */
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
	
	/**
	 * When the print button is clicked,
	 * show a print dialog allowing the user to print
	 * labels for the currently selected items. 
	 */
	private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			PrintSettingsDialog dialog = new PrintSettingsDialog(display.getItemsSelected(), labelFormat);
			dialog.showPrintDialog();
		}
		
	}
	
	/**
	 * Open a new file chooser in a new frame, and return the 
	 * return file selected or null if no file selected. 
	 * @return the selected file to open, otherwise null
	 */
	private File getFileChooserFile() {
		JFileChooser fc = new JFileChooser();
		fc.setApproveButtonText("Import Orders");
		addFormatOptionToFileChooser(fc);
		
		JFrame tempFrame = new JFrame();
		tempFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		int returnVal = fc.showOpenDialog(tempFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return fc.getSelectedFile();
		}
		return null;
	}
	
	/**
	 * Add to the file chooser a combobox allowing the user
	 * to choose what format the Excel file to import is. 
	 * @param chooser the fileCHooser to add the combobox to
	 */
	private void addFormatOptionToFileChooser(JFileChooser chooser) {
		JComboBox<String> formatList = createFormatComboBox();
		JPanel panel = (JPanel) chooser.getComponent(3);    // JPanel at bottom of filechooser
		
		HPanel formatPanel = new HPanel();
		formatPanel.add(new JLabel("Excel Format Type: "));
		formatPanel.add(formatList);
		formatPanel.setBorder(null);
		formatPanel.setBackground(null);
		
		panel.add(formatPanel, 3);
	}
	
	/**
	 * Create a comboBox containing as strings the names of all
	 * the current excel file formats in the settings formats folder. 
	 * @return a combobox with options of the names of all current excel file formats
	 */
	private JComboBox<String> createFormatComboBox() {
		ArrayList<ExcelFormat> formats = efg.getFormats();
		String[] formatNames = new String[formats.size()];
		int setIndex = 0;
		for (int i = 0; i < formats.size(); i++) {
			formatNames[i] = formats.get(i).getName();
			if (formats.get(i).getName().equals("Quickbooks")) {
				setIndex = i;
			}
		}
		excelFormatBox = new JComboBox<>(formatNames);
		excelFormatBox.setSelectedIndex(setIndex);
		return excelFormatBox;
	}
	
	/**
	 * Return whether the given file name ends in .xlsx
	 * @param name the name of the file
	 * @return true if it ends in .xlsx, false otherwise
	 */
	private boolean isExcelFile(String name) {
		return name.substring(name.length() - 5).equals(".xlsx");
	}
	
	/**
	 * Write the given orders to the saved order file in the given format
	 * @param orders the orders to write to the file
	 * @param format the format to write them in
	 */
	private void backupOrders(ArrayList<Order> orders, ExcelFormat format) {
		writer.writeOrders(orders, format);
	}
	
	/**
	 * Add the new orders to the existing list of orders.
	 * If the order number is already present, replace it with the new order
	 * of that number. Then, update the order display
	 * and backup the orders to a file. 
	 * @param newOrders the orders to add to the display
	 */
	private void addOrdersToExistingOrders(ArrayList<Order> newOrders) {
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
    	}
		showOrderDisplay();
		backupOrders(orders, efg.getFormatByName("Backup"));
	}
	/**
	 * On click, open a file chooser window, get the file the user chooses,
	 * read the orders from it if its an Excel file, 
	 * and add them to the existing order list. 
	 */
	private class ImportListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			File file = getFileChooserFile();
	        if (file != null) {
	            String name = file.getAbsolutePath();
	            if (isExcelFile(name)) {
	            	ArrayList<Order> newOrders = reader.getOrdersFromFile(name, 
	            			efg.getFormatByName(excelFormatBox.getSelectedItem().toString()));
	            	addOrdersToExistingOrders(newOrders);
	            }
	        } 
		}
	}
	
	/**
	 * Returns whether the main frame window is visible or not. 
	 * @return true if the window is visible, false otherwise. 
	 */
	public boolean isWindowOpen() {
		return frame.isVisible();
	}
}
