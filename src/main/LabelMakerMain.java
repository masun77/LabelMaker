package main;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import input.ExcelFileReader;
import input.FileReader;
import labels.DateImp;
import printing.LabelRendererAndPrinter;

/**
 * Uses the input order information to create labels that can be printed. 
 */
public class LabelMakerMain {

	public static void main(String[] args) {
		// Display orders
		
		// Enter orders
		
		// View and print labels
		
		// Render labels
		LabelRendererAndPrinter lr = new LabelRendererAndPrinter();
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("cust", "lettuce", "5 lb bag", "00081812345", new DateImp(1,2,2021)));
		items.add(new Item("Maya", "carrots", "10 bunches", "00000818187777", new DateImp(4,25,2022)));
		lr.renderLabels(items);
		
		// Print labels
		lr.printLabels();
		
		// Export to QB
	}

}
