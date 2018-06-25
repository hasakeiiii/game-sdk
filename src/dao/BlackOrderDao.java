package dao;

import java.util.ArrayList;
import java.util.List;

import model.AliPay;
import model.App;
import model.BlackDevice;
import model.BlackOrder;
import model.CardPay;
import model.ChannelData;
import model.MmPay;
import model.MsmList;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;
import azul.CacheDao;

public class BlackOrderDao extends CacheDao{
	public BlackOrderDao() {
		init();
	}

	public void genBlackDeviceListByOrder()
	{
		String sql;
		ArrayList<Object> objList;
		sql = String.format("select * from %s ", baseTableName);
		objList = getList(sql);
		
		MmPayDao mmPayDao = new MmPayDao();
		MsmListDao msmListDao= new MsmListDao();
		BlackDeviceDao blackDeviceDao = new BlackDeviceDao();
		
		for(int i=0;i<objList.size();i++)
		{
			BlackOrder blackOrder =(BlackOrder)objList.get(i);
			String order = blackOrder.getOrderNo();
			if(order.contains("\n"))
			{
				order=order.substring(0, order.length()-1);
				//blackOrder.setOrderNo(order);
				//blackDeviceDao.edit(blackOrder);
			}
			DebuUtil.log("order="+blackOrder.getOrderNo());
			MmPay mmPay =mmPayDao.getPayRecordByOrderNO(order);
			if(mmPay != null)
			{
				DebuUtil.log("tradeno="+mmPay.getTradeNo());
			      MsmList msmList= msmListDao.getMsmListByTradeNo(mmPay.getTradeNo());

			      if(msmList != null)
			      {
			    	  DebuUtil.log("imei="+msmList.getImei());
			    	  if(blackDeviceDao.isBlackDevice(msmList.getImei()) != 1)
			    	  {
				    	  BlackDevice blackDevice = new BlackDevice();
				    	  blackDevice.setDeviceId(msmList.getImei());
				    	  blackDeviceDao.add(blackDevice);
			    	  }
			      }
			}
		
		}
		
	}
	
	
	
}
