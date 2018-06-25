package dao;

import java.util.ArrayList;
import java.util.List;

import model.Cooperation;
import model.MmPay;
import model.Pay;
import sdkReq.MmpayRsq;
import util.ConstValue;
import util.DebuUtil;
import util.StringUtil;
import azul.BaseDao;

public class MmPayDao extends BaseDao{
	public MmPayDao() {
		init();
	}
	
	public void test()
	{
		MmPay obj = new MmPay();
		
		obj.setChannel("0000000000");
		
		obj.setAppId("300008138647");
		obj.setAppKey("6DD23E6257198AE7");
		obj.setAppCode("30000288206502");
		
		obj.setAmount(6*100);
		
		obj.setOrderNo("");
		obj.setTradeNo("526BB497801ED15DAD8EECF8A534D09A");
		obj.setRet(1);
		mmPay(obj);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public void revisePay()
	{
		String sql ="select *  from mm_pay where  date='2015-05-18' ";//2085,2182,2183,2132,2133,2102,2103,notisfy=0 and
		ArrayList<Object> list;
		list = getList(sql);
		
		for(int i=0;i<list.size();i++)
		{
			MmPay gamedata=(MmPay)list.get(i);
			/*if(gamedata.app_key.contains("_"))
			{
				 String splitstr = gamedata.app_key;
				 String[] strarray=splitstr.split("_");     		
				 gamedata.ExData = strarray[0];
			}
			else*/
			{
			   gamedata.ExData = gamedata.app_key;/////////////////////////
			   gamedata.cp_order_no = gamedata.ExData;
			}
			if(gamedata.getRet() == 1)
			{
				PayDao paydao = new PayDao();
	    	    Pay pay = null;
				pay = paydao.getPayRecordByCpOrder(gamedata.cp_order_no);
				gamedata.bSDK = true;
				if(pay != null)
				paydao.payNotisfy(pay,gamedata.bSDK);
			}
		}
	}
	
	public void notisymm()
	{
		String sql ="select *  from mm_pay where  notisfy=0 limit 100  ";//2085,2182,2183,2132,2133,2102,2103,notisfy=0 and
		ArrayList<Object> list;
		list = getList(sql);
		
		for(int i=0;i<list.size();i++)
		{
			MmPay gamedata=(MmPay)list.get(i);
			/*if(gamedata.app_key.contains("_"))
			{
				 String splitstr = gamedata.app_key;
				 String[] strarray=splitstr.split("_");     		
				 gamedata.ExData = strarray[0];
			}
			else*/
			{
			   gamedata.ExData = gamedata.app_key;/////////////////////////
			   gamedata.cp_order_no = gamedata.ExData;
			}
			
			PayDao paydao = new PayDao();
    	    Pay pay = null;
			pay = paydao.getPayRecordByCpOrder(gamedata.cp_order_no);
			if(pay != null)
			{
				gamedata.packet_id = pay.packet_id;
			}
			//if(gamedata.notisfy == 1)
			{
				
       		    Cooperation cooperation = MmpayRsq.getPacket(gamedata);
       		    if(cooperation != null)
       		    {
					String rsq =MmpayRsq.notisty(cooperation,gamedata,"","");//http://119.29.15.70:8080/sdk/param/mmPayCallback.jsp
			    	if(!StringUtil.is_nullString(rsq) || rsq.equals(MmpayRsq.NOURL) )
			    	{
			    		gamedata.setNotisfy(1);
			    		MmPayDao mPayDao = new MmPayDao();
			    		mPayDao.edit(gamedata);
			    	}
       		    }
			}
			
		}
	}
	
	public void notisyzz()
	{
		String sql ="select *  from mm_pay where  app_id='300008776172' ";//2085,2182,2183,2132,2133,2102,2103,notisfy=0 and
		ArrayList<Object> list;
		list = getList(sql);
		
		for(int i=0;i<list.size();i++)
		{
			MmPay gamedata=(MmPay)list.get(i);
			
			/*if(gamedata.app_key.contains("_"))
			{
				 String splitstr = gamedata.app_key;
				 String[] strarray=splitstr.split("_");     		
				 gamedata.ExData = strarray[0];
			}
			else*/
			{
			   gamedata.ExData = gamedata.app_key;/////////////////////////
			}
			
			//if(gamedata.ret == 1)
			{
				/*String rsq =MmpayRsq.notisty(gamedata,"","");//http://119.29.15.70:8080/sdk/param/mmPayCallback.jsp
		    	if(!StringUtil.is_nullString(rsq))
		    	{
		    		gamedata.setNotisfy(1);
		    		MmPayDao mPayDao = new MmPayDao();
		    		mPayDao.edit(gamedata);
		    	}*/
			}
		}
	}
	
	public static void paySA(MmPay obj)
	{
		ChannelDataDao channelDataDao = new ChannelDataDao();
		channelDataDao.paySA(obj);
		//if(ConstValue.bNew == 1)//统计省份数据
		{
			ProvinceDataDao provinceDataDao = new ProvinceDataDao();
			provinceDataDao.pay(obj,false);
		}
	}
	 
	//select * from mm_pay where app_id='300008481286'
	
	public MmPay getPayRecord(String trade_no)
	{
		
		
		MmPay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where trade_no='%s'", baseTableName,trade_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (MmPay)objList.get(0);
		}
		return pay;
	}
	
	public MmPay getPayRecordByOrderNO(String OrderNO)
	{
		MmPay pay = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where order_no='%s'", baseTableName,OrderNO);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			pay = (MmPay)objList.get(0);
		}
		return pay;
	}
	
	public List<Object> getPayRecordLikeOrderNO(String OrderNO)
	{
		
		String sql = "select * from mm_pay where order_no like'"+OrderNO+"%'";
		List<Object> list = getList(sql);
		return list;
	}
	public int isExit(MmPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where trade_no='%s'", baseTableName,obj.getTradeNo());
		int count = getRecordCount(sql);
		if(count < 1)
		{
		   ret = ConstValue.OK;
		}
		return ret;
	}
	public int mmPay(MmPay obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		//sql = String.format("select * from %s where trade_no='%s'", baseTableName,obj.getTradeNo());
		int count = 0;//getRecordCount(sql);
	
		DebuUtil.log("MM付费表 结果="+count);
		if(count < 1)
		{
		   /*PayDao paydao = new PayDao();
		   Pay pay = paydao.getPayRecord(obj.trade_no,"mmpay");
		   if(pay != null)
		   {
			   if(pay.amount != obj.amount)
			   {
				   DebuUtil.log("值不相等需要修改 ");
				   pay.amount = obj.amount;
			   }
			   
			   DebuUtil.log("修改支付状态 ");
			   if(obj.ret >= 0)
			   {
			      pay.state = 1;
			   }
			   paydao.edit(pay);
		   }*/
		   add(obj);
		   ret = ConstValue.OK;
		   //DebuUtil.log("添加成功");
		}
		return ret;
	}	
/*	
	public ArrayList<Object> getAll(){
		ArrayList<Object> objList;
		String sql = "select * from mm_pay where date ='2015-06-13' and time <= '10:30:00' and order_no like '1115%'";
		objList = getList(sql);
		System.out.println(sql);
		return objList;
	}*/
}
