package common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyCookie {

	public String[] getCookie(HttpServletRequest request){
		String[] result=new String[]{"","",""};
		String usernameCookie = null;
		String passwordCookie = null;
		String typeCookie=null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        if ("SESSION_LOGIN_USERNAME".equals(cookie.getName())) {
		           usernameCookie = cookie.getValue(); 
		        }
		        if ("SESSION_LOGIN_PASSWORD".equals(cookie.getName())) {
		           passwordCookie = cookie.getValue();
		        }
		        if ("SESSION_LOGIN_TYPE".equals(cookie.getName())) {
		        	typeCookie= cookie.getValue();
		        }
		    }
		    if (usernameCookie != null && passwordCookie != null) { 
		    	result=new String[3];
		    	result[0]=usernameCookie;
		    	result[1]=passwordCookie;
		    	result[2]=typeCookie;
		     }
		}
		return result;
	}
	
	public void setCookie(HttpServletRequest request,HttpServletResponse response,String username,String userpwd,String usertype){
	    Cookie cookie = new Cookie("SESSION_LOGIN_USERNAME", username); 
	    cookie.setMaxAge(99999999);
	    response.addCookie(cookie);

	    cookie = new Cookie("SESSION_LOGIN_PASSWORD", userpwd); 
	    cookie.setMaxAge(99999999);
	    response.addCookie(cookie);

	    cookie = new Cookie("SESSION_LOGIN_TYPE", usertype); 
	    cookie.setMaxAge(99999999);
	    response.addCookie(cookie); 
	}
	
	public void setCookie(HttpServletRequest request,HttpServletResponse response,String username,String userpwd,int time,String usertype){
	    Cookie cookie = new Cookie("SESSION_LOGIN_USERNAME", username);
	    cookie.setMaxAge(time);
	    response.addCookie(cookie);
	    cookie = new Cookie("SESSION_LOGIN_PASSWORD", userpwd); 
	    cookie.setMaxAge(time);
	    response.addCookie(cookie);
	    cookie = new Cookie("SESSION_LOGIN_TYPE", userpwd); 
	    cookie.setMaxAge(time);
	    response.addCookie(cookie);
	}
}
