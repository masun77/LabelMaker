package main;

import javax.swing.JFrame;

import display.MainFrame;

public class RunProgram {
	public static void main(String[] args) {
		MainFrame main = new MainFrame(JFrame.EXIT_ON_CLOSE);
		main.showOrderDisplay();
	}
}
