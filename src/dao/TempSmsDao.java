package dao;

import java.util.ArrayList;

import util.DateUtil;
import util.DebuUtil;

import model.TempSms;
import azul.BaseDao;

public class TempSmsDao extends BaseDao {

	public TempSmsDao(){
		init();
	}
	/**
	 * 将所发送的验证码短信存入数据库   返回添加结果
	 * @param tempSms
	 * @return result “000”为成功  “001”为添加失败 "002"为账号已绑定过失败，此号码已经绑定过账号
	 */
	public String addTempSmsRecord(TempSms tempSms){
		
		String result ="";
		RegisterDao redisterDao = new RegisterDao();
		String sql = String.format("select * from register where phone_num='%s'", tempSms.getPhoneNum());
		if(redisterDao.getObject(sql).size()>0){
			result = "002";
		}else{
			if(add(tempSms).equals("1"))
				result = "000";
			else
				result = "001";
		}
		
		return result;
	}
	


	/**
	 *  更新某个验证码的验证时间
	 * @param checkinTime
	 * @param checkinDate
	 */
	private void updateCheckinTimeAndDate(int id,String checkinTime) {
		// TODO Auto-generated method stub
		String sql ;
		sql = String.format("UPDATE temp_sms set checkin_time='%s' where id=%d", checkinTime,id);
		DebuUtil.log3(sql);
		int result=0;
		result = executeUpdate(sql);
		if (result!=0) {
			DebuUtil.log("修改数据库结果为："+result);
		}
	}
	
	/**
	 * 验证码检验  成功则将手机号码添加到对应用户的注册表中
	 * @param code 验证码
	 * @param addtime  添加时间
	 * @param adddate  添加日期
	 * @return 无论查询成功与否都有结果返回：  001 验证码不存在；  002  验证码过期；  000  验证码有效；
	 */
	public String checkCode(String code,String addtime,String adddate){
		String sql = String.format("select * from temp_sms where add_time='%s' and add_date='%s' and identi_code='%s'", addtime,adddate,code);
		DebuUtil.log3("sql:"+sql);
		String str[] = new String[7];
		ArrayList<Object> list = getObject(sql);
		DebuUtil.log3("结果的长度为："+list.size());
		if (!list.isEmpty()) {
			//查找到相应的结果时，执行下一步，检查是否超时
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i)!=null) {
					str[i] = list.get(i).toString();
				}else{
					str[i] = "";
				}
				
				DebuUtil.log3("str["+i+"]:"+str[i]+"\n");
			}
			//其中str[3]存储的是添加时间  但是判断的话要看日期与时间是否都一致
			DebuUtil.log3("code="+str[2]+"\n添加时间:"+str[4]+"\n 添加日期"+str[3]);
			
			String checkinTime = DateUtil.getTime();
			
			int t =0;
			t = DateUtil.getUsageOfTime(checkinTime,str[4]);
			if (t>120) {
				return TempSms.CodeOverTimeFail;
			}else{
				int id = Integer.valueOf(str[0]);
				updateCheckinTimeAndDate(id, checkinTime);
				RegisterDao registerDao = new RegisterDao();
				String sql1 = String.format("update register set phone_num='%s' where username='%s'", str[6],str[1]);
				DebuUtil.log3(sql1);
				registerDao.executeUpdate(sql1);
				return TempSms.Success;
			}
			
		}
		
		return TempSms.NoCodeExistFail;
	}
	
	/**
	 * 测试方法
	 * @param code
	 * @param phoneNum
	 */
	public void testcheckCode(String code,int id){
		//checkCode(code, id);
	}
	
}
