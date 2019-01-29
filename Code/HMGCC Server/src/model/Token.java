package model;

import java.util.Random;

/**
 * Class used to hold information about verification tokens.
 * @author Oliver Bowker
 * @version 1.0
 */
public class Token {

	private String tokenID;
	private String email;
	private int creationTime;
	
	/**
	 * Constructor for Token class. Used when creating a brand new Token for the database.
	 * @param email = Email of user.
	 * @param creationTime = Tokens creation time.
	 */
	public Token(String email, int creationTime) {
		this.tokenID = this.generateNewToken();
		this.email = email;
		this.creationTime = creationTime;
	}
	
	/**
	 * Overloaded constructor for Token class. Used when getting an entire Token from the database.
	 * @param tokenID = ID/Code of the Token.
	 * @param email = Email the Token is linked to.
	 * @param creationTime = Time the token was generated.
	 */
	public Token(String tokenID, String email, int creationTime) {
		this.tokenID = tokenID;
		this.email = email;
		this.creationTime = creationTime;
	}
	
	/**
	 * Function used to generate a new random verification code.
	 * @return a verification code.
	 */
	private String generateNewToken() {
		String chars = "ABCDEFGHIJKLMNPQRTSUVWXYZ123456789";
		StringBuffer ret_str = new StringBuffer();
		Random rand = new Random();
		for (int i = 0; i < 10; i++) {
			ret_str.append(chars.charAt(rand.nextInt(chars.length()-1)));
		}
		return ret_str.toString();
	}
	
	/**
	 * Function to get a Tokens ID/Code.
	 * @return Tokens ID/Code.
	 */
	public String getTokenID() {
		return this.tokenID;
	}
	
	/**
	 * Function to set a Tokens ID/Code.
	 * @param tokenID = New Token ID/Code.
	 */
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	
	/**
	 * Function to get the email address linked to Token.
	 * @return the email address linked to Token.
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Function used to set email linked to Token.
	 * @param email = New email to link with the Token object.
	 */
	public void setEmail(String email) { 
		this.email = email;
	}
	
	/**
	 * Function used to get the time the Token was created.
	 * @return time Token was created.
	 */
	public int getCreationTime() { 
		return this.creationTime;
	}
	
	/**
	 * Function to set the creation time of a Token.
	 * @param creationTime = New creation time of Token.
	 */
	public void setCreationTime(int creationTime) {
		this.creationTime = creationTime;
	}
	
}
