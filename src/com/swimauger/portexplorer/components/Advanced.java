package com.swimauger.portexplorer.components;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.swimauger.portexplorer.App;
import com.swimauger.portexplorer.utils.Config;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Advanced extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1758736710816927909L;
	private JTextField txtSource;
	private JTextField txtTarget;

	/**
	 * Create the panel.
	 */
	public Advanced() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				ColumnSpec.decode("5dlu"),
				ColumnSpec.decode("max(90dlu;min)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.MIN_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),}));
		
		JLabel lblPort = new JLabel("Port:");
		add(lblPort, "4, 4, right, default");
		
		JPanel panel = new JPanel();
		add(panel, "6, 4, center, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(30dlu;min)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(30dlu;min)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		txtSource = new JTextField();
		txtSource.setEnabled(false);
		txtSource.setText("Source");
		panel.add(txtSource, "2, 2, fill, default");
		txtSource.setColumns(10);
		
		JLabel lblTo = new JLabel("to");
		panel.add(lblTo, "4, 2, right, default");
		
		txtTarget = new JTextField();
		txtTarget.setEnabled(false);
		txtTarget.setText("Target");
		txtTarget.setColumns(10);
		panel.add(txtTarget, "6, 2, fill, center");
		
		JLabel lblProtocol = new JLabel("Protocol:");
		add(lblProtocol, "4, 6, right, default");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"TCP", "UDP"}));
		comboBox.setName("");
		comboBox.setEnabled(false);
		add(comboBox, "6, 6, center, center");
		
		JCheckBox chckbxUseDefaultSettings = new JCheckBox("Use default settings");
		chckbxUseDefaultSettings.setSelected(true);
		chckbxUseDefaultSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isEnabled = !chckbxUseDefaultSettings.isSelected();
				txtSource.setEnabled(isEnabled);
				txtTarget.setEnabled(isEnabled);
				comboBox.setEnabled(isEnabled);
				if (isEnabled) {
					txtSource.setText(Config.sourcePort+"");
					txtTarget.setText(Config.targetPort+"");
				} else {
					txtSource.setText("Source");
					txtTarget.setText("Target");
				}
			}
		});
		add(chckbxUseDefaultSettings, "6, 8");
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!chckbxUseDefaultSettings.isSelected()) {
					try {
						String protocol = (String) comboBox.getSelectedItem();
						int source = Integer.parseInt(txtSource.getText());
						int target = Integer.parseInt(txtTarget.getText());
						if (protocol.equalsIgnoreCase("TCP") || protocol.equalsIgnoreCase("UDP")) {
							if (source >= 0 && target >= 0) {
								Config.protocol = (String) comboBox.getSelectedItem();
								System.out.println("Protocol: "+Config.protocol);
								Config.sourcePort = Integer.parseInt(txtSource.getText());
								System.out.println("Source Port: "+Config.sourcePort);
								Config.targetPort = Integer.parseInt(txtTarget.getText());
								System.out.println("Target Port: "+Config.targetPort);
								JOptionPane.showMessageDialog(App.INSTANCE, "Saved settings:\nSelected Game: "+Config.selectedGame+"\nSource Port: "+Config.sourcePort+"\nTarget Port: "+Config.targetPort+"\nProtocol: "+Config.protocol+"\nServer Directory: "+Config.serverDirectory);
								System.out.println("Saved");
							} else {
								JOptionPane.showMessageDialog(App.INSTANCE, "Invalid Port! Ports must be greater than or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(App.INSTANCE, "Invalid Protocol!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (NumberFormatException err) {
						JOptionPane.showMessageDialog(App.INSTANCE, "Invalid Port! Ports have to be a number", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Selected Game: "+Config.targetPort);
					Config.sourcePort = 25565;
					System.out.println("Source Port: "+Config.sourcePort);
					Config.targetPort = 25565;
					System.out.println("Target Port: "+Config.targetPort);
					Config.protocol = "TCP";
					System.out.println("Protocol: "+Config.targetPort);
					System.out.println("Server Directory: "+Config.serverDirectory);
					
					JOptionPane.showMessageDialog(App.INSTANCE, "Saved settings:\nSelected Game: "+Config.selectedGame+"\nSource Port: "+Config.sourcePort+"\nTarget Port: "+Config.targetPort+"\nProtocol: "+Config.protocol+"\nServer Directory: "+Config.serverDirectory);
					System.out.println("Saved");
				}
			}
		});
		add(btnSave, "6, 10, center, center");

	}

}
