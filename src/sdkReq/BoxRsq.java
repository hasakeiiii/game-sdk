package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Box;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.FileUtil;
import util.StringUtil;
import dao.BoxDao;
import dao.PayDao;

public class BoxRsq extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460305344278405373L;
	
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
		JSONObject json = null;
		System.out.println(reqStr);
		if(reqStr != null)
		{
			json = JSONObject.fromObject(reqStr);
			Box box = new Box(json);
			BoxDao boxdao = new BoxDao();
			
				if(box.getStatus().equals("0"))
				{
					PayDao paydao = new PayDao();
					DebuUtil.log("支付订单号="+box.getCpparam());
					if(!StringUtil.is_nullString(box.getCpparam()))
					{
					    obj = paydao.getPayRecordByCpOrder(box.getCpparam());
					    if(obj != null)
					    {
					    	DebuUtil.log("饭盒付费成功处理");
						    obj.state = 1;
						    box.amount =obj.amount;
						    int addret = boxdao.pay(box);
							if(addret == ConstValue.OK)
							{
							    paydao.edit(obj);
							    paydao.payNotisfy(obj,true);
							}
					    }
					}
				}
			
		}
		
		
		if(obj != null)
		{
			
		/*	JSONObject rsqjson = new JSONObject();
			rsqjson.put("retmsg", "0");
			out.print(rsqjson.toString());
			System.out.println(rsqjson.toString());*/
			out.print("0");
		}
		
		out.close();
		
	}
	

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream   in=request.getInputStream();
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		if(ConstValue.RDR == 1)
		{
		  //  str = URLDecoder.decode(str,"UTF-8");	
		}
		HandleReq(str,request,response);
		
	}
}
