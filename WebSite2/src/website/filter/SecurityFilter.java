package website.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName="check",servletNames={"dispatcher"})
public class SecurityFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest request= (HttpServletRequest)req;
    	HttpServletResponse response= (HttpServletResponse)res;
    	
    	//如果在正在跑驗證帳號的程式、註冊、要求js檔 ，就直接跳過
    	String svp = request.getServletPath();
    	if(svp.endsWith("login.do") || svp.endsWith("regist.do")){
    		filterChain.doFilter(req, res);
    		return;
    	}
    	
    	//如果沒有登入就會跳轉到404頁面	
    	Integer authorise = (Integer) request.getSession().getAttribute("Authorise");
    	if(authorise==null || !authorise.equals(1)) {
    		//forward 不會重新送請求，所以不會經過filter，上面不用過濾
    		request.getRequestDispatcher("/WEB-INF/jsp/404.jsp")
    		.forward(request, response);
    	}
    	
    	filterChain.doFilter(req, res);
    	
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
