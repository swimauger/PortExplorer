package com.swimauger.portexplorer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.swimauger.portexplorer.components.Advanced;
import com.swimauger.portexplorer.components.Basic;

public class App {

	private JFrame frame;
	public static JFrame INSTANCE;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		INSTANCE = frame;
		frame = new JFrame("Port Explorer");
		frame.setBounds(100, 100, 300, 230);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Basic", new Basic());
		tabs.add("Advanced", new Advanced());
		frame.getContentPane().add(tabs);
	}

}
