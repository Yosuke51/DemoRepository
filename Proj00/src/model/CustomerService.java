package model;

import java.util.Arrays;

import model.dao.CustomerDAOInterface;
import model.dao.CustomerDAOJdbc;

public class CustomerService {
	
		public static void main (String[] args){
			CustomerService cs = new CustomerService();
			System.out.println(cs.login("Dave", "D"));
			
			System.out.println("Change:" + cs.changepassword("Carol", "STC", "C"));
			
		}
	    private CustomerDAOInterface cdj = new CustomerDAOJdbc();
	
		public CustomerBean login(String id, String password){
			CustomerBean result = null;
			CustomerBean bean = cdj.select(id);
			
			if(bean != null){
			if(password!=null && password.length()!=0){
				byte[] pass = bean.getPassword();
				byte[] temp = password.getBytes();
				
				if(Arrays.equals(pass, temp)){
					
					return bean;
				}
				
			  }
			}
			return result;
		}
		
		public Boolean changepassword(String id, String oldpassword, String newpassword){
			
			CustomerBean bean = this.login(id, oldpassword);
			
			if(bean!=null){
			Boolean change = cdj.update(newpassword.getBytes(), bean.getEmail(), bean.getBirth(), id);
			System.out.println(cdj.select(id));
			return change;
			}
			return false;
		}
		
}
