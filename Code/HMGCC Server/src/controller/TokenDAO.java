package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Token;

/**
 * Class used to access the Token table of the sqlite database. Tokens are used to
 * verify actions such as activating accounts or changing passwords.
 * @author Oliver Bowker
 * @version 1.0
 */
public class TokenDAO {

	/**
	 * Function used to get connection to the database.
	 * @return a Connection object to connect to database.
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
	 * Function used to add a new Token object to the Token table.
	 * @param t = The new Token object to be inserted into the table.
	 * @return whether or not the insert was successful.
	 * @throws SQLException when there is an issue with connecting to or adding to the database.
	 */
	public boolean addNewToken(Token t) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		
		try {
			c = this.getConnection();
			c.setAutoCommit(false);
			
			s = c.prepareStatement("INSERT INTO TOKEN VALUES(?,?,?);");
			s.setString(1, t.getTokenID());
			s.setString(2, t.getEmail());
			s.setInt(3, t.getCreationTime());
			
			s.executeUpdate();
			c.commit();
			s.close();
			c.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null && !s.isClosed()) s.close();
			if (c != null && !c.isClosed()) c.close();
		}
		return false;
	}
	
	/**
	 * Function used to get a Token from the database.
	 * @param email = Email of user who is attempting to verify an action.
	 * @return the Token object related to the email parameter.
	 * @throws SQLException if there is an issue accessing the database or carrying out the query.
	 */
	public Token getToken(String email) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		ResultSet r = null;
		Token token = null;
		
		try {
			c = this.getConnection();
			s = c.prepareStatement("SELECT * FROM TOKEN WHERE EMAIL = ?;");
			s.setString(1, email);
			r = s.executeQuery();
			
			while (r.next()) {
				token = new Token(r.getString(1), r.getString(2), r.getInt(3));
			}
			
			r.close();
			s.close();
			c.close();
			return token;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (r != null && !r.isClosed()) r.close();
			if (s != null && !s.isClosed()) s.close();
			if (c != null && !c.isClosed()) c.close();
		}
		
		return token;
	}
	
	/**
	 * Function used to remove an existing token from the table/database.
	 * @param email = Email that should have token removed from database.
	 * @return whether or not the removal was successful.
	 * @throws SQLException when there is an issue accessing the database or carrying out the update.
	 */
	public boolean removeToken(String email) throws SQLException {
		Connection c = null;
		PreparedStatement s = null;
		
		try {
			c = this.getConnection();
			c.setAutoCommit(false);
			s = c.prepareStatement("DELETE FROM TOKEN WHERE EMAIL = ?;");
			s.setString(1, email);
			s.executeUpdate();
			s.close();
			c.commit();
			c.close();
			System.out.println("Removed Existing Token");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null && !s.isClosed()) s.close();
			if (c != null && !c.isClosed()) c.close();
		}
		return false;
	}
	
}
