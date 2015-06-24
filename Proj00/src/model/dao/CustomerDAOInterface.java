package model.dao;

import model.CustomerBean;

public interface CustomerDAOInterface {

	public abstract CustomerBean select(String custid);

	public abstract boolean update(byte[] password, String email,
			java.util.Date birth, String custid);

}