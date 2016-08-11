package usnschool.com.candypangproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConnector {
	private static DBConnector connector = new DBConnector();
	
	public static DBConnector getConnector(){
		
		return connector;
	}
	
	Connection con;
	
	public DBConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/candypang", "root", "1234");
			
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
				System.out.println("�븘�씠�뵒瑜� 李얠븯�뒿�땲�떎.");
				if(rs.getString("password").equals(password)){
					return true;
				}
				System.out.println("鍮꾨�踰덊샇媛� ���졇�뒿�땲�떎.");
				return false;
			} else {
				System.out.println("�븘�씠�뵒瑜� 李얠� 紐삵뻽�뒿�땲�떎.");
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
	
	public void updateScore(String id, int score){
		Statement st = null;
		String sqlget = "select score from userinfo where id = '"+id+"'";
		String sqlput = "update userinfo set score = "+score+" where id = '"+id+"'";
		ResultSet rs = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sqlget);
			rs.next();
			if(rs.getInt("score") < score){
				st.execute(sqlput);
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
	}
	public int getMyRank(String id){
		ResultSet rs = null;
		Statement st = null;
		String sql = "select count(*) count from (select * from userinfo where score > (select score from userinfo where id = '"+id+"')) selected";
		int rank = 0 ;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
			rank = rs.getInt("count");
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
		
		
		return rank;
		
	}
	
	public ArrayList<RankerData> getRankerList(){
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from userinfo order by score desc limit 30";
		ArrayList<RankerData> rankerlist = new ArrayList<>();
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			int count = 0;
			while(rs.next()){
				count++;
				RankerData rankerdata = new RankerData();
				rankerdata.setNum(rs.getInt("num"));
				rankerdata.setRank(count);
				rankerdata.setId(rs.getString("id"));
				rankerdata.setScore(rs.getInt("score"));
				rankerlist.add(rankerdata);
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
		
		
		return rankerlist;
	}
	
	public int getMyScore(String id){
		Statement st = null;
		ResultSet rs = null;
		String sql = "select * from userinfo where id = '"+id+"'";
		int score = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);
			rs.next();
			score = rs.getInt("score");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		
		return score;
	}
	
}
