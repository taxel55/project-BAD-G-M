import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
	String USERNAME = "root";
	String PASSWORD = "";
	String DATABASE = "g&m";
	String HOST = "localhost : 3306";
	String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
	
	java.sql.Connection con;
	Statement st;
	
	ResultSet res;
	ResultSetMetaData resM;
	
	private static Connection conn;
	
	//ambil data
	ResultSet execQuery(String q) {
		try {
			res = st.executeQuery(q);
			resM = res.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	//add data 
	public void execUpdate(String q) {
		try {
			st.executeUpdate(q);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getInstance () {
		if(conn == null) return new Connection();
		return conn;
		
	}
	
	public Connection() {
		try {
//			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION,USERNAME,PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}