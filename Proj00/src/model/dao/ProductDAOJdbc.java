package model.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.ProductBean;

public class ProductDAOJdbc implements ProductDAOInterface {
	
	private List<ProductBean> beans = new ArrayList<ProductBean>(); 
	@Override
	public List<ProductBean> getBeans() {
		return beans;
	}
	
	public static void main(String[] args){
		 	ProductDAOInterface pdj = new ProductDAOJdbc();
		 	System.out.println("====================="+"SELECT BY ID:");
		 	System.out.println(pdj.select(5)); // SELECT_BY_ID
		 	
		 	System.out.println("====================="+"After SELECT ALL:");
			for(ProductBean bean : pdj.select()) {   // SELECT_ALL
				System.out.println(bean);
			}
			
			System.out.println("====================="+"After INSERT:");
			System.out.println(pdj.insert(new ProductBean(new String[]{"11","HAM","25.6","2015-06-05","200"})));
			
			System.out.println("====================="+"After Update:");
			System.out.println(pdj.update("CoffeeSmith",20,new java.util.Date(), 5, 4));  //UPDATE
			
			
			System.out.println("====================="+"After Delete:");
			System.out.println(pdj.delete(10));
	}
	
	public String url = "jdbc:sqlserver://localhost:1433;databaseName=java;user=sa;password=passw0rd";
	
	private static final String SELECT_BY_ID =
			"select * from product where id=?";
	
	@Override
	public ProductBean select(int id) {
		ProductBean result = null;
	
		try(Connection conn = DriverManager.getConnection(url);) {
			
			PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				String[] array = new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
				result = new ProductBean(array);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static final String SELECT_ALL =
			"select * from product";
	
	@Override
	public List<ProductBean> select() {
		List<ProductBean> result = null;
		
		try(Connection conn = DriverManager.getConnection(url);) {
			
			PreparedStatement ps = conn.prepareStatement(SELECT_ALL);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String[] array = new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
				ProductBean s = new ProductBean(array);
				beans.add(s);
				result = getBeans();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	private static final String INSERT =
			"insert into product (id, name, price, make, expire) values (?, ?, ?, ?, ?)";
	
	@Override
	public ProductBean insert(ProductBean bean) {
		ProductBean result = null;
		
		try(Connection conn = DriverManager.getConnection(url);) {
			
			PreparedStatement ps = conn.prepareStatement(INSERT);
			ps.setInt(1,bean.getId());
			ps.setString(2,bean.getName());
			ps.setDouble(3,bean.getPrice());
			ps.setDate(4,new java.sql.Date(bean.getMake().getTime()));
			ps.setInt(5,bean.getExpire());
			int num = ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT * FROM product");
			ResultSet rs = ps.executeQuery();	
			while(rs.next()){
				String[] array = new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
//				ProductBean s = new ProductBean(array);
//				System.out.println(s.toString());
//				System.out.println("=====================INSERT:");
				result = new ProductBean(array);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	private static final String UPDATE =
			"update product set name=?, price=?,make =?, expire=? where id=?";
	
	@Override
	public ProductBean update(String name, double price, java.util.Date make, int expire, int id) {
		ProductBean result = null;
		
		try(Connection conn = DriverManager.getConnection(url);) {
			
			PreparedStatement ps = conn.prepareStatement(UPDATE);
			ps.setString(1,name);
			ps.setDouble(2,price);
			SimpleDateFormat timeFormat =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String s = timeFormat.format(make);
			ps.setString(3,s);
			ps.setInt(4,expire);
			ps.setInt(5,id);
			int num = ps.executeUpdate();
			
			ps = conn.prepareStatement("SELECT * FROM product where id=?");	
			ps.setInt(1,id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String[] array = new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)};
				result = new ProductBean(array);
			}
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	private static final String DELETE =
			"delete from product where id=?";
	
	@Override
	public boolean delete(int id) {
		boolean result = false;
		
		try(Connection conn = DriverManager.getConnection(url);) {
			
			PreparedStatement ps = conn.prepareStatement(DELETE);
			ps.setInt(1, id);
			int rs = ps.executeUpdate();
			

			ps = conn.prepareStatement("SELECT * FROM product");
			ResultSet is = ps.executeQuery();
			
			while(is.next()){
				String[] array = new String[]{is.getString(1),is.getString(2),is.getString(3),is.getString(4),is.getString(5)};
				ProductBean s = new ProductBean(array);
				System.out.println(s.toString());
			}
			
			while(rs>0){
				return result = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
