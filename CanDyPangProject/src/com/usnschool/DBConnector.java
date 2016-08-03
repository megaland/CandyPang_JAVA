package com.usnschool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		Statement st = null;
		String sql = "select * from userinfo where id = '" + id+"'";
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				System.out.println("아이디를 찾았습니다.");
				if(rs.getString("password").equals(password)){
					return true;
				}
				System.out.println("비밀번호가 틀렸습니다.");
				return false;
			} else {
				System.out.println("아이디를 찾지 못했습니다.");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
		
		
	}
	
	public boolean getIdExist(String id){
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from userinfo where id = '"+id+"'";
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			
			if(rs.next()){
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
		return false;
	}
	
}
