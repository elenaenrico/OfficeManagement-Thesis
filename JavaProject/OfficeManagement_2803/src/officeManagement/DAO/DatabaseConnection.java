package officeManagement.DAO;

import java.sql.*;

public class DatabaseConnection {
	
	
	public DatabaseConnection() {
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(
				    "jdbc:mysql://localhost:3306/gestionale?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
				    "root",
				    "root"
				);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;	
	}
}
