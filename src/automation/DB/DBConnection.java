package automation.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/Flook";
//27.117.242.131
	static final String USERNAME = "root";
	static final String PASSWORD = "siddid";
	private static DBConnection instance;
	static {
		instance = new DBConnection();
	}
	public static DBConnection getInstance(){
		return instance;
	}
	Connection conn = null;
	public Connection getConn(){
		return this.conn;
	}
	private DBConnection(){
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
			System.out.println("\n- MySQL Connection");
			stmt = conn.createStatement();
			
			String sql;
			sql = "SELECT * FROM ingredient_category_tb";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				String groupName = rs.getString("ingredient_category_code");
				

				System.out.print("\n** Group : " + groupName);
				
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se1){
			se1.printStackTrace();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(stmt!=null)
					stmt.close();
			}catch(SQLException se2){
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("\n\n- MySQL Connection Close");
	}
}