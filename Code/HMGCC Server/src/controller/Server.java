package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import GUIWidgets.LogContainer;
import javafx.application.Platform;
import model.ConnectedClient;
import servermodule.AccountService;

/**
 * Class used to handle server connections, disconnects and receiving of requests.
 * @author Oliver Bowker
 * @version 1.0
 */
public class Server {
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ServerSocket sock;
	
	public ArrayList<ConnectedClient> connections = new ArrayList<ConnectedClient>();
	
	private AccountService accountService;
	
	private LogContainer logs;
	
	/**
	 * Enum to contain all kinds of requests user can make to server, except connection and
	 * disconnection requests.
	 */
	public enum RequestType {
		LOGIN, SIGNUP, VERIFY;
	}
	
	/**
	 * Constructor for Server class.
	 * @param logs = GUI widget where information about requests are displayed.
	 */
	public Server(LogContainer logs) {
		this.accountService = new AccountService(this);
		this.logs = logs;
	}
	
	/**
	 * Function run when server first starts, a loop is done and threaded methods are used to handle all clients.
	 * @throws IOException - Any issues with disconnecting clients etc.
	 */
	public synchronized void startServer() throws IOException {
		Thread t = new Thread("Main Server Thread") {
			public void run() {
				try {
					sock = new ServerSocket(8192, 100);
					while (true) {
						ConnectedClient client = new ConnectedClient(waitForConnection());
						connections.add(client);
						whileConnected(client);
					}
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Server Closed");
				} finally {
					try {
						if (in != null) in.close();
						if (out != null) out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		t.start();
	}
	
	/**
	 * Function used to wait for a user to connect.
	 * @return a new Socket object for the Server to communicate with the newly connected client.
	 */
	private Socket waitForConnection() {
		try {
			System.out.println("Waiting for Connection...");
			Socket conn = this.sock.accept();
			System.out.println("User connected from " + conn.getInetAddress().getHostName());
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Function used to receive message from the client, as well as log the client in to their account and disconnect the client..
	 * @param c = The client that this thread is serving.
	 */
	private synchronized void whileConnected(ConnectedClient c) {
		Thread t = new Thread("Client Thread") {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						logs.update(c.sock.getInetAddress().getHostName() + " : " + c.sock.getInetAddress().getHostAddress() + " connected.");						
					}
				});
				
				do {
					try {
						String message = c.in.readObject().toString();
						@SuppressWarnings("unused")
						// Outcome holds type of request made as well as if the requests executed 
						// successfully, may be expanded to hold more data in the future.
						int[] outcome = parseMessage(c, message);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						break;
					} catch (IOException e) {
						// User disconnected.
						break;
					}
				} while(true);
				
				handleDisconnect(c);
				
			}
		};
		t.start();
	}
	
	/**
	 * Function used to parse the users message and carry out the correct service.
	 * @param c = Client that sent the message/request.
	 * @param message = Message/request the client sent to the server.
	 * @return an array of data about the request.
	 */
	private int[] parseMessage(ConnectedClient c, String message) {
		int[] ret_arr = new int[2];
		String opType = message.split(" ")[0].toUpperCase();
		
		switch(opType) {
			case "LOGIN":
				ret_arr[0] = RequestType.LOGIN.ordinal();
				ret_arr[1] = (this.accountService.login(c, message)) ? 1 : 0 ;
				break;
			case "SIGNUP":
				ret_arr[0] = RequestType.SIGNUP.ordinal();
				ret_arr[1] = (this.accountService.signup(c, message)) ? 1 : 0;
				break;
			case "VERIFY":
				ret_arr[0] = RequestType.VERIFY.ordinal();
				ret_arr[1] = (this.accountService.verifySignup(c, message)) ? 1 : 0;
				break;
			default:
				Platform.runLater(new Runnable() {
					public void run() {
						logs.update("Unrecognised Command " + opType + ".");
					}
				});
				return null;
		}
		
		Platform.runLater(new Runnable() {
			public void run() {
				logs.update(c, ret_arr);				
			}
		});
		return ret_arr;
	}
	
	/**
	 * Function used to send messages back to connected users.
	 * @param message = The message that will be sent to the client.
	 * @param c = Client to send the message to.
	 */
	public synchronized void sendMessage(String message, ConnectedClient c) {
		Thread t = new Thread("Server Send Thread") {
			public void run() {
				try {
					c.out.writeObject(message);
					c.out.flush();
					System.out.println("SERVER - " + message);
				} catch (IOException e) {
					// Client has Disconnected.
					handleDisconnect(c);
				}				
			}
		};
		t.start();
	}
	
	/**
	 * Function that handles a client disconnecting from the server.
	 * @param c = Client that has disconnected.
	 * @throws IOException - Caused when there is an error closing resources.
	 */
	public void handleDisconnect(ConnectedClient c) {
		try {
			c.in.close();
			c.out.close();
			c.sock.close();
		} catch (IOException e) {
			// Thrown when stream/socket is all ready closed.
		} finally {
			this.connections.remove(c);
			Platform.runLater(new Runnable() {
				public void run() {
					logs.update(c.sock.getInetAddress().getHostName() + " : " + c.sock.getInetAddress().getHostAddress() + " disconnected.");					
				}
			});
			System.out.println("Client at : " + c.sock.getInetAddress().getHostAddress() + " disconnected.");
		}
	}
}
