package com.usnschool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	private static DBConnector connector = new DBConnector();
	
	public static DBConnector getConnector(){
		
		return connector;
	}
	
	Connection con;
	
	public DBConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/candypang", "root", "nfc123");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void insertUserInfo(String id, String password){
		Statement st = null;
		String sql = "insert into userinfo (id, password) values('"+id+"', '"+password +"')";
		try {
			st = con.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		
	}
	
	public boolean getCheckIdPassword(String id, String password){
		
		return true;
	}
	
}
