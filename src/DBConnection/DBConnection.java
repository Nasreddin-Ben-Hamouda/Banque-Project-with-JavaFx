package DBConnection;
import java.sql.*;
	
	public class DBConnection {
		private static final String DB_URL = "jdbc:mysql://localhost:3306/mini_projet_java?serverTimezone=UTC";
		private static final String DB_USERNAME="root";
		private static final  String DB_PWD="";
		
		private static Connection connection=null;
		private static PreparedStatement Statement = null ;
		private static ResultSet myResultSet = null ;
		
		private DBConnection() {} 
		
		public static Connection getConnection() {
			if(connection==null) {
				try {
					connection  = DriverManager.getConnection(DB_URL, DB_USERNAME , DB_PWD);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return connection;
		}
		
		public static PreparedStatement getPreparedStatement(String SQL) {
			try {
				return Statement = getConnection().prepareStatement(SQL);
			}
			catch (Exception exc) {
				 exc.printStackTrace();
			 }
			return null;
		}
		@SuppressWarnings("static-access")
		public static PreparedStatement getPreparedStatementWithReturnedKey(String SQL) {
			try {
				return Statement = getConnection().prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
			}
			catch (Exception exc) {
				 exc.printStackTrace();
			 }
			return null;
		}
		public static long getKey(Statement s) {
			try {
				ResultSet rs = s.getGeneratedKeys();
				if (rs.next()){
						return rs.getLong(1);
					}
				}
			catch (Exception exc) {
			 exc.printStackTrace();
		 }
			return 0;
		}
		
	}
