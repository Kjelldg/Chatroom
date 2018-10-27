package Database;

import java.sql.*;

public class Database {
	
	//THIS INFO IS NEEDED WHEN CONNECTING FROM SERVER!
	//String url = "jdbc:postgresql://localhost:5432/postgres";
	//String user name = "postgres";
	//String password = "[3`Td?9=";
	//Database database = new Database(url, user name, password);
	//database.connectDatabase();
	//database.setUp();

	private Connection con;
	private String url;
	private String databaseUsername;
	private String databasePassword;

	// Constructor.
	public Database(String url, String databaseUsername, String databasePassword) throws SQLException {
		this.url = url;
		this.databaseUsername = databaseUsername;
		this.databasePassword = databasePassword;
		
		connectDatabase();
	}

	public Database() {
	}

	// Method for connecting our database.
	public void connectDatabase() throws SQLException {
		con = DriverManager.getConnection(url, databaseUsername, databasePassword);

		System.out.println("Connected to database.");
	}

	// Creating necessary tables if they don't already exist.
	public void setUp() {
		try {
			Statement statement = con.createStatement();
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS userdata (username VARCHAR(80) PRIMARY KEY NOT NULL, password VARCHAR(80) NOT NULL);");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Add created user to our database table 'userdata'.
	public boolean createUser(String username, String password) {

		username = username.toLowerCase();

		PreparedStatement statement;
		try {
			statement = con.prepareStatement("INSERT INTO userdata VALUES (?, ?)");
			statement.setString(1, username);
			statement.setString(2, password);

			statement.execute();
			return true;

		} catch (SQLException e) {
			System.err.println("User already exists.");
			return false;
		}
	}

	// Allows login for user if information is correct.
	public boolean logOnUser(String username, String password) {

		username = username.toLowerCase();

		try {
			PreparedStatement statement = con
					.prepareStatement("SELECT username FROM userdata WHERE LOWER(username) = ? AND password = ?");
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			System.err.println("Wrong credentials.");
			return false;
		}
	}

	// Checks if user exists in our database.
	public boolean userExistence(String username) {

		username = username.toLowerCase();

		try {
			PreparedStatement statement = con
					.prepareStatement("SELECT username FROM userdata WHERE LOWER(username) = ?");
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			System.err.println("Could not retreive data.");
			return false;
		}
	}

	// Removes user if supplied information is correct.
	// This method was created just in case.
	public boolean removeUser(String username, String password) {

		username = username.toLowerCase();

		try {
			PreparedStatement statement = con
					.prepareStatement("DELETE FROM userdata WHERE username = ? AND password = ?");
			statement.setString(1, username);
			statement.setString(2, password);

			return statement.executeUpdate() > 0;

		} catch (SQLException e) {
			System.err.println("Wrong credentials.");
			return false;
		}
	}

	// Close our database when we are done.
	public boolean closeDatabase() {
		try {
			con.close();
			System.out.println("Database closed.");
		} catch (SQLException e) {
			System.err.println("Error: Failed to close database.");
			return false;
		}
		return false;
	}

}
