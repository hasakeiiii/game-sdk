package dao;

import java.util.ArrayList;
import java.util.List;

import model.AliPay;
import model.App;
import model.CardPay;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;
import azul.CacheDao;

public class BlackDeviceDao extends CacheDao{
	public BlackDeviceDao() {
		init();
	}
	
	public int isBlackDevice(String device_id)
	{
		String sql;
		sql = String.format("select * from %s where device_id='%s'", baseTableName,device_id);
		int count = getRecordCount(sql);
	
		return count;
	}	
	
}
