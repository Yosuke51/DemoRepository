package model.dao;

import java.sql.*;

import model.CustomerBean;

public class CustomerDAOJdbc implements CustomerDAOInterface {
	
	private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=java;user=sa;password=passw0rd";
	private static final String SELECT_BY_CUSTID = "select * from customer where custid=?";
	
	public static void main(String[] args){
		CustomerDAOInterface cdj = new CustomerDAOJdbc();
		
		 CustomerBean cb = cdj.select("Dave");
		 System.out.println(cb.getCustid());
		 System.out.println((char)cb.getPassword()[0]);
		 System.out.println(cb.getEmail());
		 System.out.println(cb.getBirth());
		 
		 Boolean s = cdj.update("E".getBytes(), "ellan@iii.com",new java.util.Date(0) , "Ellen");
		 System.out.println(s);
		
	}



	@Override
	public CustomerBean select(String custid) {
		CustomerBean cb = null;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection(url);
			ps = conn.prepareStatement(SELECT_BY_CUSTID);
			ps.setString(1, custid);
			rs = ps.executeQuery();
			
			
			while(rs.next()){
				cb = new CustomerBean();
				cb.setCustid(rs.getString(1));
				cb.setPassword(rs.getBytes(2));
				cb.setEmail(rs.getString(3));
				cb.setBirth(rs.getDate(4));
				
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (ps!=null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return cb;
	}
	private static final String UPDATE =
			"update customer set password=?, email=?, birth=? where custid=?";
	@Override
	public boolean update(byte[] password, String email, java.util.Date birth, String custid) {
		boolean result = false;
		
		try(Connection conn = DriverManager.getConnection(url);) {
			
			PreparedStatement ps = conn.prepareStatement(UPDATE);
			
			
			ps.setBytes(1, password);
			ps.setString(2, email);
			ps.setDate(3, new java.sql.Date(birth.getTime()));
			ps.setString(4, custid);
			
			int update = ps.executeUpdate();
			
			while(update>0){
				return result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
