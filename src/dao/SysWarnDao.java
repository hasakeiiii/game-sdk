package dao;
import model.SysWarn;
import azul.CacheDao;
public class SysWarnDao extends CacheDao{
public SysWarnDao() {
	init();
}
//页面调用
//dao.SysWarnDao sysWarnDao=new dao.SysWarnDao();
//sysWarnDao.send(1);
public void send(String key ,String info){System.out.println("000000000");
	SysWarn sysWarn=(SysWarn)loadBySql("select * from sys_warn where keyword='"+key+"'");System.out.println("1111111111");
	if(sysWarn==null){
		return;
	}System.out.println("222222222");
	int kind=sysWarn.getKind();
	String address=sysWarn.getAddress();
	String[] addressArr=address.split(",");
	//邮件警告
	if(kind==0){
		String stmp=common.ConfigIni.getValue("email","stmp");
		String user=common.ConfigIni.getValue("email","user");
		String password=common.ConfigIni.getValue("email","password");
		for(int j=0;j<addressArr.length;j++){
			if("".equals(addressArr[j])) {
				continue;
			}
			common.ApachMail email = new common.ApachMail();
        	email.send(stmp,addressArr[j],user,password,"UTF-8","系统警告",info);
        }
	}
	else{System.out.println("eeeeeeeeeeeeeeee");
		for(int j=0;j<addressArr.length;j++){
			if("".equals(addressArr[j])) {
				continue;
			}
			
        }
	}
}
}