package dao;

import model.MuzhiGame;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;

public class MuzhiGameDataDao extends BaseDao{
	public MuzhiGameDataDao() {
		init();
	}
	
	public int add(MuzhiGame obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	

}
