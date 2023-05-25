package ru.avalon.javapp.devj120.avalontelecom;

import ru.avalon.javapp.devj120.avalontelecom.ui.MainFrame;

/**
 * Application entry point, which only purpose is to create and show application main window.
 */
public class Main {
	/**
	 * Application entry point; creates and shows application main window.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args){
		new MainFrame().setVisible(true);
	}
}
