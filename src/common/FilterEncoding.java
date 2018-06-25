package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/*
URIEncoding="UTF-8"
<filter>
	<filter-name>encodingFilter</filter-name>
	<filter-class>common.EncodingFilter</filter-class>
</filter>
<filter-mapping>
	<filter-name>encodingFilter</filter-name>
	<url-pattern>/*</url-pattern>
</filter-mapping>
*/
public class FilterEncoding implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		arg0.setCharacterEncoding("utf-8");
		azul.BaseDao.setContext((javax.servlet.http.HttpServletRequest)arg0);
		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
