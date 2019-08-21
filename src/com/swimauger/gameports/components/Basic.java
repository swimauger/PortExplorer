package com.swimauger.gameports.components;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.swimauger.gameports.App;
import com.swimauger.gameports.utils.Config;
import com.swimauger.gameports.utils.PortAssistant;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;

public class Basic extends JPanel {

	private static final long serialVersionUID = -4163403058500148728L;

	private JTextField txtPathToServer;
	
	public Basic() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(100dlu;default)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		Box verticalBox = Box.createVerticalBox();
		add(verticalBox, "4, 2");
		
		JLabel lblGame = new JLabel("Game:");
		add(lblGame, "2, 4, right, default");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Minecraft"}));
		add(comboBox, "6, 4, fill, center");
		
		JLabel lblServer = new JLabel("Server:");
		add(lblServer, "2, 8, right, default");
		
		txtPathToServer = new JTextField();
		add(txtPathToServer, "6, 8, fill, default");
		txtPathToServer.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setToolTipText("Path to root of the servers folder");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(App.INSTANCE);
				Config.serverDirectory = fc.getSelectedFile()+"";
				txtPathToServer.setText(Config.serverDirectory);
			}
		});
		add(btnBrowse, "6, 9, fill, center");
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PortAssistant.start();
			}
		});
		add(btnStart, "6, 13, center, center");

	}

}
