package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pay;
import net.sf.json.JSONObject;
import util.DebuUtil;

import com.alipay.sign.Base64;

import dao.PayDao;

public class TCPayNotisfy extends HttpServlet {
	
		
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6548900693726625169L;


	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
	{
		//DebuUtil.log3("TCPayNotisfy="+request.getQueryString());
		
		PrintWriter out = null;
		response.setContentType("text/html;charset=utf8");
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//reqStr = request.getQueryString();
		//debugqStr = reqStr;
		
		
		//out.print(debugqStr);
		//DebuUtil.log("request="+request.getQueryString());
		String method = request.getParameter("method");
		String key="3c86f9aa1c5c330196b68d794ecf8d8c";//这个根据SDK分配填写出
		
		if(method.equals("check"))
		{
			String cp_order_id = request.getParameter("cp_order_id");
			String order_time = request.getParameter("order_time");
			String SerialNo = request.getParameter("correlator");
			String sign = request.getParameter("sign");
	         
			String ret = "fail";
			
			String mysign;//=Rsa.getMD5(content+"&key="+key);
			
			//DebuUtil.log3("cp_order_id="+cp_order_id+" SerialNo="+SerialNo+" sign="+sign +" order_time"+order_time);
			mysign=util.Rsa.getMD5(cp_order_id+SerialNo+order_time+method+key);
			                     //MD5(cp_order_id+correlator+order_time+method+appKey)
			//DebuUtil.log3("mysign="+mysign);
			
			if(mysign.equals(sign))//签名正确,做业务逻辑处理
			{
				  
				//成功回success		
				PayDao paydao = new PayDao();
				Pay pay = paydao.getPayRecord(cp_order_id);
				if(pay != null)
				{
					ret = "<sms_pay_check_resp>";
					ret += "<cp_order_id>" + cp_order_id + "</cp_order_id>";
				    ret += "<correlator>" + SerialNo + "</correlator>";
				    ret += "<game_account>" + pay.getUsername() + "</game_account>";
				    ret += "<fee>" + pay.getAmount()/100 + "</fee>";		    
				    ret += "<if_pay>0</if_pay>";			       
				    ret += "</sms_pay_check_resp>";			        
					/*ret = "<cp_notify_resp>";
					ret += "<h_ret>" + "0" + "</h_ret>";
					ret += "<cp_order_id>" + cp_order_id + "</cp_order_id>";
					ret += "</cp_notify_resp>";*/
				    //////////////////////////////////////////////
				    //DebuUtil.log3("rsq="+ret);
				    out.print(ret);
				}
			}
		}
		else if(method.equals("callback"))
		{
			String cp_order_id = request.getParameter("cp_order_id");
			String SerialNo = request.getParameter("correlator");
			String result_code = request.getParameter("result_code");
			String result_desc = request.getParameter("result_desc");
			String fee = request.getParameter("fee");
			String pay_type = request.getParameter("pay_type");
			String sign = request.getParameter("sign");
			
			String ret = "fail";
			
			String mysign;//=Rsa.getMD5(content+"&key="+key);
			//OrderId, SerialNo, ResultCode, PayFee.ToString(), PayType, InterfaceId.ToString(), AppSecret
			mysign=util.Rsa.getMD5(cp_order_id+SerialNo+result_code+fee+pay_type+method+key);
			
			//DebuUtil.log3("mysign="+mysign);
			
			if(mysign.equals(sign))//签名正确,做业务逻辑处理
			{
				  
				if(result_code.equals("00"))//成功业务逻辑处理
				{
					//DebuUtil.log3("cp notisfy");
					PayDao paydao = new PayDao();
		        	paydao.payNotisfy(cp_order_id,SerialNo,1);
				}
				
				//成功回success		
				ret = "<cp_notify_resp>";
				ret += "<h_ret>" + "0" + "</h_ret>";
				ret += "<cp_order_id>" + cp_order_id + "</cp_order_id>";
				ret += "</cp_notify_resp>";
				//DebuUtil.log3("rsq="+ret);
				out.print(ret);
			}
			
			
		}
		out.close();
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HandleReq(request,response);

   
	}
	


	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DebuUtil.log("init doPost");
		HandleReq(request,response);
	
	}
}
