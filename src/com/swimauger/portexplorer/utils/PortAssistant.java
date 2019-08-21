package com.swimauger.portexplorer.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.xml.sax.SAXException;

import com.swimauger.portexplorer.App;

public class PortAssistant {
	
	private static boolean verifyData() {
		if (Config.sourcePort < 0 && Config.targetPort < 0) {
			// Invalid Port
			JOptionPane.showMessageDialog(App.INSTANCE, "Invalid Port!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!Config.protocol.equalsIgnoreCase("TCP") && !Config.protocol.equalsIgnoreCase("UDP")) {
			// Invalid Protocol
			JOptionPane.showMessageDialog(App.INSTANCE, "Invalid Protocol!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (!Config.selectedGame.equalsIgnoreCase("Minecraft")) {
			// Invalid Game Selection
			JOptionPane.showMessageDialog(App.INSTANCE, "Invalid Game Selection!", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public static void portForward() {
		int selection = 0;
		if (verifyData()) {
			try {
				GatewayDiscover discover = new GatewayDiscover();
				System.out.println("Looking for Gateway Devices");
				discover.discover();
				
				GatewayDevice d = discover.getValidGateway();
				
				if(d != null) {
					System.out.println("Found gateway device.");
				} else {
					JOptionPane.showMessageDialog(App.INSTANCE, "No valid gateway device found!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				InetAddress localAddress = d.getLocalAddress();
				System.out.println("Using local address: "+localAddress.getHostAddress());
				String externalIPAddress = d.getExternalIPAddress();
				System.out.println("External address: "+externalIPAddress);
				
				System.out.println("Attempting to map port "+Config.sourcePort+" to "+Config.targetPort);
				PortMappingEntry portMapping = new PortMappingEntry();
				
				if (d.getSpecificPortMappingEntry(Config.sourcePort, Config.protocol, portMapping)) {
					String[] options = {"Cancel", "Override"};
					selection = JOptionPane.showOptionDialog(App.INSTANCE, "Port is already mapped. Would you like to override?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
				} else {
					selection = 1;
				}
				
				if (selection == 1) {
					System.out.println("Sending port mapping request");
					if(d.addPortMapping(Config.sourcePort, Config.targetPort, localAddress.getHostAddress(), Config.protocol, "Mapping ports...")) {
						JOptionPane.showMessageDialog(App.INSTANCE, "Server port mapping successful!");
						System.out.println("Success");
					} else {
						JOptionPane.showMessageDialog(App.INSTANCE, "Port mapping failed!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					System.out.println("Canceled");
				}
				
				
			} catch (IOException | SAXException | ParserConfigurationException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(App.INSTANCE, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	public static void start() {
		switch (Config.selectedGame) {
		case "Minecraft":
			File server = new File(Config.serverDirectory);
			if (server.exists()) {
				File serverProps = new File(Config.serverDirectory+"/server.properties");
				if (serverProps.exists()) {
					try {
						List<String> lines = new ArrayList<String>();
						String line = null;
						FileReader fr = new FileReader(serverProps);
						BufferedReader br = new BufferedReader(fr);
						while ((line = br.readLine()) != null) {
							if (line.contains("server-ip=")) {
								GatewayDiscover discover = new GatewayDiscover();
								System.out.println("Looking for Gateway Devices");
								discover.discover();
								GatewayDevice d = discover.getValidGateway();
								line = "server-ip="+d.getLocalAddress().getHostAddress();
							} else if (line.contains("server-port=")) {
								line = "server-port="+Config.targetPort;
							}
							lines.add(line+"\n");
						}
						fr.close();
						br.close();
						
						FileWriter fw = new FileWriter(serverProps);
			            BufferedWriter out = new BufferedWriter(fw);
			            System.out.println(lines);
			            for(String s : lines)
			                 out.write(s);
			            out.flush();
			            out.close();
			            portForward();
					} catch (IOException | SAXException | ParserConfigurationException err) {
						JOptionPane.showMessageDialog(App.INSTANCE, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					String[] options = {"Cancel", "Continue"};
					int selection = JOptionPane.showOptionDialog(App.INSTANCE, "Missing server.properties inside Minecraft Server directory!\nWould you like to continue without editing server properties?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
					if (selection == 1) {
						portForward();
					}
				}
			} else {
				JOptionPane.showMessageDialog(App.INSTANCE, "Server directory selected does not exist!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			break;

		default:
			break;
		}
	}
	
}
