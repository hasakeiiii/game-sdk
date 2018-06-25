package dao;

import java.util.ArrayList;

import model.Channel;
import model.CpManage;
import model.Userinfo;
import util.ConstValue;
import util.DebuUtil;
import azul.CacheDao;


public class CpManageDao extends CacheDao{
	public CpManageDao() {
		init();
	}
	
	public int addCpManage(CpManage cpmanage){
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where name='%s'", baseTableName,cpmanage.getName());
		int count = getRecordCount(sql);
		DebuUtil.log("cp管理表 结果="+count);
		if(count < 1)
		{
		   add(cpmanage);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}
	
	public int editCpManage(CpManage cpmanage)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where id=%s", baseTableName,cpmanage.getId());
		int count = getRecordCount(sql);
		DebuUtil.log("cp管理表 结果="+count);
		if(count >= 1)
		{
		   edit(cpmanage);
		   ret = ConstValue.OK;
		   DebuUtil.log("修改成功");
		}
		return ret;
	}	
	
	public int getCpNo(){
		int cn = 0;
		String res;
		String sql;
		sql = String.format("select max(cp_no) from cp_manage");
		cn = getRecordCount(sql);
		if(cn == 0){
			cn = 1;
		}else{
			cn = cn + 1;
		}
		return cn;
	}
	
	public CpManage getRecord(int cp_no)
	{
		CpManage cp = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where cp_no=%d", baseTableName,cp_no);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			cp = (CpManage)objList.get(0);
		}
		return cp;
	}
	public CpManage getRecord1(int login){
		CpManage cpManage = null;
		String sql; 
		sql = String.format("select * from %s where login_user=%d", baseTableName,login);
		ArrayList<Object> objList;
		objList = getList(sql);
		if(objList.size() > 0)
		{
			cpManage = (CpManage)objList.get(0);
		}
		return cpManage;
		
	}
}
