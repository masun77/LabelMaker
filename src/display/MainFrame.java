/**
 * MainFrame
 * Displays a main window with the current orders and options
 * to print them or import more orders. 
 */

package display;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import importData.ExcelFormat;
import importData.ExcelFormatGetter;
import importData.ExcelReader;
import importData.ExcelWriter;
import importData.FileChooserLocationGetter;
import labels.Date;
import labels.DateImp;
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
	private ArrayList<Order> filteredOrders = new ArrayList<>();
	private ArrayList<Integer> invoiceNumbers = new ArrayList<>(); 
	private ExcelReader reader = new ExcelReader();
	private ExcelWriter writer = new ExcelWriter();
	private JComboBox<String> excelFormatBox = null;
	private Date filterStartDate = new DateImp();
	private Date filterEndDate = new DateImp();
	private VPanel orderPanel = new VPanel();
	
	public MainFrame(int frameCloseOperation) {
		setDates();
		frame.setDefaultCloseOperation(frameCloseOperation);
		efg.readExcelFormats();
		frame.setSize(new Dimension(700,500));

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
		orderPanel = new VPanel();
		frame.getContentPane().removeAll();
		filterOrders();
		
		HPanel panel = new HPanel();
		addDateFilter(orderPanel);
		orderPanel.add(display.getOrderDisplay(filteredOrders));
		panel.add(orderPanel);
		addButtons(panel);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	/**
	 * Set the start date for the date filter to today's date,
	 * and the end date to today's date + 1. 
	 */
	private void setDates() {		
		Calendar cal = Calendar.getInstance();
		filterStartDate = DateImp.parseDate(cal.get(Calendar.MONTH)+1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.YEAR));
		filterEndDate = DateImp.parseDate(filterStartDate.getDateMMDDYYYY());
		filterEndDate.addDays(4);
//		filterStartDate = new DateImp(1,1,2022);
//		filterEndDate = new DateImp(1,1,2023);
	}
	
	/**
	 * Add dates that fall between the filter start and end dates, inclusive,
	 * to the list of filtered orders. 
	 */
	private void filterOrders() {
		filteredOrders = new ArrayList<>();
		for (Order o: orders) {
			Date date = o.getShipDate();
			if (date.dateLaterThanOrEqualTo(filterStartDate) 
					&& date.dateEarlierThan(filterEndDate)) {
				insertByDate(filteredOrders, o);
			}
		}
	}
	
	/**
	 * Insert the given String into the list of Strings in alphabetical order. 
	 * @param list the list to insert into
	 * @param item the item to insert
	 */
	private void insertByDate(ArrayList<Order> list, Order order) {
		int index = 0;
		while (index < list.size() && 
				list.get(index).getShipDate().dateEarlierThan(order.getShipDate())) {
			index += 1;
		}
		while (index < list.size() && list.get(index).getShipDate().dateEquals(order.getShipDate())
				&& list.get(index).getCompany().compareTo(order.getCompany()) < 0) {
			index += 1;
		}
		list.add(index, order);
	}
	
	/**
	 * Add a date filter to the given panel with a start date textfield
	 * and end date textfield, with listeners to reset the order display
	 * when they lose focus
	 * @param parent the panel to add the datefilter to
	 */
	private void addDateFilter(JPanel parent) {
		HPanel datePanel = new HPanel();
		datePanel.setMaximumSize(new Dimension(300,100));
		
		JTextField startDateField = new JTextField();
		JTextField endDateField = new JTextField();
		
		startDateField.setText(filterStartDate.getDateMMDDYYYY());
		startDateField.addFocusListener(new DateFilter(startDateField, true));
		endDateField.setText(filterEndDate.getDateMMDDYYYY());
		endDateField.addFocusListener(new DateFilter(endDateField, false));
		
		datePanel.add(new JLabel("From: "));
		datePanel.add(startDateField);
		datePanel.add(new JLabel("To: "));
		datePanel.add(endDateField);
		
		parent.add(datePanel);
	}
	
	/**
	 * Add the side buttons to the frame, including the print and import buttons
	 * @param parent the panel to add the buttons to. 
	 */
	private void addButtons(JPanel parent) {
		VPanel buttonPanel = new VPanel();
		
		addFunction(buttonPanel, "Print Selected Labels", new PrintListener());
		addFunction(buttonPanel, "Import Orders From Excel File", new ImportListener());
		addFunction(buttonPanel, "Select All", new SelectAllListener());
		addFunction(buttonPanel, "Clear Selections", new ClearSelectionsListener());
		addFunction(buttonPanel, "Delete Selected Orders", new DeleteOrdersListener());
		
		parent.add(buttonPanel);
	}
	
	/**
	 * Add a button to the parent panel with the given text and action listener.
	 * @param parent the panel to add the buttom to
	 * @param buttonText the text for the button
	 * @param listener the action listener for the button
	 */
	private void addFunction(JPanel parent, String buttonText, ActionListener listener) {
		JButton button = new JButton(buttonText);
		button.addActionListener(listener);
		parent.add(button);
		parent.add(Box.createRigidArea(new Dimension(0, 10)));
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
		FileChooserLocationGetter lg = new FileChooserLocationGetter();
		String path = lg.getFolderPath();
		fc.setCurrentDirectory(new File(path));
		
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
	 * When the clear selections button is clicked,
	 * clears the checked boxes in the order display.
	 */
	private class ClearSelectionsListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			display.clearSelections();
		}
	}
	
	/**
	 * When the clear selections button is clicked,
	 * clears the checked boxes in the order display.
	 */
	private class DeleteOrdersListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			orders = display.deleteSelectedOrders();
			showOrderDisplay();
			backupOrders(orders, efg.getFormatByName("Backup"));
		}
	}
	
	private class SelectAllListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			display.selectAllBoxes();
		}
	}	
	
	/**
	 * When the field loses focus, set its corresponding date to the 
	 * date currently in its text, and reshow the order display 
	 * with the orders filtered by the new date .
	 */
	private class DateFilter implements FocusListener {		
		private JTextField field;
		private boolean isStart;
		
		public DateFilter(JTextField f, boolean start) {
			field = f;
			isStart = start;
		}
		
		@Override
		public void focusGained(FocusEvent e) {		}

		@Override
		public void focusLost(FocusEvent e) {
			if (isStart) {
				filterStartDate = DateImp.parseDate(field.getText());
			}
			else {
				filterEndDate = DateImp.parseDate(field.getText());
			}
			showOrderDisplay();
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
