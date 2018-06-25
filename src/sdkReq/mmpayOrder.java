package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.App;
import model.Cooperation;
import model.MmPay;
import model.Pay;
import net.sf.json.JSONObject;
import dao.AppDao;
import dao.CooperationDao;
import dao.MmPayDao;
import dao.PayDao;
import dao.RegisterDao;

import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;

public class mmpayOrder extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8462750081700830800L;

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
		Pay pay = null;
		PayDao dao = new PayDao();
		
		if(reqStr != null)
		{
			DebuUtil.log3("mmpayOrder="+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			obj = new model.Pay(json);
			
			MmPayDao mmpaydao = new MmPayDao();
			MmPay mmpay= mmpaydao.getPayRecord(obj.thir_pay_id);
			//pay = dao.getPayRecord(obj.thir_pay_id,"mmpay");		
			
			if((mmpay != null) && (mmpay.getRet() >= 0))
			{
				if(mmpay.amount != obj.amount)
				{
					obj.amount = mmpay.amount;
				}
				//obj.state = 1;
				DebuUtil.log("修改支付状态 ");
				pay = dao.getPayRecord(mmpay.getTradeNo(),"mmpay");		
				if(pay == null)
				{
					obj.state = 1;//不下发
				}
				else
				{
					
					/*AppDao appdao = new AppDao();
					App appinfo = appdao.getAppRecord(obj.game_id);
	    	    	
					if(StringUtil.is_nullString(appinfo.getUrl()))
					{
						pay.device_id=obj.device_id;	    	    	
		    	    	pay.game_id=obj.game_id;
		    	    	pay.server_id=obj.server_id;
		    	    	pay.cp_order_id=obj.cp_order_id;
		    	    	pay.username=obj.username;	
		    	    	pay.packet_id = obj.packet_id;	    	    		
		    	    	pay.ratio=obj.ratio;
		    	    	pay.coin_name=obj.coin_name;
		    	    	pay.pay_type = obj.pay_type;	    	    	
		    	    	pay.type = obj.type;
		    	    	//pay.state = 0;
		    	    	pay.business_no = obj.business_no;
		    	    	pay.channel_no = obj.channel_no;
		    	    	dao.getPayPacketID(pay);
		    	    	dao.edit(pay);
					}*/
	    	    	//pay.ncount = 0;
	    	    	
				}
				
			}
			
			if(pay == null)
			ret = dao.pay(obj);
			
		}
		
		if(ret == ConstValue.OK)
		{
			 if(obj.state == 1)
			 {
				if(ConstValue.OPTIMIZE == 1)
		        {
		            //PayDao paydao = new PayDao();
		            dao.payNotisfy(obj);
		        }
				else
				{
				    int result = obj.rsqCallbackUrl(0);
				    DebuUtil.log("通知结果,result="+result);
				    if( result== 1)
		    	    {
				    	DebuUtil.log("保存通知结果,pay.id="+obj.id);
				    	dao.update(obj);
		    	    }
				}
			 }
	    	    
			JSONObject rsqjson = new JSONObject();  
			
			rsqjson.put("ret", 1);
			rsqjson.put("order_no", obj.pay_no);
			out.print(rsqjson.toString());
		}
		else
		{
			JSONObject rsqjson = new JSONObject();          
			rsqjson.put("ret", -1);
			rsqjson.put("order_no", "");
			out.print(rsqjson.toString());
		}
		out.close();
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("mmpayOrder doGet");
		HandleReq(request.getQueryString(),response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("mmpayOrder doPost");
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
