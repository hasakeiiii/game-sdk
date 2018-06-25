package sdkReq;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CooperationDao;
import dao.MmPayDataDao;
import dao.MsmListDao;
import dao.RegisterDao;
import model.Cooperation;
import model.MmPayData;
import model.MsmList;
import net.sf.json.JSONObject;
import util.ConstValue;
import util.DebuUtil;
import util.StringUtil;
import azul.MYSocket;
import azul.SocketThread;


public class FirstDayServlet extends HttpServlet {

	private SocketThread mSocketThread;
	
	/**
	 * Constructor of the object.
	 */
	public FirstDayServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	Cooperation getCooperation(String packet_id)
	{
		CooperationDao cooperationDao = new CooperationDao();
		Cooperation cop = cooperationDao.getRecord(packet_id);
		return cop;
	}
	
	MmPayData getMmPayData(String pay_id)
	{
		MmPayDataDao mmPayDataDao = new MmPayDataDao();
		MmPayData paydata = mmPayDataDao.getRecord(pay_id);
		return paydata;
	}
	
	int getIndexByAcount(String pay_amount,int amount)
	{
		
		int index = 0;
		//CooperationDao cooperationDao = new CooperationDao();
		//Cooperation cop = cooperationDao.getRecord(packet_id);
		
		DebuUtil.log("payamount："+pay_amount);
		
		String[] payList = pay_amount.split(",");
		
		for(int i = 0;i<payList.length;i++){
			int v = Integer.valueOf(payList[i]);
			if (v == amount) {
				index = i+1;
				break;
			}
		}
		
		return index;
		
	}
	
	
	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*String msg = "this is server speaking!!";
		String amountstr = request.getParameter("amount");
		String gameId = request.getParameter("gameId");
		String imsi = request.getParameter("imsi");
		String imei = request.getParameter("imei");
		String packet_id = request.getParameter("packet_id");//packet_id=381182001&amount=1&gameId=192&imsi=547464164566446&imei=64156474564544
		int amount = Integer.valueOf(amountstr);
		PrintWriter out = null;
		Cooperation cop = getCooperation(packet_id);
		String pay_id = "";
		if(cop != null)
		{
			pay_id = cop.getPayId();
		}
		
		
		MmPayData paydata = null;
		if(!StringUtil.is_nullString(pay_id))
		{
			paydata = getMmPayData(pay_id);
		}
		
		DebuUtil.log("pay_id对应的值为："+pay_id);//
		
		String socketID = "";
		if(paydata != null)
		{
			socketID = paydata.getPayId()+"_"+paydata.getPayType();
		}
		
		
		String pay_amount = paydata.getPayAmount();
		
		int index = getIndexByAcount(pay_amount,amount);
		
		
		
		String AppID = "";
		if(gameId.equals("192"))
		{
			AppID = "300008673356";
			index = 8;
		}
		JSONObject rsqjson = new JSONObject();	
		rsqjson.put("imsi", imsi);
		rsqjson.put("imei", imei);
		rsqjson.put("userdata", packet_id);
		String paycode  = String.format("%s%02d",AppID,index);
		rsqjson.put("paycode", paycode);
		msg = rsqjson.toString();
		
		response.setContentType("text/html;charset=utf8");
		
		try {
			 out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String respond_str="no socket";
		long beTime = System.currentTimeMillis();
		long waitTime = 1000*10;
		//long shortwaitTime = 500;
		//long selTime = beTime;
		do
		{
			//selTime = System.currentTimeMillis();
			MYSocket mySocket = mSocketThread.getAvailibleSocket(socketID);
			if (mySocket!=null) {
				DebuUtil.log("send="+msg);
				 respond_str = mySocket.manageMSG(msg);		
	             if(!StringUtil.is_nullString(respond_str))
	             {
	            	 break;
	             }
			}
			
			long curTime = System.currentTimeMillis();
			if((curTime-beTime) > waitTime)
			{
				DebuUtil.log("10秒超时退出");
				break;
			}
			else 
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}while(true);
		
		/////////////////////////////////////////
		MsmList msmList = new MsmList();
		 msmList.setChn(paycode);
        msmList.setImei(imei );
        msmList.setImsi(imsi);
        msmList.setXexdata(packet_id);
        msmList.setAmount(Integer.valueOf(amount));
        
        MsmListDao  msmListDao = new MsmListDao();
		msmListDao.add(msmList);//
		////////////////////////////////////////
	        
		if(StringUtil.is_nullString(respond_str))
		{
			respond_str="no socket";
		}
		out.print(respond_str);	
		out.close();*/
		
		//http://192.168.1.105:8080/day01/servlet/FirstDayServlet
		
	}

	

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//doGet(request, response);
	}

	
	@Override
	public void init() throws ServletException {
	
		DebuUtil.log("servlet init");

		//mSocketThread = new SocketThread();
		//mSocketThread.start();

		//System.out.println("<=============��ʼ��ϵͳ�����ɹ�==============>");
	}

	
}
