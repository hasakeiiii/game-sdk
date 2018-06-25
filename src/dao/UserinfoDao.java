package dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import common.MD5;

import model.App;
import model.Businesser;
import model.CfgCompany;
import model.CfgSell;
import model.Channel;
import model.Pay;
import model.SysUser;
import model.Userinfo;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import azul.BaseDao;
import azul.CacheDao;

public class UserinfoDao  extends CacheDao{
	public UserinfoDao() {
		init();
	}
	public void test()
	{
		Userinfo obj = new Userinfo();
		
		obj.username = "lsl";
		obj.pass = "6670575";
		obj.role = "admin";//"admin";
		
		addUserinfo(obj);
		
		
	}
	
	
	public String login(String username,String password,HttpServletRequest request){
    	String result="";
		try {
			HttpSession session=request.getSession();
			//MD5 md5=new MD5();
			//password=md5.toMD5(password);
			int sys_user_id=-1;
			session.setAttribute("BSS","");	
			if("admin".equals(username) && "adminlsl".equals(password)){//超级用户
				sys_user_id=0;
				Userinfo sysUser =new Userinfo();
				sysUser.setUsername("admin");
				sysUser.setRole("admin");
				//sysUser.setSysUserId(0);
				sysUser.setPass("adminlsl");
				session.setAttribute("sysUser",sysUser);
			}
			if(sys_user_id!=0){
				UserinfoDao userinfoDao=new UserinfoDao();
				//String strSQL = "select * from userinfo where username='" + username + "' and userpass='" + password + "'";
				String strSQL;
				strSQL = String.format("select * from %s where username='%s' and pass='%s'", baseTableName,username,password);
				DebuUtil.log("strSQL="+strSQL);
				ArrayList list = userinfoDao.getList(strSQL);
				DebuUtil.log("list.size()="+list.size());
				if (list.size() != 0) {
					Userinfo sysUser = (Userinfo) list.get(0);
				    sys_user_id=sysUser.getId();
					session.setAttribute("sysUser",sysUser);
					if("CPA".equals(sysUser.getRole()) ||"CPA_R".equals(sysUser.getRole()) ||"CPS".equals(sysUser.getRole())){
						DebuUtil.log("cid登录:"+username);
						
						ChannelDao channelDao=new ChannelDao();
						Object cidObj=channelDao.loadBySql("select * from channel where login_user="+sysUser.getId());
						DebuUtil.log("select * from channel where login_user="+sysUser.getId());
						if(cidObj!=null){
							Channel channel=(Channel)cidObj;
							session.setAttribute("channel_no",channel.getNo());
							DebuUtil.log("channel_no:"+channel.getNo());
						}
					}
					else if("BSS".equals(sysUser.getRole())){
						DebuUtil.log("sell登录:"+username);	
						BusinesserDao businesserDao=new BusinesserDao();
						Object cidObj=businesserDao.loadBySql("select * from businesser where login_user="+sysUser.getId());
						if(cidObj!=null){
							Businesser cfgSell=(Businesser)cidObj;
							session.setAttribute("BSS",cfgSell.getNo());	
						}
					}/*
					else if("admin".equals(sysUser.getRole())){
						System.out.println("admin登录:"+username);	
					}
					else{
						System.out.println("未知权限登陆:"+username);	
					}*/
				}
				else{
					/*Object obj = sysUserDao.loadBySql("select * from sys_user where username='" + username + "'");
					if (obj!=null) {
						return "用户密码错误";
					} else {
						return "该用户不存在";
					}*/
					strSQL = String.format("select * from %s where username='%s'", baseTableName,username);
					list = userinfoDao.getList(strSQL);
					
					if (list.size() > 0) {
						return "用户密码错误";
					} else {
						return "该用户不存在";
					}
				}
			}
			/*if(sys_user_id!=-1){
				//添加用户操作日志
				StringBuffer sb=new StringBuffer();
				sb.append("insert into sys_logs (sys_user_id,ip,act_type,act) values (");
				sb.append(sys_user_id);
				sb.append(",'");
				sb.append(request.getRemoteAddr());
				sb.append("','LOGIN','')");
				executeUpdate(sb.toString());
				//特殊设置结束----------------------
			}*/

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }  
	
	public Userinfo getUserinfo(String username)
	
	{
		Userinfo userinfo = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where username='%s'", baseTableName,username);
		objList = getList(sql);
		if(objList.size() > 0)
		{
			userinfo = (Userinfo)objList.get(0);
		}
		return userinfo;
	}
	
	public int editUserinfo(Userinfo obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where username='%s' and id<>%d", baseTableName,obj.getUsername(),obj.getId());
		int count = getRecordCount(sql);
	
		DebuUtil.log("渠道表 结果="+count);
		if(count < 1)
		{
		   edit(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("修改成功");
		}
		return ret;
	}	
	public int addUserinfo(Userinfo obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where username='%s'", baseTableName,obj.getUsername());
		int count = getRecordCount(sql);
	
		DebuUtil.log("管理用户表 结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
}
