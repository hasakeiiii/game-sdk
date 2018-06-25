package dao;

import java.util.ArrayList;

import util.ConstValue;
import util.DebuUtil;
import mmuser.MMUser;
import model.Activate;
import model.DeviceInfo;
import azul.BaseDao;
/**
 * 
 * @author mzyw_linilq
 * @version 1.0
 * 操作设备数据信息表 device_info
 *
 */
public class DeviceInfoDao extends BaseDao {

	public DeviceInfoDao (){
		init();
	}
	
	public DeviceInfo getRecord(String device_id)//,String packet_id
	{
		DeviceInfo deviceInfo = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where imei='%s' ", baseTableName,device_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			deviceInfo = (DeviceInfo)objList.get(0);
		}
		return deviceInfo;
	}
	public int getDevice(String device_id)//,String packet_id
	{
		//DeviceInfo deviceInfo = null;
	//	ArrayList<Object> objList;
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where imei='%s' ", baseTableName,device_id);
		int count = getRecordCount(sql);
		//objList = getList(sql);
		//DebuUtil.log(sql);
		DebuUtil.log("设备结果="+count);
		if(count < 1)
		{
			ret = 1;
		}
		return ret;
	}
	
	public void  doMMUser()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where sid is null limit 10", baseTableName);//where packet_id='217150001'
		objList = getList(sql);
		
		MMUser.doInitAllUser(objList);
		
		
	}
	
	public void  doMMUserAct()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where sid  is not null limit 10 ", baseTableName);//where packet_id='217150001'
		objList = getList(sql);
		
		MMUser.doActAllUser(objList);
		
		
	}
	
}
