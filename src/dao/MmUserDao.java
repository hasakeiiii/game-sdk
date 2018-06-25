package dao;

import java.util.ArrayList;

import util.ConstValue;
import util.DebuUtil;
import mmuser.MMUser;
import model.Activate;
import model.MmUser;
import model.TenPay;
import azul.BaseDao;
/**
 * 
 * @author mzyw_linilq
 * @version 1.0
 * 操作设备数据信息表 device_info
 *
 */
public class MmUserDao extends BaseDao {

	public MmUserDao (){
		init();
	}
	
	public MmUser getRecord(String imei,String packet_id)
	{
		MmUser mmUser = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where imei='%s' ", baseTableName,imei);;//where packet_id='217150001'
		objList = getList(sql);
		if(objList.size() > 0)
		{
			for(int i=0;i<objList.size();i++)
			{
				MmUser tmmUser = (MmUser)objList.get(0);
				if(tmmUser.getPacketId().equals(packet_id))
				{
					mmUser = tmmUser;
				}
			}
		}
		//DebuUtil.log(sql);
		
		return mmUser;
	}
	
	public boolean IsExit(String imei)
	{
		String sql;
		sql = String.format("select * from %s where imei='%s'", baseTableName,imei);
		int count = getRecordCount(sql);
	    boolean ret = false;
	    if(count > 0)
	    {
	    	ret = true;
	    }
		return ret;
	}	
	
	
}
