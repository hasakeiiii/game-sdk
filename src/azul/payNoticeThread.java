package azul;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import model.CpSettle;
import model.Pay;
import model.PayFeeNum;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.SaConfiguration;

import com.unionpay.upmp.sdk.examples.QueryExample;

import dao.AppDao;
import dao.ChannelDataDao;
import dao.CooperationDao;
import dao.CpSettleDao;
import dao.MmPayDao;
import dao.PayDao;
import dao.PayFeeNumDao;



public class payNoticeThread extends Thread {
	

    int count = 0;
    
    void handle_notisy()
    {
    	DebuUtil.log2("自动补发充值线程运行");
    	Date curdate= new Date();
    	
    	ArrayList<Object> objList;
		String sql = "select * from pay where state=1 and notisfy=0 and ncount<10 and date>='2015-01-23' limit 1000 ";//and date>='2014-07-16'
		//objList = getList(sql);
		PayDao paydao = new PayDao();
		objList = paydao.getPayList(sql);
		//objList = paydao.getAllList();
		DebuUtil.log2("补发个数="+objList.size());
		
		for(int i=0;i<objList.size();i++){
			Pay pay=(Pay)objList.get(i);
			String datestr = pay.date+" "+pay.time;
			Date payDate = DateUtil.strToDate(datestr);
  			long milSencods = curdate.getTime()-payDate.getTime();
  			//if((milSencods)/(1000*60) > 10)
  				
			if((pay.getState() == 1)&&(pay.getNotisfy() == 0)&&(pay.getNcount() < 10) && ((milSencods)/(1000*60) > 5))
			{
				DebuUtil.log2("自动补发充值通知");
				int ret = pay.rsqCallbackUrl(0);
				//pay.ncount ++;
				//if(ret == 1)
				{
				   paydao.update(pay);
				}
				
				/*try {
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					DebuUtil.log2(e.toString());
				}*/
			}
		}
    }
   
    public static String getDBName(String JdbcUrl)
    {
    	String str =JdbcUrl;// "jdbc:mysql://localhost/sdkdata?rewriteBatchedStatements=true";
        int end = str.indexOf("?");
        int begin = str.indexOf("//")+2;
        //DebuUtil.log("begin="+begin);
        str = str.substring(begin, end);
        //DebuUtil.log("str="+str);
        begin = str.indexOf("/")+1;
        end = str.length();
        str = str.substring(begin, end);
        //DebuUtil.log("str="+str);
        return str;
    }
    
    public static void handle_bk() 
    {
    	DebuUtil.log4("数据库备份开始");
//    	Date curdate= new Date();
    	PayDao paydao = new PayDao();
    	int days = 2;
    	String  paySql = String.format("select * from pay where date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY));",days);
    	int count = paydao.getCount(paySql);
    	
    	 SaConfiguration configuration =SaConfiguration.getInstance("msmpay");
		 String JdbcUrl = configuration.getValue("JdbcUrl");
		 
    	 String DBName = getDBName(JdbcUrl);
    	if(count > 0)
    	{
    		
			paySql = String.format("insert into pay select * from %s.pay where date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY));",DBName,days);
			String mmPaySql = String.format("insert into mm_pay select * from %s.mm_pay where date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY));",DBName,days);
			
			MmPayDao mmPaydao = new MmPayDao();
			paydao.executeUpdateBk(paySql);
			mmPaydao.executeUpdateBk(mmPaySql);
			DebuUtil.log4("数据库备份结束");
			DebuUtil.log4("数据库数据删除");
			paySql = String.format("delete from pay where date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY));",days);
			paydao.executeUpdate(paySql);
			mmPaySql = String.format("delete from mm_pay where date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY));",days);
			mmPaydao.executeUpdate(mmPaySql);
			//mmPaySql = String.format("delete from ZhangZhong where date = (select curdate()-%d);",days);
			//mmPaydao.executeUpdate(mmPaySql);
			DebuUtil.log4("数据库数据删除结束");
    	}
    	
	}
    
    
    void handle_notisy_sa()
    {
    	DebuUtil.log2("自动补发充值线程运行");
    	
    	MmPayDao mmPayDao = new MmPayDao();
    	mmPayDao.notisymm();
    	
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DebuUtil.log2(e.toString());
		}
	
    }
    
    void handle_getdata_sa()
    {
    	DebuUtil.log("同步服务器数据");
    	CooperationDao cooperationDao= new CooperationDao();
	    ArrayList<String> payurlList = new ArrayList<String>();
	    SaConfiguration configuration =SaConfiguration.getInstance("msmpay");
		String str = configuration.getValue("PayURLList");
		StringTokenizer token=new StringTokenizer(str,"_");  
        while(token.hasMoreElements()){ 
        	String v = token.nextToken();
        	payurlList.add(v);
        	//System.out.println(v);
        } 
        
	    ArrayList<String> requrlList = new ArrayList<String>();
	    str = configuration.getValue("ReqURLList");
		token=new StringTokenizer(str,"_");  
        while(token.hasMoreElements()){  
        	String v = token.nextToken();
        	requrlList.add(v);
        	//System.out.println(v);
        } 
        String date =  DateUtil.getDate();//"2014-12-29";//
	    cooperationDao.SettelDataFromSrv(payurlList,requrlList,date);	
	    
	    String time =  DateUtil.getTime();
	    if( (time.compareTo("00:00:00") > 0) && (time.compareTo("00:30:00") < 0))//第二天的数据重新取
	    {
	    	Date lastdate =DateUtil.addDate("dd", -1, new Date());
	    	cooperationDao.SettelDataFromSrv(payurlList,requrlList,DateUtil.getDate(lastdate));
	    }
    }
    
    static void handle_union_que()
    {
    	DebuUtil.log2("银联自动查询线程运行");//
    	
    	Date curdate= new Date();
    	//Date quedate = DateUtil.addDate("mm", -10, date);
    	
    	ArrayList<Object> objList;
    	
  		String sql = "select * from pay where state=0 and pay_type='unionpay' and ncount<2 and date='"+ DateUtil.getDate()+"'";//and date>='2014-07-16'
  		//objList = getList(sql);
  		PayDao paydao = new PayDao();
  		objList = paydao.getPayList(sql);
  		//objList = paydao.getAllList();
  		DebuUtil.log2("银联个数="+objList.size());
  		
  		for(int i=0;i<objList.size();i++){
  			Pay pay=(Pay)objList.get(i);
  			String datestr = pay.date+" "+pay.time;
  			
  			Date payDate = DateUtil.strToDate(datestr);
  			long milSencods = curdate.getTime()-payDate.getTime();
  			if((milSencods)/(1000*60) > 10)
  			{
  				QueryExample.Query(pay);
  				try {
  					Thread.sleep(2*1000);
  				} catch (InterruptedException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  					DebuUtil.log2(e.toString());
  				}
  			};
  		}
    }
    static void handle_check()
    {
    	DebuUtil.log5("统计前一天的付费用户次数数据");
    	PayDao paydao = new PayDao();
    	PayFeeNum payFeeNum = new PayFeeNum();
    	PayFeeNumDao payFeeNumDao = new PayFeeNumDao();
/*    	String days = "6,7,8,9,10,11,12,13,14,15,16,17,18,19";
    	String[] day = days.split(",");
    	for(int j=0;j<day.length;j++){
    		int dayss = Integer.parseInt(String.valueOf(day[j]));*/
    	int dayss = 1;
	    	String sql = String.format("select * from pay_fee_num where  date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY))",dayss);
	    	int count = payFeeNumDao.getCount(sql);
	    	if(count == 0){
	    		DebuUtil.log5("~~~~~~~~~~~~~~统计开始");
		    	String  paySql = String.format("select packet_id,count(*),count(DISTINCT device_id),date from pay where date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY)) and state =1 group by packet_id;",dayss);
		    	CacheDao cacheDao = new CacheDao();
		    	List<ArrayList> feeList = cacheDao.getObjectList(paySql);
		    	for(int i = 0;i< feeList.size();i++){
		    		payFeeNum.packet_id = String.valueOf(feeList.get(i).get(0));
		    		payFeeNum.payNum = Integer.parseInt(String.valueOf(feeList.get(i).get(1)));
		    		payFeeNum.payPer = Integer.parseInt(String.valueOf(feeList.get(i).get(2)));
		    		payFeeNum.date = String.valueOf(feeList.get(i).get(3));
		    		payFeeNumDao.addPayFeeNum(payFeeNum);
		    	}
	    	}
	    	DebuUtil.log5("~~~~~~~~~~~~~~统计成功");
    	/*}*/
    }
    
    static void handle_cp()
    {
    	DebuUtil.log5("CP扣量计算开始");
    	DecimalFormat df=new DecimalFormat("0");
    	CpSettleDao cpSettleDao = new CpSettleDao();
    	AppDao appDao = new AppDao();
    	int day = 1;
    	String cpsql = String.format("select * from cp_settle where  date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY))",day);
    	int count = cpSettleDao.getCount(cpsql);
    	if(count == 0){
	    	String sql = "select cp_no from cp_manage ";
	    	CpSettle cpSettle = new CpSettle();
	    	CacheDao cacheDao = new CacheDao();
	    	List<ArrayList> cplist = cacheDao.getObjectList(sql);
	    	for(int i = 0;i<cplist.size();i++){
	    		String cpNo = cplist.get(i).get(0).toString();
//	    		String cpappsql = String.format("select * from app where cp_no = '%s' )",cpNo);
	    		List<ArrayList> cpapplist = appDao.getAppCp(cpNo);
	    		for(int j = 0;j<cpapplist.size();j++){
	    			String cpgame = cpapplist.get(j).get(0).toString();
	    			int cpcount = Integer.parseInt(String.valueOf(cpapplist.get(j).get(1)));
	    			String channSql = String.format("select date,"
	    					+ "game_id,"
	    					+ "business_no,"
	    					+ "channel,"
	    					+ "packet_id,"
	    					+ "activity_num,"
	    					+ "login_num,"
	    					+ "day1_num,"
	    					+ "day7_num,"
	    					+ "fee_num,"
	    					+ "day_all_pay_acount,"
	    					+ "req_ok_num,"
	    					+ "(mm_pay+tc_pay+union_pay+open_pay+web_pay+wo_pay+box_pay+uni_pay+tc_pay+ld_pay+jd_pay+wx_pay+ali_pay),"
	    					+ "day_all_acount from channel_data where  date = (select DATE_SUB(CURDATE(), INTERVAL %d DAY)) "
	    					+ "and game_id = '%s'",day,cpgame);
	    			System.out.println(channSql);
	    			List<ArrayList> channlist = cacheDao.getObjectList(channSql);
	    			for(int k=0;k<channlist.size();k++){
	    				cpSettle.date = channlist.get(k).get(0).toString();
	    				cpSettle.game_id = channlist.get(k).get(1).toString();
	    				cpSettle.BusinessNo = channlist.get(k).get(2).toString();
	    				cpSettle.channel = channlist.get(k).get(3).toString();
	    				cpSettle.packet_id = channlist.get(k).get(4).toString();
	    				cpSettle.settlePay = Integer.parseInt(String.valueOf(channlist.get(k).get(12)))*cpcount/100;
	    				cpSettle.settleActivateNum = Integer.parseInt(String.valueOf(channlist.get(k).get(5)))*cpcount/100;
	    				cpSettle.settleLoginNum = Integer.parseInt(String.valueOf(channlist.get(k).get(6)))*cpcount/100;
	    				cpSettle.settleDay1Pay = Integer.parseInt(String.valueOf(channlist.get(k).get(7)))*cpcount/100;
	    				cpSettle.settleDay7Pay = Integer.parseInt(String.valueOf(channlist.get(k).get(8)))*cpcount/100;
	    				cpSettle.feeNum = Integer.parseInt(String.valueOf(channlist.get(k).get(9)));
	    				cpSettle.dayAllPayAcount = Integer.parseInt(String.valueOf(channlist.get(k).get(10)));
	    				cpSettle.reqOkNum = Integer.parseInt(String.valueOf(channlist.get(k).get(11)));
	    				cpSettle.dayAllAcount = Integer.parseInt(String.valueOf(channlist.get(k).get(13)));
	    				cpSettleDao.add(cpSettle);
 	    			}
	    		}
	    	}
	    	DebuUtil.log5("CP扣量计算结束");
    	}
    }
    	
	public final void run()
	{	
		String curdatestr = "";
		do
		{			
			SaConfiguration configuration =SaConfiguration.getInstance("msmpay");
			String datecenter = configuration.getValue("SADateCen");
			int time= 60*1000;
			Date dt=new Date();
			DateFormat df = new SimpleDateFormat("HH:mm");//设置显示格式
			String nowTime="";
			nowTime= df.format(dt);
			
			if(datecenter.equals("1"))
			{
				time= 5*60*1000;//
			}
			
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//DebuUtil.log2(e.toString());
			}
			
			
			if(ConstValue.PAY_SERVER != 1)
			{
				handle_notisy();
				handle_union_que();
				
				String nowtime =  DateUtil.getTime();
				if((nowtime.compareTo("03:00:00") > 0) && (nowtime.compareTo("03:20:00") < 0))//单机每天算一次		
				//if((nowtime.compareTo("17:00:00") > 0) && (nowtime.compareTo("17:30:00") < 0))//单机每天算一次					  
				{
					 //String datestr = DateUtil.getDate();
					 Date lastdate =DateUtil.addDate("dd", -1, new Date());
					 String lastdatestr = DateUtil.getDate(lastdate);
					 DebuUtil.log2("lastdatestr="+lastdatestr);
					 if(!curdatestr.equals(lastdatestr))
					 {
						 DebuUtil.log2("单机每天算一次");
						 curdatestr = lastdatestr;
						 ChannelDataDao channelDataDao = new ChannelDataDao();
					     channelDataDao.settleXingXingPay(lastdatestr);
					 }
				 }
				
			}
			else
			{
				
				
				if(datecenter.equals("1"))
				{
				   //handle_getdata_sa();
				}
				else
				{
					 handle_notisy_sa();
					 
					 String nowtime =  DateUtil.getTime();
					 if( (nowtime.compareTo("02:00:00") > 0) && (nowtime.compareTo("02:30:00") < 0))//2点把数据清掉					    
					 {
				//	 handle_bk();备份
					 handle_check();	 
					 handle_cp();	 
					 }
				}
				
			}		
			
		}while(true);
		
		
		
		/*Util_G.debug_e(Util_G.busylog, "���������߳�\n");  
		m_RequestHtpp.openNet();
		m_RequestHtpp.sendData();
		Util_G.debug_e(Util_G.busylog, "�������������\n");  
		m_RequestHtpp.handleRecData();
		m_RequestHtpp.closeNet();
		Util_G.debug_e(Util_G.busylog, "�����߳̽���\n"); */
	
		
		
	}
	
}
