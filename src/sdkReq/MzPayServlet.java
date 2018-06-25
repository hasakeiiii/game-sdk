package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Register;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.Rsa;
import dao.PayDao;
import dao.RegisterDao;

public class MzPayServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public MzPayServlet() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DebuUtil.log("MzPayServlet doGet");
		HandleReq(request.getQueryString(), response);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DebuUtil.log("MzPayServlet doPost");
		InputStream in = request.getInputStream();
		byte[] buf = FileUtil.getByteArrayFromInputstream(in, -1);

		String str = new String(buf, "UTF-8");
		HandleReq(str, response);
	}

	
	private void HandleReq(String reqStr, HttpServletResponse response) {

		int result = ConstValue.Fail;
		int cost = 0;
		response.setContentType("text/html;charset=utf8");

		model.Pay payObj = null;
		if (reqStr != null) {
			DebuUtil.log("MzPayServlet请求数据" + reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			String content = "";
			String sign = "";

			JSONObject json2 = json.getJSONObject("content");
			
			content = json2.toString();
			sign = json.getString("sign");

			payObj = new model.Pay(json2);
			RegisterDao redao = new RegisterDao();
			Register register = redao.getRegisterRecord(payObj.username);
			String msign = "";
			if (register != null) {

				String str = content + "&" + register.pass;
				byte[] bt = new byte[1];
				try {
					bt = str.getBytes("utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				msign = Rsa.getMD5(bt);
				DebuUtil.log("sign：" + msign);

				if (sign.equals(msign)) {
					//int abalance = register.getAccountBalance();
					cost = payObj.amount/100 ;//因为payObj.amount单位是分
					//int accountleft = abalance - cost;
                    if(redao.pay(payObj.username, cost))
                    {
						//DebuUtil.log("支付前：" + abalance + "   支付过后：" + accountleft);
	
						//register.setAccountBalance(accountleft);
						//String ret = "";
						//ret = redao.edit(register);
						//if (ret.equals("1")) {
						//	payObj.state = 1 ;
						//}
                    	payObj.state = 1 ;
                    	
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
				DebuUtil.log("MzPayServlet返回：" + json.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}


}
