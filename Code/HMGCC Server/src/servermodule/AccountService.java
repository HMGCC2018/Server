package servermodule;

import java.sql.SQLException;
import controller.Server;
import controller.TokenDAO;
import controller.UserDAO;
import model.ConnectedClient;
import model.Token;
import model.User;

/**
 * Class used to handle all user account requests on the server.
 * @author Oliver Bowker
 * @version 1.0
 */
public class AccountService {

	private Server server;
	
	/**
	 * Constructor for AccountService class.
	 * @param server = Server object for program.
	 */
	public AccountService(Server server) {
		this.server = server;
	}
	
	
	/**
	 * Function used to handle the log in request of a user.
	 * @param c = Connected client that is attempting to log into the server.
	 * @param message = Message recieved from the user.
	 * @return whether or not the client successfully logged in.
	 */
	public boolean login(ConnectedClient c, String message) {
		 {
			String[] userDetails;
			try {
				userDetails = message.split("LOGIN ")[0].split(" ");
				System.out.println(userDetails[0] + " " + userDetails[1]);
				
				String email = userDetails[1].trim();
				String password = userDetails[2].trim();
				System.out.println("Email : " + email + " Password : " + password);
				UserDAO dao = new UserDAO();
				User user = dao.login(email, password);
				
				if (user != null) {
					System.out.println("Logged In");
					server.connections.get(findClient(c)).setUser(user);
					server.connections.get(this.findClient(c)).setConnectionStatus("Logged In");
					server.connections.get(this.findClient(c)).setID(user.getUserID());
					server.sendMessage("Success", c);
					System.out.println("Sent Success Message to Client");
					return true;
				} else {
					System.out.println("Failed to Log In");
					return false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} 
		return false;
	}
	
	/**
	 * Function used to handle sign up requests of users.
	 * @param c = Client that made the sign up request.
	 * @param message = Message/details the client specified.
	 * @return whether or not the client successfully signed up.
	 */
	public boolean signup(ConnectedClient c, String message) {
		try {
			String[] userDetails = message.split(" ");
			UserDAO dao = new UserDAO();
			User user = dao.addUser(new User(userDetails[1], userDetails[2], userDetails[3], userDetails[4], userDetails[5], userDetails[6],
					 Boolean.parseBoolean(userDetails[8]), Boolean.parseBoolean(userDetails[8])));
			try {
				if (user != null) {
					server.connections.get(this.findClient(c)).setUser(user);
					server.sendMessage("Success", c);
					EmailService email = new EmailService();
					email.sendSignupVerification(c.getUser());
					System.out.println("Successful Signup");
					return true;
				}
				else return false;
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false;
		
	}
	
	/**
	 * Function used to verify a users account/verification code.
	 * @param c = Client that made the verify request.
	 * @param message = Message/details the client sent to the server.
	 * @return whether or not the verification was successful.
	 */
	public boolean verifySignup(ConnectedClient c, String message) {
		try {
			String userToken = message.split(" ")[1].trim();
			TokenDAO tdao = new TokenDAO();
			Token token = tdao.getToken(c.getUser().getEmail());
			int curTime = (int) System.currentTimeMillis();
			
			if (curTime - token.getCreationTime() < 300000 && token.getTokenID().equals(userToken)) {
				UserDAO udao = new UserDAO();
				c.getUser().setActive(true);
				if(udao.updateUser(c.getUser(), c.getUser().getUserID())) {
					System.out.println("User Account Activated");
					return true;
				}
			} else {
				System.err.println("Token Ran Out : " + (curTime - token.getCreationTime()) +"\n User:" + userToken + " - Val:" + token.getTokenID());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Function used to get the index of a client in the currently connected clients ArrayList.
	 * @param c = Client which index should be found.
	 * @return index of specified client.
	 */
	private int findClient(ConnectedClient c) {
		for (int i=0; i<server.connections.size(); i++) {
			if (c.equals(server.connections.get(i))) return i;
		}
		return 0;
	}
}
