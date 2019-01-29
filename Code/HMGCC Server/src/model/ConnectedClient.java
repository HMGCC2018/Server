package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class used to keep details on clients connected to the server.
 * @author Oliver Bowker
 * @version 1.0
 */
public class ConnectedClient {

	public ObjectOutputStream out;
	public ObjectInputStream in;
	public Socket sock;
	
	private String connectionStatus;
	private String id;
	private User user;
	
	/**
	 * Constructor for ConnectedClient class.
	 * @param sock = Socket object created when user connects to server.
	 */
	public ConnectedClient(Socket sock) {
		this.sock = sock;
		this.connectionStatus = "Connected - Not Logged In";
		this.id = "Unknown";
		this.setupStreams();
	}
	
	/**
	 * Overridden toString() method.
	 */
	@Override
	public String toString() {
		return "Address : " + this.sock.getInetAddress().getHostName() + 
			   "\nPort Number : " + this.sock.getPort();
	}
	
	/**
	 * Function to setup the input and output streams for communication.
	 */
	private void setupStreams() {
		try {
			this.out = new ObjectOutputStream(this.sock.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.sock.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Function to get current connection status.
	 * @return current connection status.
	 */
	public String getConnectionStatus() {
		return this.connectionStatus;
	}
	
	/**
	 * Set connection status.
	 * @param connectionStatus = new connection status.
	 */
	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}
	
	/**
	 * Function to get ID of client.
	 * @return clients ID.
	 */
	public String getID() {
		return this.id;
	}
	
	/**
	 * Function to set ID of client.
	 * @param id = clients new ID.
	 */
	public void setID(String id) {
		this.id = id;
	}
	
	/**
	 * Function to get the User associated with the client.
	 * @return the User object associated with the client.
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Function to set the User of the client.
	 * @param user = New User object of the client.
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
