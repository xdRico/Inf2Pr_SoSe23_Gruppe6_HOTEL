package de.gruppe6.hotel.frontend.base.menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import de.gruppe6.hotel.frontend.base.element.Gruppe6JPanel;
import de.gruppe6.hotel.util.Log;
import de.gruppe6.hotel.backend.extended.network.NetworkMode;


public class NetworkModeMenu {
	
	private JTextField tfIpAdress;
	private JTextField tfPort;
	private JComboBox<NetworkMode> networkmodelist;
	

	public NetworkModeMenu(Gruppe6JPanel panel, PlayerAddMenu playerMenu) {

		networkmodelist = new JComboBox<NetworkMode>(NetworkMode.values());
		networkmodelist.setSelectedIndex(0);
		JLabel labelNetwork = new JLabel("Spielmodus: ");
		labelNetwork.setForeground(Color.WHITE);
		panel.add(labelNetwork);
		panel.add(networkmodelist);
		
		JLabel labelIpAddess = new JLabel("IP Adresse: ");
		labelIpAddess.setForeground(Color.WHITE);
	
		
		tfIpAdress = new JTextField("", 20);
		try {
			tfIpAdress.setText(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			Log.sendException(e);
		}
		tfIpAdress.setForeground(Color.WHITE);
		tfIpAdress.setCaretColor(Color.BLACK);
		tfIpAdress.setBackground(new Color(40, 40, 40));
		
		JLabel labelPort = new JLabel("Port: ");
		labelPort.setForeground(Color.WHITE);
		
		tfPort = new JTextField("", 20);
		tfPort.setForeground(Color.WHITE);
		tfPort.setCaretColor(Color.BLACK);
		tfPort.setBackground(new Color(40, 40, 40));
		
		tfPort.setText("17171");
		tfPort.setEditable(false);
		
		tfPort.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
	            String value = tfPort.getText();
	            int l = value.length();
	            if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' && l < 5) {
	            	tfPort.setText(value + ke.getKeyChar());
	            	if (Integer.parseInt(tfPort.getText())> 65535) {
	            		tfPort.setText("17171");
	            	}
	            }
	            
	            if ((int)ke.getKeyChar() == 8) {
	            	if (value.length() == 0) {
	            		return;
	            	}
	            	String text = "";
	            	for (int i=0; i < value.length()-1; i++) {
	            		text += value.charAt(i); 
	            	}
	            	tfPort.setText(text);
	            }
			}
		});
		
		networkmodelist.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel.remove(tfIpAdress);
				panel.remove(labelIpAddess);
				panel.remove(labelPort);
				panel.remove(tfPort);
				
				if ( networkmodelist.getSelectedItem() == NetworkMode.LOCALNET || networkmodelist.getSelectedItem() == NetworkMode.ONLINE) {
					panel.add(labelIpAddess);
					panel.add(tfIpAdress);
					panel.add(labelPort);
					panel.add (tfPort);
					playerMenu.setOnlyOne();
				}else if (networkmodelist.getSelectedItem() == NetworkMode.SERVER ) {
					panel.add(labelPort);
					panel.add (tfPort);
					playerMenu.setNone();
				}else {
					playerMenu.setNormal();
				}
				panel.refresh();
			}
		});

		panel.refresh();
	}
	
	protected InetAddress getIpAdress(){
		try {
			return InetAddress.getByName(tfIpAdress.getText());
		} catch (UnknownHostException e) {
			Log.sendException(e);
			return null;
		} 
	}
	
	protected int getPort(){
		if (Integer.parseInt(tfPort.getText()) > 0 && Integer.parseInt(tfPort.getText()) < 65536)
			return Integer.parseInt(tfPort.getText());
		
		return 17171;
	}
	
	protected NetworkMode getNetworkMode() {
		return (NetworkMode) networkmodelist.getSelectedItem();
	}
}
