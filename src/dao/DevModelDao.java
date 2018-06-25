package dao;

import java.util.ArrayList;

import mmuser.MMUser;
import model.Activate;
import model.DevModel;
import azul.BaseDao;
/**
 * 
 * @author mzyw_linilq
 * @version 1.0
 * 操作设备数据信息表 device_info
 *
 */
public class DevModelDao extends BaseDao {

	public DevModelDao (){
		init();
	}
	
	public DevModel  getDevModel()
	{
		DevModel devModel = null;
		ArrayList<Object> objList;
		String sql;
		int index = MMUser.genRan(2, 666);
		sql = String.format("select * from %s where id=%d", baseTableName,index);//where packet_id='217150001'
		objList = getList(sql);
		if(objList.size() > 0)
		{
			devModel = (DevModel)objList.get(0);
		}
		return devModel;
		
		
	}
	
	
	
}
