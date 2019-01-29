package HMGCCTestClient;

import java.io.IOException;

public class ClientRunner {
	
	public static void main(String[] args) {
		try {
			@SuppressWarnings("unused")
			Client c = new Client("127.0.0.1");
		} catch (IOException e) {
			System.out.println("Server Ended Connection");
			System.exit(0);
		}
	}
	
}
