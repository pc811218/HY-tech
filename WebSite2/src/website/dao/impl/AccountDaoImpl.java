package website.dao.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import website.common.ParseXml;
import website.dao.AccountDao;

@Service("accountDao")
public class AccountDaoImpl implements AccountDao{
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//取得user資料，可以自行編寫修改，請使用JdbcTemplate做操作
	@Override
	public List<Map<String, Object>> getAccountByName(String name, String password) throws Exception {
		List<Map<String, Object>> results = 
			    (List<Map<String, Object>>) jdbcTemplate.queryForList(
			    		ParseXml.getSqlByName("Account.checkAccount"),name,password);
		return results;
		
	}
	
	//新增一筆資料
	@Override
	public int InsertOneData(String account, String password, String name, Date birthday, String phone)
			throws Exception {
		
		int upd = jdbcTemplate.update(ParseXml.getSqlByName("Account.addAccount"),
				account,password, name,birthday,phone);
		return upd;
	}
	
	
}
