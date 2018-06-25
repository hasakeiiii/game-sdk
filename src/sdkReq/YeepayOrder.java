package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.CardPay;
import model.Pay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.NetUtil;

import com.yeepay.nonbankcard.NonBankcardPaymentResult;

import dao.AppDao;
import dao.CardPayDao;
import dao.PayDao;

public class YeepayOrder extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2213731286353712011L;

	public static int getYeediscount(String pay_type,String gamieid,int amount)
	{
		AppDao appDao = new AppDao();
		App app = appDao.getAppRecord(gamieid);
		int ret = amount; 
		int yeediscount =  app.getYeediscount();
		if (yeediscount==0) {
			yeediscount = 80;
		}
		
		if(pay_type.equals("jcard")||pay_type.equals("zycard")||pay_type.equals("tscard"))
		{
			ret = amount*yeediscount/100;
		}
		return ret;
	}
	
	public void HandleReq(String reqStr,HttpServletRequest request, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		int bOK = 0;
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.Pay payobj = null;
		JSONObject requestJson = null;
		PayDao dao = new PayDao();
		Pay payrecord = null;
		
		if(reqStr != null)
		{
			   DebuUtil.log("请求数据"+reqStr);
			
				requestJson = JSONObject.fromObject(reqStr);
				payobj = new model.Pay(requestJson);
				payobj.thir_pay_id = payobj.pay_no;
				
				String ip = NetUtil.getIpAddr(request);
				payobj.ip = ip;
				
				/*payrecord = dao.getPayRecord(json.getString("card_no"),Pay.YeePayType);
				 
				if(payrecord != null)
				{
					DebuUtil.log("充值卡卡已经提交过");
				}
				else
				{
				   ret = dao.pay(obj);
				}*/
				
				payobj.amount = getYeediscount(payobj.pay_type,payobj.game_id,payobj.amount);
				payobj.setPayType("yeepay");
				
				ret = dao.pay(payobj);
				
				if(payobj.game_id.equals("166")||payobj.game_id.equals("188")
						||payobj.game_id.equals("252")||payobj.game_id.equals("284")
						||payobj.game_id.equals("289")
						||payobj.game_id.equals("193"))//
				{
					
					JSONObject rsqjson = new JSONObject();
					rsqjson.put("code", -1);
					rsqjson.put("message", "该游戏不支持充值卡");
					rsqjson.put("pay_no", "");
					out.print(rsqjson.toString());
					
					
					//DebuUtil.log2(DateUtil.getDateTime()+":订单获取成功"+rsqjson.toString()+"\n");
					out.close();
					return;
				}
				/*if(obj.pay_type.equals("jcard"))
				{
					JSONObject rsqjson = new JSONObject();
					rsqjson.put("code", -1);
					rsqjson.put("message", "该游戏不支持骏网一卡通");
					rsqjson.put("pay_no", "");
					out.print(rsqjson.toString());
					
					out.close();
					return;
				}*/
				
				
			
		}
		if(ret == ConstValue.OK)
		{
			CardPay cardPay = new CardPay(requestJson);
			cardPay.username =payobj.getUsername();
			cardPay.pay_no = payobj.thir_pay_id;
			cardPay.goods_details = "游戏币";
			cardPay.goods_name = payobj.getCoinName();
			cardPay.oder_no = payobj.getPayNo();
			cardPay.url = ConstValue.ServerUrl+"/sdk/yeepay/callback.jsp";
			
			if(ConstValue.RDR == 1)
			{
				cardPay.url = ConstValue.RDRServer+ConstValue.path+"/yeepay/callback.jsp";
			}
			else
			{
				cardPay.url = ConstValue.ServerUrl+ConstValue.path+"/yeepay/callback.jsp";
			}
			//cardPay.pay_type="china_mobile";
			DebuUtil.log("url="+cardPay.url);
			bOK = 1;
			NonBankcardPaymentResult rs = cardPay.pay();
			if(rs != null)
			{
				//DebuUtil.log("回应数据"+oder_info);
				DebuUtil.log("提交返回参数列表");
				DebuUtil.log("业务类型[r0_Cmd：" + rs.getR0_Cmd());
				DebuUtil.log("订单的提交状态[r1_Code：" + rs.getR1_Code());
				DebuUtil.log("交易订单号[r6_Order：" + rs.getR6_Order());
				DebuUtil.log("订单的提交状态[rq_ReturnMsg：" + rs.getRq_ReturnMsg());
				
				if(rs.getR1_Code().equals("1"))
				{
				   DebuUtil.log("卡状态等于1");
				   cardPay.setRet(1);
				}
				else
				{
					cardPay.setRet(-1);
				}
				
				DebuUtil.log("保存卡记录");
				CardPayDao  cardPayDao = new CardPayDao();
				cardPayDao.addCardPay(cardPay);
				
				 DebuUtil.log("修改付费表ID");
				 PayDao paydao = new PayDao();
		    	 Pay pay = new Pay();
		    	 pay.pay_no = rs.getR6_Order();
		    	 pay.thir_pay_id = cardPay.pay_no;
		    	 pay.state = 0;//等回应
		    	 DebuUtil.log("卡号="+cardPay.no);
		    	 paydao.update(pay);
		    	    
				JSONObject rsqjson = new JSONObject();
				rsqjson.put("code", rs.getR1_Code());
				rsqjson.put("pay_no", rs.getR6_Order());
				rsqjson.put("message", rs.getRq_ReturnMsg());
				//object.getInt("code"), object.getString("message")
				out.print(rsqjson.toString());
			}
		}
		/*else if(payrecord != null)
		{
			JSONObject rsqjson = new JSONObject();
			
			CardPay carday = null;
			
			if(payrecord.getState() == 1)
			{
				DebuUtil.log("充值卡充值已经成功");
				CardPayDao  cardPayDao = new CardPayDao();
				carday = cardPayDao.getPayRecord(payrecord.getThirPayId());
			
				if(carday != null)
				{
					DebuUtil.log("找到充值卡记录");
					if(carday.getNotice() != 1)
					{
						DebuUtil.log("充值结果没有通知客户端");
						DebuUtil.log("要求客户端取通知");
						rsqjson.put("code", 1);
						rsqjson.put("pay_no", payrecord.getPayNo());
						rsqjson.put("message", "提交成功！");
						out.print(rsqjson.toString());
						bOK = 1;
					}
					else
					{
						DebuUtil.log("已经通知客户端");
					}
				}
				
			}
		}*/
		
		if(bOK == 0)
		{
			JSONObject rsqjson = new JSONObject();
			rsqjson.put("code", -1);
			rsqjson.put("message", "");
			rsqjson.put("pay_no", "");
			out.print(rsqjson.toString());
		}
		out.close();
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("YeepayOrder doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("YeepayOrder doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		if(ConstValue.RDR == 1)
		{
		   // str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,request,response);
		
	}
}
