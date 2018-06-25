package com.ipaynow;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台通知Servlet接收服务器
 * @author christ
 *
 */
public class MchFrontNotifyServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 264855279846146627L;

	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Enumeration<String> paramNameEnum = req.getParameterNames();
		while(paramNameEnum.hasMoreElements()){
			String paramName = paramNameEnum.nextElement();
			req.setAttribute(paramName, req.getParameter(paramName));
		}
		req.getRequestDispatcher("frontNotify.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}
