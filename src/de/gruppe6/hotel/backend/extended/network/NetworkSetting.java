package de.gruppe6.hotel.backend.extended.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import de.gruppe6.hotel.backend.base.core.GameMain;
import de.gruppe6.hotel.backend.base.player.Player;
import de.gruppe6.hotel.backend.base.player.PlayerColor;
import de.gruppe6.hotel.backend.extended.exception.IsNotAClientException;
import de.gruppe6.hotel.backend.extended.exception.IsNotAServerException;
import de.gruppe6.hotel.util.Gruppe6Runnable;
import de.gruppe6.hotel.util.Log;

/**
 * Class Containing all relevant Information about the Network state of a GameMain
 * 
 * @author ricow
 *
 */
public class NetworkSetting implements Gruppe6Runnable{

	private boolean lookingForClients = false;
	private GameMain gameMain;
	
	private NetworkMode netMode = NetworkMode.LOCALPC;
	private int port = 17171;
	private InetAddress ipAdress;
	
	private Socket networkSocket;
	private ServerSocket serverSocket;

	/**
	 * Class Containing all relevant Information about the Network state of a GameMain
	 * 
	 * @throws UnknownHostException
	 */
	public NetworkSetting() throws UnknownHostException {
		this.ipAdress = InetAddress.getLocalHost();
	}
	
	/**
	 * Class Containing all relevant Information about the Network state of a GameMain
	 * 
	 * @param netMode - the NetworkMode to be set to this NetworkSetting
	 * @throws IllegalArgumentException, UnknownHostException, IOException
	 */
	public NetworkSetting(NetworkMode netMode, int port) throws IllegalArgumentException, UnknownHostException, IOException {
		if(netMode != NetworkMode.LOCALPC) {
			if(port < 1024 || port > 65535) throw new IllegalArgumentException("Port out of range!");
			
			this.ipAdress = InetAddress.getLocalHost();
			this.netMode = netMode;
			this.port = port;
			setupSocket();
		} else {
			this.ipAdress = InetAddress.getLocalHost();
		}
	}
	
	/**
	 * Class Containing all relevant Information about the Network state of a GameMain
	 * 
	 * @param netMode - the NetworkMode to be set to this NetworkSetting
	 * @param ipAdress - the InetAdress to be used for this NetworkSetting
	 * @param port - the Port (as int) to be used for this NetworkSetting
	 * @throws IllegalArgumentException, UnknownHostException, IOException
	 */
	public NetworkSetting(NetworkMode netMode, InetAddress ipAdress, int port) throws IllegalArgumentException, UnknownHostException, IOException {
		if(netMode != NetworkMode.LOCALPC) {
			
			//netzwerk-multiplayer-spiele
			
			if(netMode != NetworkMode.SERVER) {
				if(ipAdress == null) throw new IllegalArgumentException("ipAdress == null");
				this.ipAdress = ipAdress;
			}else 
				this.ipAdress = InetAddress.getLocalHost();
			
			if(port < 1024 || port > 65535) throw new IllegalArgumentException("Port out of range!");
			
			this.netMode = netMode;
			this.port = port;
			setupSocket();
		} else {
			this.ipAdress = InetAddress.getLocalHost();
		}
	}
	
	/**
	 * Method to get the NetworkMode of the current NetworkSetting
	 * 
	 * @return NetworkMode - the NetworkMode set to this NetworkSetting
	 */
	public NetworkMode getMode() {
		return netMode;
	}
	
	/**
	 * Method to get the ServerSocket of the current NetworkSetting
	 * 
	 * @return ServerSocket - the ServerSocket of the current NetworkSetting
	 * @throws IsNotAServerException
	 */
	public ServerSocket getServer() throws IsNotAServerException {
		if(getMode() != NetworkMode.SERVER)
			throw new IsNotAServerException(this);
		return serverSocket;
	}
	
	/**
	 * Method to get the Socket of the current NetworkSetting
	 * 
	 * @return Socket - the Socket of the current NetworkSetting
	 * @throws IsNotAClientException
	 */
	public Socket getClient() throws IsNotAClientException {
		if(getMode() == NetworkMode.SERVER || getMode() == NetworkMode.LOCALPC)
			throw new IsNotAClientException(this);
		return networkSocket;
	}
	
	/**
	 * Method to set up the Sockets of the current NetworkSetting
	 * 
	 * @throws IOException
	 */
	private void setupSocket() throws IOException {
		if(netMode == NetworkMode.LOCALPC) return;
		if(netMode == NetworkMode.SERVER) {
			serverSocket = new ServerSocket(port);
			Log.sendMessage("Set up ServerSocket with port: " + port);
		}else {
			networkSocket = new Socket(ipAdress, port);
			Log.sendMessage("Set up Socket to IP-Adress " + ipAdress + " with port: " + port);
		}
	}
	
	/**
	 * Method to initialize the current NetworkSetting with a given GameMain
	 * 
	 * @param gameMain - the GameMain to be initialized with
	 */
	public void init(GameMain gameMain) {
		this.gameMain = gameMain;
	}
	
	/**
	 * Method to read the next input of the Input Stream (client only!)
	 * 
	 * @return String - the input of the Stream
	 */
	public String readNext() {
		try {
			if(networkSocket != null) {
				byte[] b = networkSocket.getInputStream().readNBytes(4);
				int len = 0;
				for(int i = 0; i < b.length; i++) {
					len += b[i];
				}
				return new String(networkSocket.getInputStream().readNBytes(len));
			}else {
				int todo_only_client;
				return null;
			}
		}catch(Exception e) {
			Log.sendException(e);
			return null;
		}
	}
	
	/**
	 * Method to read the next input of the given Input Stream (client only!)
	 * 
	 * @param networkSocket - the NetworkSocket to be read
	 * @return String - the input of the Stream
	 */
	public String readNext(Socket networkSocket) {
		/*try {
			if(networkSocket != null) {
				Log.sendMessage("Trying to read 4 Bytes at " + networkSocket.getInetAddress());
				byte[] b = networkSocket.getInputStream().readNBytes(4);
				Log.sendMessage("Read 4 Bytes at " + networkSocket.getInetAddress() + ": " + b);
				int len = 0;
				for(int i = 0; i < b.length; i++) {
					len += b[i];
				}
				Log.sendMessage("Converted 4 Bytes at " + networkSocket.getInetAddress() + ": " + len);
				String out = new String(networkSocket.getInputStream().readNBytes(len));
				Log.sendMessage("Read the next " + len + " Bytes and recieved: " + out);
				return out;
			}else {
				return null;
			}
		}catch(Exception e) {
			Log.sendException(e);
			return null;
		}*/
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(networkSocket.getInputStream()));
			String read;
			while ((read = reader.readLine()) != null) {
				System.out.println(read);
			}
			return reader.readLine();
		} catch (IOException e) {
			Log.sendException(e);
			return null;
		}
		
	}
	
	@Override
	public String toString() {
		return netMode.toString() + (netMode == NetworkMode.LOCALPC ? "" : ": " + ipAdress + ":" + port);
	}

	@Override
	public void run() {
		lookingForClients = true;
		while(lookingForClients) {
			try {
				Socket client = serverSocket.accept();
				if(!lookingForClients) {
					Log.sendMessage("Server accepted a Client, but is not in accept-state!");
					continue;
				}
				Log.sendMessage("Waiting for Player Info: Name");
				String name = readNext(client);
				Log.sendMessage("Received Player Info: Name = " + name);
				Log.sendMessage("Waiting for Player Info: Color");
				String scolor = readNext(client);
				PlayerColor color = PlayerColor.valueOf(scolor);
				Log.sendMessage("Received Player Info: Color = " + color);
				if(color == null)
					color = PlayerColor.random();
				Log.sendMessage("Trying to add the Player " + name + " with color " + color + "!");
				gameMain.addClient(client, name, color);
			} catch (IOException e) {
				Log.sendException(e);
			}
		}
	}

	@Override
	public boolean isRunning() {
		return lookingForClients;
	}

	@Override
	public void stop() {
		lookingForClients = false;
	}

	/**
	 * Method to write a message to all connected Clients
	 * 
	 * @param message - the message to be sent
	 */
	public void write(String message) {
		try {
			for(Player player : gameMain.getPlayers()) {
				write(message, player.getSocket());
			}
		}catch(Exception e) {
			Log.sendException(e);
		}
	}
	
	/**
	 * Method to write a message to a specific connected Client
	 * 
	 * @param message - the message to be sent
	 * @param client - the client to be the receiver
	 * @throws IOException 
	 */
	public void write(String message, Socket client) throws IOException {
		/*client.getOutputStream().write(byteLength(message));
		client.getOutputStream().write(message.getBytes());*/
		PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
		writer.println(message);
		writer.close();
	}
	
	/**
	 * Method to get the length of a message as array of four bytes
	 * 
	 * @param input - the message that the length is to be defined
	 * @return byte[] - the array of bytes telling the length
	 */
	private byte[] byteLength(String input) {
		int len = input.getBytes().length;
		return new byte[] {
				(byte)(len >> 24),
				(byte)(len >> 16),
				(byte)(len >> 8),
				(byte)len};
	}
}
