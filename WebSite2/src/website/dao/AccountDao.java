package website.dao;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface AccountDao {
	public List<Map<String, Object>> getAccountByName(String name, String password) throws Exception;
	public int InsertOneData(String account, String password,String name,Date birthday,String phone) throws Exception;
	
}
