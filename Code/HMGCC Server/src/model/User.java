package model;

/**
 * Class used to store details about stored Users.
 * @author Oliver
 * @version 1.0
 */
public class User {

	private String userID;
	private String encryptionKey;
	private String username;
	private String email;
	private String password;
	private String phone;
	private boolean twoFactorAuthentiaction;
	private boolean accountActive;
	
	/**
	 * Constructor of User class.
	 * @param userID = Users unique ID.
	 * @param encryptionKey = Key used to encrypt and decrypt users data.
	 * @param username = Users username.
	 * @param email = Users email.
	 * @param password = Users hashed password.
	 * @param phone = Users phone number.
	 * @param twoFactorAuthentication = If two factor authentication is enabled.
	 * @param accountActive = If account has been activated.
	 */
	public User(String userID, String encryptionKey, String username, String email, String password,
				String phone, boolean twoFactorAuthentication, boolean accountActive) {
		this.userID = userID;
		this.encryptionKey = encryptionKey;
		this.username = username;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.twoFactorAuthentiaction = twoFactorAuthentication;
		this.accountActive = accountActive;
	}
	
	/**
	 * Overridden toString() method.
	 */
	@Override
	public String toString() {
		return "User ID : " + this.userID +
			   "\nEncryption Key : " + this.encryptionKey + 
			   "\nUsername : " + this.username + 
			   "\nEmail : " + this.email + 
			   "\nPassword : " + this.password + 
			   "\nPhone Number : " + this.phone + 
			   "\nTwo Factor Authentication Activated : " + this.twoFactorAuthentiaction + 
			   "\nAccount Active : " + this.accountActive;
	}
	
	/**
	 * Function used to get Users ID.
	 * @return users ID.
	 */
	public String getUserID() {
		return this.userID;
	}
	
	/**
	 * Function used to set the Users ID.
	 * @param userID = Users new ID.
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Function used to get the encryption key.
	 * @return users encryption key.
	 */
	public String getEncryptionKey() {
		return this.encryptionKey;
	}
	
	/**
	 * Function used to set the users encryption key.
	 * @param encryptionKey = Users new encryption key.
	 */
	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}
	
	/**
	 * Function used to get the users username.
	 * @return the users username.
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Function used to set users username.
	 * @param username = Users new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Function used to get the users email.
	 * @return users email address.
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Function used to set the users email.
	 * @param email = Users new email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Function used to get the users hashed password.
	 * @return users hashed password.
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Function used to set the users password.
	 * @param password = Users new password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Function used to get the user phone number.
	 * @return users phone number.
	 */
	public String getPhone() {
		return this.phone;
	}
	
	/**
	 * Function used to set the users phone number.
	 * @param phone = Users new phone number.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	 * Function used to get whether or not the user has two factor authentication enabled.
	 * @return whether or not the user has two factor authentication enabled.
	 */
	public boolean getTwoFactorAuthentication() {
		return this.twoFactorAuthentiaction;
	}
	
	/**
	 * Function used to set whether or not the user has two factor authentication enabled.
	 * @param twoFactorAuthentication = Whether or not the user has two factor authentication enabled.
	 */
	public void setTwoFactorAuthentication(boolean twoFactorAuthentication) {
		this.twoFactorAuthentiaction = twoFactorAuthentication;
	}
	
	/**
	 * Function used to see if a users account is active.
	 * @return whether or not the users account is active.
	 */
	public boolean isActive() {
		return this.accountActive;
	}
	
	/**
	 * Function used to set whether or not the users account is active.
	 * @param accountActive = Whether or not the users account is active.
	 */
	public void setActive(boolean accountActive) {
		this.accountActive = accountActive;
	}
	
	
}
