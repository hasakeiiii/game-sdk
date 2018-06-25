package dao;

import java.util.ArrayList;
import java.util.List;

import model.AppPay;
import model.Cooperation;
import model.Pay;
import model.Vague;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;

public class VagueDao extends CacheDao{

	public VagueDao() {
		init();
	}
	
	public Vague getRecord(String no)
	{
		Vague vague = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			vague = (Vague)objList.get(0);
		}
		return vague;
	}
	
	
	public int addVague(Vague obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("模糊配置 表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	public String EditVague(Vague vague)
	{
		return edit(vague);
	}
	public List<Vague> getAllVague(){
		String sql = "SELECT * FROM vague";
		List<Vague> list = getList(sql);
		return list;
	}
}
