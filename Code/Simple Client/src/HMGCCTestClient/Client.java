package HMGCCTestClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Socket sock;
	private String serverIP;
	private String message;
	
	private Scanner ui = new Scanner(System.in);
	
	public Client(String target) throws IOException {
		this.serverIP = target;
		this.startClient();
	}
	
	private void startClient() throws IOException {
		this.connectToServer();
		this.setupStreams();
		this.recieve();
		this.whileChatting();
	}
	
	private void connectToServer() {
		try {
			this.sock = new Socket(InetAddress.getByName(this.serverIP), 8192);
			System.out.println("Connected to Server at " + this.sock.getInetAddress().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void recieve() {
		Thread t = new Thread("Client Recieve") {
			public void run() {
				while(true) {
					try {
						System.out.println(in.readObject().toString());
					} catch (ClassNotFoundException | IOException e) {
						e.printStackTrace();
						break;
					} 
				}
				
				try {
					in.close();
					out.close();
					sock.close();
					System.exit(0);
				} catch (IOException e) {
					// Do nothing;
				}
			}
		};
		t.start();
	}
	
	private void setupStreams() {
		try {
			this.out = new ObjectOutputStream(this.sock.getOutputStream());
			this.out.flush();
			this.in = new ObjectInputStream(this.sock.getInputStream());
			System.out.println("Streams set up.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void whileChatting() {
		do {
			System.out.println("Send to Server");
			this.message = this.ui.nextLine();
			this.sendMessage(this.message);
		} while (!message.equals("END"));
	}
	
	private void sendMessage(String message) {
		try {
			this.out.writeObject(message);
			this.out.flush();
			System.out.println(message);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error Sending Message");
			System.exit(0);
		}
	}
	
	
	
}
