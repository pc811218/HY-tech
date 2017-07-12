package website.model.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import website.common.Encryption;
import website.dao.AccountDao;
import website.model.AccountModel;

@Service("accountModel")
public class AccountModelImpl implements AccountModel{
	
	private AccountDao accountDao;
	
	@Resource(name = "accountDao")
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	// 檢查使用者登入帳號密碼
	@Override
	public Map<String, Object> checkAccount(String name, String password) {
		List<Map<String, Object>> list = null;
		Map<String, Object> map = null;
		try {
			list = accountDao.getAccountByName(name, password);
		} catch (Exception e) { 
			//錯誤處理 ， 有問題直接回傳NULL的Map
			return map;
		}
		
		if (list!=null&&list.size()!=0) {
			map = list.get(0);
		}
		
		return map;
	}

	@Override
	public int addAccount(String account, String password, String name, Date birthday, String phone) {
		
		int udp = 0;
		
		try {
			//進資料庫前把密碼轉換為MD5加密
			String pswdMD5 = Encryption.encrypt("MD5", password);
			udp = accountDao.InsertOneData(account, pswdMD5, name, birthday, phone);
		} catch (Exception e) {
			//錯誤處理 ， 有問題直接回傳0
			return 0;	
		}
		
		return udp;
	}
}
