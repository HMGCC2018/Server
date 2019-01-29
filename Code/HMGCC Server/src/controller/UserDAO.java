package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

/**
 * Class used to access and do CRUD operations on the User database.
 * @author Oliver Bowker
 * @version 1.0
 */
public class UserDAO {

	/**
	 * Function used to set up a connection to the database.
	 * @return connection to the database.
	 */
	private Connection getConnection() {
		Connection c = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			String dbURL = "jdbc:sqlite:users.sqlite";
			c = DriverManager.getConnection(dbURL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	/**
	 * Function used to get a new User dependent on email entered, used for logging in.
	 * @param userEmail = User email to log into server.
	 * @return a new User object with data from the database.
	 * @throws SQLException - Thrown if there is an issue in a database operation.
	 */
	public User getUser(String userEmail) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		ResultSet r = null;
		User target = null;
		
		try {
			c = this.getConnection();
			s = c.prepareStatement("SELECT * FROM USER WHERE EMAIL = ?;");
			s.setString(1, userEmail);
			r = s.executeQuery();
		
			while(r.next()) {
				target = new User(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
								  r.getString(6), Boolean.parseBoolean(r.getString(7)), Boolean.parseBoolean(r.getString(8)));
			}
			
			return target;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (r != null) r.close();
			if (s != null) s.close();
			if (c != null) c.close();
		}
		return target;
	}
	
	/**
	 * Function used to add a new user, used for signing up.
	 * @param user = New user to add to the database.
	 * @return a new User object of the insert was successful.
	 * @throws SQLException - Thrown if there is an issue in a database operation.
	 */
	public User addUser(User user) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		
		try {
			c = this.getConnection();
			c.setAutoCommit(false);
			
			s = c.prepareStatement("INSERT INTO USER VALUES(?,?,?,?,?,?,?,?);");
			s.setString(1, user.getUserID());
			s.setString(2, user.getEncryptionKey());
			s.setString(3, user.getUsername());
			s.setString(4, user.getEmail());
			s.setString(5, user.getPassword());
			s.setString(6, user.getPhone());
			s.setString(7, Boolean.toString(user.getTwoFactorAuthentication()));
			s.setString(8, Boolean.toString(user.isActive()));
			s.executeUpdate();
			
			s.close();
			c.commit();
			c.close();
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null && !s.isClosed()) s.close();
			if (c != null && c.isClosed()) c.close();
		}
		return null;
	}
	
	/**
	 * Function used to update an existing User records details.
	 * @param user = The new User to replace/update the old User.
	 * @param userID = The userID of the User to be updated.
	 * @return whether or not the update was successful.
	 * @throws SQLException when there is an issue connecting to the database or carrying out the update.
	 */
	public boolean updateUser(User user, String userID) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		
		try {
			c = this.getConnection();
			c.setAutoCommit(false);
			s = c.prepareStatement("UPDATE USER SET USERID = ?, ENCRYPTIONKEY = ?, USERNAME = ?, EMAIL = ?, PASSWORD = ?, "
									+ " PHONE = ?, TWOFACTORAUTHENTICATION = ?, ACCOUNTACTIVE = ? WHERE USERID = ?");
			s.setString(1, user.getUserID());
			s.setString(2, user.getEncryptionKey());
			s.setString(3, user.getUsername());
			s.setString(4, user.getEmail());
			s.setString(5, user.getPassword());
			s.setString(6, user.getPhone());
			s.setString(7, Boolean.toString(user.getTwoFactorAuthentication()));
			s.setString(8, Boolean.toString(user.isActive()));
			s.setString(9, userID);
			
			s.executeUpdate();
			s.close();
			c.commit();
			c.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null && !s.isClosed()) s.close();
			if (c != null && c.isClosed()) c.close();
		}
		return false;
	}
	
	/**
	 * Function used to login to a users account.
	 * @param email = Email related to users login details.
	 * @param password = Password related to users login details.
	 * @return the logged in users ID, if null then sign in was unsuccessful.
	 * @throws SQLException - Thrown if there is an issue in a database operation.
	 */
	public User login(String email, String password) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		ResultSet r = null;
		
		User user = null;
		
		try {
			c = this.getConnection();
			s = c.prepareStatement("SELECT * FROM USER WHERE EMAIL = ?;");
			s.setString(1, email);
			r = s.executeQuery();
			
			while (r.next()) {
				user = new User(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5),
								r.getString(6), r.getBoolean(7), r.getBoolean(8));
			}
			
			// Try used to catch if there was no User in the database.
			try {
				if (user.getPassword().equals(password)) return user;
			} catch (NullPointerException e) {
				return null;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			if (r != null) r.close();
			if (s != null) s.close();
			if (c != null) c.close();
		}
		return null;
	}
	
}
