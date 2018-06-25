package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import azul.TestUrlThread;

import model.Cooperation;
import net.sf.json.JSONObject;
import dao.ActivateDao;
import dao.CooperationDao;

public class TestUrl extends HttpServlet {
	public TestUrl() {
		super();
	}
	private static final long serialVersionUID = 1L; 

	public void init() throws ServletException {
		TestUrlThread t = new TestUrlThread();
		t.start();
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	
	

}
