package website.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import website.common.Encryption;
import website.model.AccountModel;

@Controller
public class LoginController {
	
	private AccountModel accountModel;
	
    private static final String accountRex = "^[A-Za-z][\\w\\.]{5,11}$"; //英文開頭、後面接英文/數字/_(6~12)
    private static final String passwordRex = "^[A-Za-z0-9@_]{6,12}$"; //英文/數字(6~12)
    private static final String phoneRex = "^09[0-9]{8}$"; 			//09xxxxxxxx(10)
    private static final String nameRex = "^[A-Za-z\\u4e00-\\u9fa5]{2,10}$"; //英文/中文(2~10)
	
	@Resource(name = "accountModel")
	public void setAccountModel(AccountModel accountModel) {
		this.accountModel = accountModel;
	}

	// 處理登入動作
	@RequestMapping("/login.do")
	public ModelAndView handleLogin(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		String name = req.getParameter("userName");
		String password = req.getParameter("password");
		
		// 檢核使用者登入帳密
		//去除空白
		name = name.trim();
		password = password.trim();
		
		//判斷是否為有輸入
		if(name.length()!=0&&password.length()!=0){
			//把輸入的密碼轉換為MD5
			String pswdMD5 = Encryption.encrypt("MD5", password);
			//用轉換後的密碼去資料庫比對
			Map<String, Object> resultMap = accountModel.checkAccount(name, pswdMD5);
			//帳密正確 resultMap有抓到東西
			if(resultMap!=null){
				req.getSession().setAttribute("Authorise", 1);
				return new ModelAndView("index","name",resultMap.get("name"));
			//帳密錯誤
			} else{
				return new ModelAndView("login","errMsg","不存在的帳號或密碼有誤!");
			}
			
		//沒有輸入任何值
		} else{
			return new ModelAndView("login","errMsg","帳號或密碼不可為空白");
			
		}
		
		// 請實做：根據登入是否成功，回傳相對應ModelView
		
	}

	@RequestMapping("/industryList.do")
    public ModelAndView doIndustry(HttpServletRequest req,
            HttpServletResponse resp) throws Exception {
        return new ModelAndView("industryList");
    }

    @RequestMapping("/logout.do")
	public ModelAndView handleLogout(HttpServletRequest arg0,
			HttpServletResponse arg1) throws Exception {
		
		arg0.getSession().removeAttribute("Authorise");
		
		return new ModelAndView("login");
		
	}
	
    @RequestMapping("/regist.do")
    public ModelAndView handleRegist(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
    		
    		req.setCharacterEncoding("utf-8");
    		
    		//用來存放錯誤的
    		List<String> errMsg = new ArrayList<String>(); 
    		
    		String account = req.getParameter("account");
    		String password = req.getParameter("password");
    		String name = req.getParameter("name");
    		String birth = req.getParameter("birth");
    		String phone = req.getParameter("phone");
    		
    		//去除空白
    		account = account.trim();
    		password = password.trim();
    		name = name.trim();
    		birth = birth.trim();
    		phone = phone.trim();
    		
    		Date bdDate = null;

    		
    		if(account.length()==0||!account.matches(accountRex))
    			errMsg.add("帳號不符合規定");
    		if(password.length()==0||!password.matches(passwordRex))
    			errMsg.add("密碼不符合規定");
    		if(name .length()==0||!name.matches(nameRex))
    			errMsg.add("姓名不符合規定");
    		if(birth.length()!=0){
    			try {
    				bdDate = Date.valueOf(birth);
    			} catch(Exception e){
    				errMsg.add("生日不符合格式");
    			}
    		}
    		
    		if(phone.length()==0||!phone.matches(phoneRex))
    			errMsg.add("電話不符合規定");
    		
    		//errMsg.add("test不符合規定");
    		//errMsg.add("test2不符合規定");
    		
    		
    		//####若有錯，返回給login，顯示錯誤####
    		if(!errMsg.isEmpty())
    			return new ModelAndView("login","error",errMsg);
    		
    		
    		//+++++資料無誤-開始新增++++++
    		int udp = accountModel.addAccount(account, password, name, bdDate, phone);
    		
    		
    		
    	return new ModelAndView("login","isRegistOK",udp);
    }
    
}
