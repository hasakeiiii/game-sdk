package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Onlybalance;
import net.sf.json.JSONObject;
import util.DebuUtil;
import util.FileUtil;
import dao.AppDao;
import dao.OnlybalanceDao;
import dao.RegisterDao;
/**
 * 查询拇指币余额的severlet
 * @author Administrator
 *
 */
public class MzBalance extends HttpServlet {


	public MzBalance() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String reqStr = request.getQueryString();
		handleRequest(reqStr, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		handleRequest(str, response);
	}

	private void handleRequest(String reqstr, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf8");
		String username,gameId;
		
		DebuUtil.log("MzBalance请求内容："+reqstr);
		JSONObject reqjson = JSONObject.fromObject(reqstr);
		username = reqjson.getString("username");
		gameId = reqjson.optString("gameid");
		RegisterDao rdao = new RegisterDao();
		model.Register register = rdao.getRegisterRecord(username);
		
		String result = "fail";
		String olbalance = "-1";
		int amount = 0;
		
		if (register!=null) {
			//当玩家存在时才去获取其拇指币余额
			amount	= register.getAccountBalance();
			result = String.valueOf(amount);
			olbalance = getOnlyBalance(username,gameId);
		}
		
		try {
			PrintWriter out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("username", username);
			json.put("userbalance", result);
			json.put("onlybalance", olbalance);
			out.println(json.toString());
			DebuUtil.log("MzBalance返回：" + json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	private String getOnlyBalance(String username,String gameId) {
		//当游戏存在且游戏的专属币折扣不为零时，才去查询专属币余额
		AppDao appdao = new AppDao();
		App app = null;
		Onlybalance olBalance = null;
		int zmount = 0;
		app = appdao.getAppRecord(gameId);
		if (app==null||app.off==0) 
			return "-1";
		DebuUtil.log("MzBalance折扣：" + app.off);
		OnlybalanceDao oldao = new OnlybalanceDao();
		olBalance = oldao.getRecord(username, gameId);
		if (olBalance==null)
			return "0";
		
		zmount = olBalance.getAccountLeft();
		System.out.println("专属币余额为："+zmount);
		if(zmount>0)
			return String.valueOf(zmount);
		else
			return "0";
			
		
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
