package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.sign.Base64;

//import model.GCGameResultPacket;
import model.Pay;
//import model.SSFreshChargePacket;
//import model.bufStream;
import net.sf.json.JSONObject;
//import uc_sdk.Util;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.FileUtil;
import util.MySocket;
import util.Rsa;
import util.StringUtil;


public class notify_muzhi extends HttpServlet {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -1908233266085982667L;

	static String GM_Server="127.0.0.1";
	static int port = 8002;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	public void HandleReq(HttpServletRequest request, HttpServletResponse response)
	{
		DebuUtil.log("notify_muzhi");
		
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
		String content = request.getParameter("content");
		String sign = request.getParameter("sign");
		String key="zty_156";//这个根据SDK分配填写出
		String ret = "fail";
		
		//content="eyJ1Ijoi6ZK755+zIn0=";
		 String name = DebuUtil.printfbuf(Base64.decode(content));
		 DebuUtil.log("\nname2="+name+"\n");
		try {
			content = new String(Base64.decode(content),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DebuUtil.log("\ncontent="+content+"\n");
		
		
		
		// String name = DebuUtil.printfbuf(Base64.decode(content));
		// DebuUtil.log("\nname2="+name+"\n");
		 
		DebuUtil.log("content="+content);//7b,22,75,22,3a,22,e9,92,bb,e7,9f,b3,22,7d,
		
		String mysign;//=Rsa.getMD5(content+"&key="+key);
		 mysign=util.Rsa.getMD5(content+"&key="+key);
		
		DebuUtil.log("mysign="+mysign);
		
		//if(mysign.equals(sign))//签名正确,做业务逻辑处理
		{
			  DebuUtil.log("sign ok");
			  String pay_no;
			  String username;
			  String device_id;
			  String game_id;
			  String server_id;
			  String cp_order_id;
			  String pay_type;
			  String thir_pay_id;
			  int amount;
			  int payStatus;
			  int user_id;
			 
			  JSONObject json = JSONObject.fromObject(content);
			  
			  
			  pay_no=json.getString("pay_no");
			  username=json.getString("username");
			  device_id=json.getString("device_id");
			  server_id=json.getString("server_id");
			  game_id=json.getString("game_id");
			  cp_order_id=json.getString("cp_order_id");
			  pay_type=json.getString("pay_type");
			  amount=json.getInt("amount");
			  payStatus = json.getInt("payStatus");
			  user_id=json.getInt("user_id");
			//成功回success		
			  //DebuUtil.log("sign ok2");
			  /////////////////////////////////////////////
			 // byte [] bufs = new byte[SSFreshChargePacket.packetsize];
			 /* int index = 0;
			  long Guid = user_id;
			  int Stone = (amount/100)*10;		//充值宝石
			  int TotalPPMoney = amount/100;	//总充值pp币
			  int m_PlayerID = 1001;*/
			 // bufStream bufstr = new bufStream(bufs,0);
				
			 // SSFreshChargePacket chpacket = new SSFreshChargePacket();
			  //chpacket.m_Guid = ;
			  //chpacket.writePacket(bufstr);
			  
			  //byte [] retbuf = null;
			 /* if(payStatus == 0)
			  {
				  SSFreshChargePacket chpacket = new SSFreshChargePacket();
				  chpacket.m_UID = String.format("%d", user_id);
				  chpacket.m_PlatformType = SSFreshChargePacket.EN_TERRACE_TYPE_MUZHI;
				  chpacket.m_PlatformOrderNo = pay_no;
				  chpacket.m_ServerOrderNo = cp_order_id;
				  chpacket.m_ServerID = Integer.valueOf(server_id);
				  chpacket.m_RechargeNum = amount;
				 // retbuf = MySocket.sendPacket(chpacket,GM_Server, port,GCGameResultPacket.packe_tsize);//127.0.0.1
				  DebuUtil.log("send packet");
				  
				 try {
					 GM_Server= Util.getConfig("server.Properties","server"+chpacket.m_ServerID);
					 port= Integer.valueOf(Util.getConfig("server.Properties","port"+chpacket.m_ServerID));
				 } catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }
				  DebuUtil.log("GM_Server="+GM_Server);
				  
				  int rerint = MySocket.sendPacket(chpacket,GM_Server, port);//127.0.0.1
				  if(rerint == 200)
				  {
					  ret = "success";
				  }
				  else
				  {
					  chpacket.dump2();
				  }
			  }*/
			  
			 
			  DebuUtil.log("ret="+ret);
			  //////////////////////////////////////////////
			  out.print(ret);
		}

		
		out.close();
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("init doGet");
		//HandleReq(request.getQueryString(),response);
		HandleReq(request,response);

        //out.close();
	}
	


	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DebuUtil.log("init doPost");
		HandleReq(request,response);
		//InputStream   in=request.getInputStream();
		//byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		//String str = new String(buf,"UTF-8");
		//BufferedReader   br=new BufferedReader(in); 
		//HandleReq(str,response);
	}
}
