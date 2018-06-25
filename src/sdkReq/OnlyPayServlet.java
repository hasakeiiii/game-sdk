package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Onlybalance;
import model.Register;
import net.sf.json.JSONObject;
import dao.OnlybalanceDao;
import dao.PayDao;
import dao.RegisterDao;

import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.Rsa;

public class OnlyPayServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		InputStream in = req.getInputStream();
		byte[] buf = FileUtil.getByteArrayFromInputstream(in, -1);

		String str = new String(buf, "UTF-8");
		HandleReq(str, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HandleReq(req.getQueryString(), resp);
	}
	
	private void HandleReq(String reqStr, HttpServletResponse response) {
		int result = ConstValue.Fail;
		int cost = 0;
		response.setContentType("text/html;charset=utf8");

		model.Pay payObj = null;
		if (reqStr != null) {
			DebuUtil.log("OnlyPayServlet请求数据" + reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			
			JSONObject json2 = json.getJSONObject("content");
			payObj = new model.Pay(json2);
			RegisterDao redao = new RegisterDao();
			Register register = redao.getRegisterRecord(payObj.username);
			String olret = "-1";
			if (register != null) {
				if (checkSign(json, register)) {
					//删减账户余额
					olret = OnlybalanceDao.minusOnlyBalance(payObj);
					if (!olret.equals("-1")) {
						payObj.state = 1;
						cost = payObj.amount/100;
						PayDao paydao = new PayDao();
						result = paydao.pay(payObj);
						paydao.payNotisfy(payObj, true);
					}
				}
			}
		}
		
		if (result == ConstValue.OK) {
			try {
				result = 200;
				PrintWriter out = response.getWriter();
				JSONObject json = new JSONObject();
				json.put("cost", cost);
				json.put("ret", result);
				out.println(json.toString());
				DebuUtil.log("OnlyPayServlet返回：" + json.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
/**
 * 验证sign是否正确
 * @param json 
 * @param register
 * @return
 */
	private boolean checkSign(JSONObject json, Register register) {
		String content = "";
		String sign = "";
		String msign;
		boolean ret = false;
		
		JSONObject json3 = json.getJSONObject("content");
		content = json3.toString();
		sign = json.getString("sign");
		
		String str = content + "&" + register.pass;
		byte[] bt = new byte[1];
		try {
			bt = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		msign = Rsa.getMD5(bt);
		DebuUtil.log("sign：" + msign);
		if (msign.equals(sign)) {
			ret = true;
		}
		return ret;
		
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

}
