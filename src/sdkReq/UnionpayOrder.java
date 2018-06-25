package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pay;
import model.TenPay;
import model.UnionPay;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.NetUtil;
import dao.PayDao;

public class UnionpayOrder extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8403402078366375838L;
	public void HandleReq(String reqStr,HttpServletRequest request, HttpServletResponse response)
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
		if(reqStr != null)
		{
			DebuUtil.log("请求数据"+reqStr);
			JSONObject json = JSONObject.fromObject(reqStr);
			obj = new model.Pay(json);
			String ip = NetUtil.getIpAddr(request);
			obj.ip = ip;
			PayDao dao = new PayDao();
			ret = dao.pay(obj);
		}
		if(ret == ConstValue.OK)
		{
			UnionPay unionpay = new UnionPay();
			unionpay.setPayNo(obj.getPayNo());
			unionpay.setGoodsName(obj.getCoinName());
			unionpay.setGoodsDetails("游戏币");
			unionpay.setPrice(obj.getAmount());//
			
				/////////////////////////////////////
				//ConstValue.ServerUrl = "http://www.91muzhi.com:8080";
				/////////////////////////////////////////
			//String nosify_url = ConstValue.ServerUrl+ConstValue.path+"/tennotify_url.jsp";
			//String return_url = ConstValue.ServerUrl+ConstValue.path+"/tenpayReturnUrl.jsp";
			
			//DebuUtil.log("url="+url);
			String oder_info = unionpay.getTNNO(obj.getRatio());
			PayDao payDao = new PayDao();
			Pay pay = payDao.getPayRecord(unionpay.getPayNo());
			if(pay != null)
			{
				pay.setExt(unionpay.goods_details);
				payDao.edit(pay);
			}
			//DebuUtil.log("回应数据"+oder_info);
			DebuUtil.log("oder_info="+oder_info);
			JSONObject rsqjson = new JSONObject();  
			rsqjson.put("order_info", oder_info);
			out.print(rsqjson.toString());
			
		}
		else
		{
			out.print("");
		}
		out.close();
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("UionpayOrder doGet");
		HandleReq(request.getQueryString(),request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DebuUtil.log("UionpayOrder doPost");
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
			
		HandleReq(str,request,response);
		
	}
}
