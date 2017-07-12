package website.model;

import java.sql.Date;
import java.util.Map;

public interface AccountModel {
	
	public Map<String, Object> checkAccount(String name, String password) throws Exception;
	public int addAccount(String account, String password,String name,Date birthday,String phone) throws Exception;
	
}
