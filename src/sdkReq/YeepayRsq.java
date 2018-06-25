package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CardPay;
import model.Mzcharge;
import model.Pay;
import net.sf.json.JSONObject;

import com.yeepay.nonbankcard.NonBankcardPaymentResult;

import dao.CardPayDao;
import dao.MzchargeDao;
import dao.PayDao;

import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;

public class YeepayRsq extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460305344278405373L;
	
	public void HandleReq(String reqStr, HttpServletResponse response)
	{
		PrintWriter out = null;
		int ret = ConstValue.Fail;
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.Pay obj = null;
		Mzcharge mzcharge = null;
		JSONObject json = null;
		if(reqStr != null)
		{
			DebuUtil.log("请求数据"+reqStr);
			json = JSONObject.fromObject(reqStr);
			String pay_no = json.getString("pay_no");
			if (pay_no.contains("mz")) {
				MzchargeDao mzchargeDao = new MzchargeDao();
				mzcharge = mzchargeDao.getPayRecord(pay_no);
			}else{
				PayDao paydao = new PayDao();
				obj = paydao.getPayRecord(json.getString("pay_no"));
			}
	   	 
		}
		
		
		if(obj != null||mzcharge!=null)
		{
			DebuUtil.log("找到卡付费数据");
			JSONObject rsqjson = new JSONObject();
			
			CardPayDao  cardPayDao = new CardPayDao();
			
			CardPay carday;
			int state ;
			if(obj != null){
				carday = cardPayDao.getPayRecordByPayNO(obj.getThirPayId());
				state = obj.state;
			}else{
				carday = cardPayDao.getPayRecordByPayNO(mzcharge.getThirPayId());
				state = mzcharge.getStatus();
			}
			
			//int state = obj.state;
			String noticestr = "";
			if((state == 1) && (carday != null))
			{
				if(carday.getNotice() != 1)
				{
					DebuUtil.log("还没有通知");
					carday.setNotice(1);
					cardPayDao.edit(carday);
					DebuUtil.log("修改通知状态");
					DebuUtil.log("通知客户端");
					//state = obj.state;
				}
				else
				{
					state = -1;
					DebuUtil.log("已经通知过了");
				}
			}
			else
			{
				
				if(carday != null)
				{
					if(carday.getRet() == 1002)
					{
						noticestr="本张卡密您提交过于频繁，请您稍后再试";
						state = -1;
					}
					else if(carday.getRet() == 1003)
					{
						noticestr="不支持的卡类型";
						state = -1;
					}
					else if(carday.getRet() == 1004)
					{
						noticestr="密码错误或充值卡无效";
						state = -1;
					}
					else if(carday.getRet() == 1006)
					{
						noticestr="充值卡无效";
						state = -1;
					}
					else if(carday.getRet() == 1007)
					{
						noticestr="卡内余额不足";
						state = -1;
					}
					else if(carday.getRet() == 1008)
					{
						noticestr="余额卡过期";
						state = -1;
					}
					else if(carday.getRet() == 1010)
					{
						noticestr="此卡正在处理中";
						state = -1;
					}
					else if(carday.getRet() == 10000)
					{
						noticestr="未知错误";
						state = -1;
					}
					else if(carday.getRet() == 2005)
					{
						noticestr="此卡已使用";
						state = -1;
					}
					else if(carday.getRet() == 2006)
					{
						noticestr="卡密在系统处理中";
						state = -1;
					}
					else if(carday.getRet() == 2007)
					{
						noticestr="该卡为假卡";
						state = -1;
					}
					else if(carday.getRet() == 2008)
					{
						noticestr="该卡种正在维护";
						state = -1;
					}
					else if(carday.getRet() == 7)
					{
						noticestr="卡号卡密或卡面额不符合规则";
						state = -1;
					}
				}
				DebuUtil.log("回应失败原因:"+noticestr);
			}
			rsqjson.put("code", state);
			rsqjson.put("message", noticestr);
			//object.getInt("code"), object.getString("message")
			out.print(rsqjson.toString());
			
		}
		else
		{
			
			JSONObject rsqjson = new JSONObject();
			rsqjson.put("code", -1);
			rsqjson.put("message", "");
			out.print(rsqjson.toString());
		}
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("YeepayRsq doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("YeepayRsq doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		
		if(ConstValue.RDR == 1)
		{
		    //str = URLDecoder.decode(str,"UTF-8");	
		}
		
		HandleReq(str,response);
		
	}
}
