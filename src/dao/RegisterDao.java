package dao;

import java.util.ArrayList;
import java.util.Date;

import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;
import model.Activate;
import model.Cooperation;
import model.Pay;
import model.Register;
import model.Userinfo;
import azul.BaseDao;



public class RegisterDao extends BaseDao{
	
	public RegisterDao() {
		init();
	}
	
	public ArrayList<Object> getAllList()
	{
		Activate activate = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s ", baseTableName);//where packet_id='217150001'
		objList = getList(sql);
		//DebuUtil.log(sql);
		
		return objList;
	}
	
	public  void test_temp()
	{
		   RegisterDao registerDao = new RegisterDao(); 
		   ArrayList<Object> list2 = registerDao.getAllList();
		   Register register = null;
		   
		   int reg = 0;
		   int act = 0;
		   int count = 0;
		  
		   for(int i=0;i<list2.size();i++)
		   {
			   register=(Register)list2.get(i);
		        
		        CooperationDao cooperationDao=new CooperationDao();
				Cooperation cooperation = cooperationDao.getRecord(register.packet_id);
				if(cooperation != null)
				{
					register.business_no = cooperation.getBusinessNo();
					register.channel_no = cooperation.getChannelNo();
					registerDao.edit(register);
				}
		        //DebuUtil.log(activate.device_id+"个数:"+reg);
				DebuUtil.log("注册总数:"+list2.size()+"当前:"+reg);
		        reg++;
		   }
		  
	}
	
	public  void test_temp2()
	{
		   RegisterDao registerDao = new RegisterDao(); 
		   ArrayList<Object> list2 = registerDao.getAllList();
		   Register register = null;
		   
		   int reg = 0;
		   int act = 0;
		   int count = 0;
		  
		   for(int i=0;i<list2.size();i++)
		   {
			   register=(Register)list2.get(i);
		        
			    Register register2 = getRegisterRecordByDevGame(register.device_id,register.game_id);
		       
				if(register2 == null)
				{
					register.valid = 1;
					registerDao.edit(register);
				}
		        //DebuUtil.log(activate.device_id+"个数:"+reg);
				DebuUtil.log("注册总数:"+list2.size()+"当前:"+reg);
		        reg++;
		   }
		  
	}
	
	public int getAllRegisterNumBefore(String game_id,String business_no,
			String channel_no,String packet_id,String date)
	{
		int ret = 0;
		
		String sql;
		
		//sql = String.format("select * from %s where packet_id='%s' and date<='%s' group by device_id,game_id"
		//		,baseTableName,packet_id,date);
		sql = String.format("select * from %s where date<='%s' and valid=1"
				,baseTableName,date);
		
		if(!StringUtil.is_nullString(packet_id))
		{
			sql += " and packet_id='"+packet_id+"'";
		}
		else
		{
			if(!StringUtil.is_nullString(game_id))
			{
				sql += " and game_id='"+game_id+"'";
			}
			if(!StringUtil.is_nullString(business_no))
			{
				sql += " and business_no='"+business_no+"'";
			}
			if(!StringUtil.is_nullString(channel_no))
			{
				sql += " and channel_no='"+channel_no+"'";
			}
		}
	
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getAllRegisterNumBefore_bc",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		
		DebuUtil.log("sql:"+sql);
		DebuUtil.log("ret:"+ret);
		
		return ret;
	}
	
	public int getAllRegisterNumBefore(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		
		sql = String.format("select * from %s where packet_id='%s' and date<='%s' group by device_id,game_id"
				,baseTableName,packet_id,date);
		//sql = String.format("select * from %s where packet_id='%s' and date<='%s' and valid=1"
		//		,baseTableName,packet_id,date);
		
		//DebuUtil.log(date+"时间注册总数="+ret);
	
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getAllRegisterNumBefore",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		
	   //ret = getRecordCount(sql);
		
		
		return ret;
	}
	
	public int getRealRegisterNum(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		
		sql = String.format("select * from %s where packet_id='%s' and date='%s' and (onlinetime>=300 or level>=10)"
				,baseTableName,packet_id,date);
		
		ret = getRecordCount(sql);
		
		return ret;
	}
	
	public int getRealRegisterNum(String game_id,String business_no,
			String channel_no,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		
		sql = String.format("select * from %s where date='%s' and (onlinetime>=%d or level>=%d)"
				,baseTableName,date,Register.RealOnLineTime,Register.RealLevel);
		
		if(!StringUtil.is_nullString(packet_id))
		{
			sql += " and packet_id='"+packet_id+"'";
		}
		else
		{
			if(!StringUtil.is_nullString(game_id))
			{
				sql += " and game_id='"+game_id+"'";
			}
			if(!StringUtil.is_nullString(business_no))
			{
				sql += " and business_no='"+business_no+"'";
			}
			if(!StringUtil.is_nullString(channel_no))
			{
				sql += " and channel_no='"+channel_no+"'";
			}
		}
		ret = getRecordCount(sql);
		
		DebuUtil.log("sql:"+sql);
		DebuUtil.log("ret:"+ret);
		return ret;
	}
	
	public int getRegAccountNum(String game_id,String packet_id,String date)
	{
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' "
				,baseTableName,packet_id);
		
		sql+=" and date='"+date+"' ";//group by device_id,game_id
		//ret = getRecordCount(sql);//getCount
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getRegAccountNum",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		}
		DebuUtil.log(date+"注册总数="+ret);
		//DebuUtil.log(sql);
		return ret;
	}
	public int getRegAccountNumByLevel(String game_id,String packet_id,String date,int from,int to)
	{
		int ret = 0;
		String sql;
		sql = String.format("select * from %s where packet_id='%s' "
				,baseTableName,packet_id);
		
		sql+=" and date='"+date+"' ";//group by device_id,game_id
		sql+=" and level>="+from;
		if(to > 0)
		sql+=" and level<="+to;
		//ret = getRecordCount(sql);//getCount
		
		ret = getRecordCount(sql);
		
		DebuUtil.log(date+"级别数="+ret);
		//DebuUtil.log(sql);
		return ret;
	}
	
	public int getRegisterNum(String game_id,String business_no,
			String channel_no,String packet_id,String date,String channelType,String type,int bAction)
	{
         int ret = 0;
		
		if(!StringUtil.is_nullString(packet_id))
		{
			ret = getRegisterNum( game_id, packet_id, date, channelType, type, bAction);
		}
		else
		{
			String sql;
			if((channelType.equals("CPA_R")) && (!StringUtil.is_nullString(type)))
			{
				sql = String.format("select * from %s where date='%s'"
								,baseTableName,date);
								
				if(type.equals("A"))
				{
					sql += " and type='A'";
				}
				else if(type.equals("C"))
				{
					sql += " and type='C'";
				}
			}
			else
			{
				sql = String.format("select * from %s where date='%s' and valid=1 "//group by device_id,game_id
	    				,baseTableName,date);
				
			}
			
			
			if(!StringUtil.is_nullString(game_id))
			{
				sql += " and game_id='"+game_id+"'";
			}
			
			if(!StringUtil.is_nullString(business_no))
			{
				sql += " and business_no='"+business_no+"'";
			}
			
			if(!StringUtil.is_nullString(channel_no))
			{
				sql += " and channel_no='"+channel_no+"'";
			}
			
	
			long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
			
			if(days >= 1)
			{
				DataBufDao dao = new DataBufDao();
				ret = dao.GetCount(sql,"getRegisterNum_bc",date,packet_id);
			}
			else
			{
			   ret = getRecordCount(sql);
			} 
			
			DebuUtil.log("sql:"+sql);
			DebuUtil.log("ret:"+ret);
		}
		
		
		//DebuUtil.log(sql);
		return ret;
	}
	
	public int getRegisterNum(String game_id,String packet_id,String date,String channelType,String type,int bAction)
	{
		int ratio;
		String sql = "";
		
		sql = String.format("select settle_ratio from cooperation where app_no='%s' and packet_id='%s'"
				,game_id,packet_id);
		ratio = getCount(sql);
		return getRegisterNum2( game_id, packet_id, date, channelType, type, bAction,ratio);
	}
	
	public int getRegisterNum2(String game_id,String packet_id,String date,String channelType,String type,int bAction,int ratio)
	{
		int ret = 0;
		String sql;
	    int btimeok = 1;
		
		long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days == 0)
		{
			btimeok = 0;
		}
		else if(days == 1)
		{
			if(bAction == 0)
			{
				Date now=new java.util.Date();
				if(now.getHours() < 11)
				{
					DebuUtil.log("次日不到11点不结算");
					btimeok = 0;
				}
			}
		}
		
		if(StringUtil.is_nullString(packet_id))
		{
			btimeok = 0;
		}
		
		if((channelType.equals("CPA_R")) && (btimeok == 1))
		{
			sql = String.format("select * from %s where packet_id='%s' and date='%s'  and type<>'C' and type<>'A' limit 1"
					,baseTableName,packet_id,date);
			
			ArrayList<Object> objList;
			objList = getList(sql);
	        if(objList.size() > 0)
	        {
	        	/*Register obj;
	        	obj = (Register)objList.get(0);
	        	String str = obj.type;
	        	//DebuUtil.log(str);
	        	if(str.equals("A") ||str.equals("C"))
	        	{
	        		
	        		DebuUtil.log("已经结算过了");
	        	}
	        	else*/
	        	{
	        		int settle_ratio = 0;
	        		int ANum = 0;
	        		int CNum = 0;
	        		
	        		DebuUtil.log("开始结算 ");
	        		//sql = String.format("select settle_ratio from cooperation where app_no='%s' and packet_id='%s'"
	        		//		,game_id,packet_id);
	        		//settle_ratio = getCount(sql);
	        		settle_ratio = ratio;
	        		//DebuUtil.log("结算比例= "+sql);
	        		DebuUtil.log("结算比例= "+settle_ratio);
	        		//sql = String.format("select * from %s where packet_id='%s' and date='%s' group by device_id,game_id"
	        		//		,baseTableName,packet_id,date);
	        		sql = String.format("select * from %s where packet_id='%s' and date='%s' and valid=1"
	        				,baseTableName,packet_id,date);
	        		
	        		ret = getRecordCount(sql);
	        		DebuUtil.log("去重注册总数= "+ret);
	        		ANum = (int)((ret*settle_ratio)/100.0+0.5);
	        		CNum = ret - ANum;
	        		//DebuUtil.log("CNum= "+CNum);
	        		DebuUtil.log("ANum= "+ANum);
	        		
	        		//long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
	        		/*sql = String.format("UPDATE %s SET type = '' WHERE game_id='%s' and packet_id='%s' and date='%s' "
	        				,baseTableName,game_id,packet_id,date);       			
        			super.executeUpdate(sql);*/
	        		sql = String.format("select * from %s where packet_id='%s' and date='%s' and type='A'"
	        				,baseTableName,packet_id,date);
	        		int ANumO = getRecordCount(sql);
	        		int count = ANum-ANumO;
	        		DebuUtil.log("ANumO= "+ANumO);
	        		if((count > 0))
	        		{
	        			DebuUtil.log("count= "+count);
	        			sql = String.format("UPDATE %s SET type = 'A' WHERE packet_id='%s' and date='%s' and type<>'C' and type<>'A' limit %d"
		        				,baseTableName,packet_id,date,count);
	        			DebuUtil.log("执行A结算");
	        			//DebuUtil.log(sql);
	        			super.executeUpdate(sql);
	        		}
	        		
	        		sql = String.format("select * from %s where packet_id='%s' and date='%s' and type='C'"
	        				,baseTableName,packet_id,date);
	        		int CNumO = getRecordCount(sql);
	        		count = CNum-CNumO;
	        		DebuUtil.log("CNumO= "+CNumO);
	        		if(count > 0)
	        		{
	        			sql = String.format("UPDATE %s SET type = 'C' WHERE packet_id='%s' and date='%s' and type<>'C' and type<>'A' "
		        				,baseTableName,packet_id,date);
	        			DebuUtil.log("执行C结算");
	        			//DebuUtil.log(sql);
	        			//DebuUtil.log(sql);
	        			super.executeUpdate(sql);
	        		}
	        		
	        		//if(days == 0)
	        		{
	        			//DebuUtil.log("当天不结算");
	        			if(type.equals("A"))
	    	        	{
	    	        		return ANum;
	    	        	}
	        			else if(type.equals("C"))
	    	        	{
	    	        		
	    	        		return CNum;
	    	        	}
	        		}
	        		
	        	}
	        }
			
		}
		
		sql = "";
		//sql = String.format("select * from %s where game_id='%s' and packet_id='%s' and date='%s'"
		//		,baseTableName,game_id,packet_id,date);
		
		if((channelType.equals("CPA_R")) && (!StringUtil.is_nullString(type)))
		{
			sql = String.format("select * from %s where packet_id='%s' and date='%s'"
							,baseTableName,packet_id,date);
			
			/*if(!StringUtil.is_nullString(packet_id))
			{
			   sql+=" and packet_id='"+packet_id+"'";
			}*/
			
			if(type.equals("A"))
			{
				sql += " and type='A'";
			}
			else if(type.equals("C"))
			{
				sql += " and type='C'";
			}
		}
		else
		{
			sql = String.format("select * from %s where packet_id='%s' and date='%s' and valid=1 "//group by device_id,game_id
    				,baseTableName,packet_id,date);
			
			/*if(!StringUtil.is_nullString(packet_id))
			{
			   sql+=" and packet_id='"+packet_id+"'";
			}*/
			//sql+=" group by device_id,game_id";
			//and valid=1
		}
		
		
		//long days = DateUtil.getDayDiff(date,util.DateUtil.getDate());
		if(days >= 1)
		{
			DataBufDao dao = new DataBufDao();
			ret = dao.GetCount(sql,"getRegisterNum",date,packet_id);
		}
		else
		{
		   ret = getRecordCount(sql);
		} 
		//ret = getRecordCount(sql);
		//DebuUtil.log(date+"注册总数="+ret);
		return ret;
		
	}
	
	
	public  void test()
	{
		Register r = new Register();
		
		r.username = "15914051609";
		r.pass = "6670575";
		r.device_id = "352746059407794";
		r.packet_id = "100152001";
		r.game_id = "152";
		r.date = DateUtil.getDate();
		r.time = DateUtil.getTime();
		register(r);
	}

	public boolean charge(String username, int amount)
	{
		//RegisterDao redao = new RegisterDao();
		Register register = null;
		boolean ret = false;
		
		register = getRegisterRecord(username);
		if(register != null)
		{
			int Balance = 0;
			Balance = amount+register.getAccountBalance();
			
			register.setAccountBalance(Balance);
			if(Edit(register))
			{
				ret = true;
			}
			
		}
		
		return ret;
	}
	
	
	public boolean pay(String username, int amount)
	{
		//RegisterDao redao = new RegisterDao();
		Register register = null;
		boolean ret = false;
		register = getRegisterRecord(username);
		if(register != null)
		{
			int Balance = 0;
			Balance = register.getAccountBalance()-amount;
			if(Balance >= 0)
			{
				register.setAccountBalance(Balance);
				if(Edit(register))
				{
					ret = true;
				}
			}
		}
		return ret;
	}
	
	public int login(String user, String pass) {
		int ret = ConstValue.Fail;
		String sql;

		/*
		 * sql = String.format("select * from %s where username='%s'",
		 * baseTableName,user); ArrayList<Object>list = new ArrayList<Object>();
		 * 
		 * list = getList(sql); DebuUtil.log("这是数字？？？？"+list.get(0));
		 * 
		 * int uCount = list.size();
		 */
		Register register;

		register = getRegisterRecord(user);

		if (register != null) {
			// 账户存在，判断账户状态是否锁上
			// register = (Register) list.get(0);
			int account_state = register.getAccountState();
			int lockTime = register.getLockedTime();
			int now = (int) (System.currentTimeMillis() / 1000);
			int tem = Math.abs((now - lockTime)) / 60;
			if (account_state != Register.LOCKED) {// locked==1
				// 账号没有上锁，继续验证密码
				ret = checkPass(pass, register);
			} else if (account_state == Register.LOCKED) {
				if (tem >= 10) {
					// 账号已锁,但是上锁时间超过10分钟，先将账号解锁，清空验证失败次数，清除锁死时间，继续验证密码
					modifyAccountInfo(0, Register.UNLOCK, 0, register);
					ret = checkPass(pass, register);
				}else 
					ret = ConstValue.AccountLocked;
			}

		} else {
			// 账号不存在
			ret = ConstValue.AccountNoExistFail;
		}
		// sql =
		// String.format("select * from %s where username='%s' and pass='%s'",
		// baseTableName,user,pass);
		/*
		 * int count = getRecordCount(sql);
		 * 
		 * if(count > 0) { //DebuUtil.log("用户"+user+"登录成功"); ret =
		 * ConstValue.OK; } else { //DebuUtil.log("用户"+user+"登录失败"); }
		 */
		return ret;
	}

	/**
	 * 当账号存在且未被锁住时，进行密码比较
	 * 
	 * @param pass
	 * @param register
	 * @return
	 */
	private int checkPass(String pass, Register register) {
		int ret = ConstValue.Fail;
		//
		String password = register.getPass();
		if (pass.equals(password)) {
			// 密码匹配,修改验证失败记录数，修改账户状态.修改锁死时间
			modifyAccountInfo(0, ConstValue.UNLOCK, 0, register);
			ret = ConstValue.OK;
		} else {
			// 密码不匹配
			int passfail_times = register.getPassfailTimes();
			DebuUtil.log("失败次数统计passfailtimes:" + passfail_times);
			if (passfail_times < 3) {
				modifyAccountInfo(++passfail_times, ConstValue.UNLOCK, 0,
						register);
				ret = ConstValue.PasswordFail;
			} else if (passfail_times == 3) {
				int now = (int) (System.currentTimeMillis() / 1000);
				modifyAccountInfo(passfail_times, ConstValue.LOCKED, now, register);
				ret = ConstValue.AccountLocked;
			}
		}
		return ret;
	}

	/**
	 * 修改账号的失败次数记录，账号上锁状态，上锁时间
	 * 
	 * @param passfailtimes
	 *            失败次数
	 * @param lockstatus
	 * @param locktime
	 * @param register
	 */
	private void modifyAccountInfo(int passfailtimes, int lockstatus,
			int locktime, Register register) {

		register.setPassfailTimes(passfailtimes);
		register.setAccountState(lockstatus);
		register.setLockedTime(locktime);
		RegisterDao registerDao = new RegisterDao();
		registerDao.edit(register);

	}

	public static int RealUser(Register obj) {
		int ret = ConstValue.Fail;

		ChannelDataDao channelDataDao = new ChannelDataDao();
		channelDataDao.RealUser(obj);
		return ret;
	}
	
	public Register chageOnlinetime(String user,int time,int level)
	{
		//int ret = ConstValue.Fail;
		//DebuUtil.log("修改密码");
		Register register = getRegisterRecord(user);
		if(register != null)
		{
			Boolean bNewUser = true;
			if(register.is_real_user())
			{
				bNewUser = false;
			}
			register.onlinetime += time;	
			register.level = level;
			
			if(!register.is_real_user())
			{
				bNewUser = false;
			}
			edit(register);
			
			if(bNewUser)
			{
				if(ConstValue.OPTIMIZE == 1)
				{
					RealUser(register);
				}
			}
		}
		
		return register;
		
	}
	
	public Register chageLevel(String username,int level)
	{
		//int ret = ConstValue.Fail;
		//DebuUtil.log("修改密码");
		Register register = getRegisterRecord(username);
		if(register != null)
		{
			register.level = level;
			edit(register);
		}
		
		return register;
		
	}
	
	public Register chagepass(String user,String pass,String newpass)
	{
		//int ret = ConstValue.Fail;
		//DebuUtil.log("修改密码");
		Register register = getRegisterRecord(user);
		if(register != null)
		{
			if(register.getPass().equals(pass))
			{
				//DebuUtil.log("找到记录修改");
				register.pass = newpass;
				edit(register);
			}
		}
		
		return register;
		
	}
	
	//sql = String.format("select * from %s where packet_id='%s' and packet_id='%s'", baseTableName,packet);
	public ArrayList<Object> getRegisterList(String sql)
	{
		Register register = null;
		ArrayList<Object> objList;
		//String sql = "";
		//sql = String.format("select * from %s where username='%s'", baseTableName,username);
		
		
		objList = getList(sql);
		//DebuUtil.log(sql);
		return objList;
	}
	
	
	public ArrayList<Object> getRegisterList(String username,String device)
	{
		Register register = null;
		ArrayList<Object> objList;
		String sql = "";
		//sql = String.format("select * from %s where username='%s'", baseTableName,username);
		if(!StringUtil.is_nullString(username))
		{
			sql = String.format("select * from %s where username='%s'", baseTableName,username);
		}
		else if(!StringUtil.is_nullString(device))
		{
			sql = String.format("select * from %s where device_id='%s'", baseTableName,device);
		}
		objList = getList(sql);
		//DebuUtil.log(sql);
		return objList;
	}
	
	public Register getRegisterRecord(String username)
	{
		Register register = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where username='%s'", baseTableName,username);
		
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			register = (Register)objList.get(0);
		}
		return register;
	}
	
	public Register getRecordByPhoneNum(String phoneNum)
	{
		Register register = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where phone_num='%s'", baseTableName,phoneNum);
		
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			register = (Register)objList.get(0);
		}
		return register;
	}
	
	public Register getRegisterRecordByDevGame(String device,String game_id)
	{
		Register register = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where device_id='%s' and game_id='%s'", baseTableName,device,game_id);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			register = (Register)objList.get(0);
		}
		return register;
	}
	
	public Register getRegisterRecordByDev(String device)
	{
		Register register = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where device_id='%s'", baseTableName,device);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			register = (Register)objList.get(0);
		}
		return register;
	}
	
	
	 public static int register(Register obj,RegisterDao registerDao)
	{
		int ret = ConstValue.Fail;
		/*String sql;
		sql = String.format("select * from %s where username='%s'", registerDao.baseTableName,obj.getUsername());
		int count = registerDao.getRecordCount(sql);
	
		//DebuUtil.log("查询注册 结果="+count);
		if(count < 1)
		{
			if(registerDao.getRegisterRecordByDevGame(obj.getDeviceId(),obj.getGameId()) == null)
			{
				DebuUtil.log("设备第一次注册 ");
				ActivateDao activateDao = new ActivateDao();
				Activate activate=activateDao.getActivateRecord(obj.getDeviceId(),obj.getGameId());
				activate.setReg(1);
				activateDao.edit(activate);
				obj.setValid(1);
				
				ChannelDataDao channelDataDao = new ChannelDataDao();
				channelDataDao.register(obj);
				
				DebuUtil.log("修改注册激活标志");
			}
			else
			{
				obj.setValid(0);
			}
			
			registerDao.add(obj);
		   
		   //添加登录记录
		   model.Login login = new model.Login();
		   login.setDate(obj.getDate());
		   login.setDeviceId(obj.getDeviceId());
		   login.setGameId(obj.getGameId());
		   login.setPacketId(obj.getPacketId());
		   login.setPass(obj.getPass());
		   login.setTime(obj.getTime());
		   login.setUsername(obj.getUsername());
		   
		   LoginDao logindao = new LoginDao();
		   logindao.add(login);
	
		   ret = ConstValue.OK;
		   //DebuUtil.log("用户"+obj.getUsername()+"注册成功");
		}
		else
		{
			//DebuUtil.log("用户"+obj.getUsername()+"注册失败");
		}*/
		ChannelDataDao channelDataDao = new ChannelDataDao();
		channelDataDao.register(obj);
		return ret;
	}
	
	public int register(Register obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where username='%s'", baseTableName,obj.getUsername());
		int count = getRecordCount(sql);
	    int bFirst = 0;
	    Activate activate = null;
		//DebuUtil.log("查询注册 结果="+count);
		if((count < 1)&&(!StringUtil.is_nullString(obj.getPass())))
		{
			 //添加登录记录
		   model.Login login = new model.Login();
		   login.setDate(obj.getDate());
		   login.setDeviceId(obj.getDeviceId());
		   login.setGameId(obj.getGameId());
		   login.setPacketId(obj.getPacketId());
		   login.setPass(obj.getPass());
		   login.setTime(obj.getTime());
		   login.setUsername(obj.getUsername());
		   	   
			if(getRegisterRecordByDevGame(obj.getDeviceId(),obj.getGameId()) == null)
			{
				DebuUtil.log("设备第一次注册 ");
				ActivateDao activateDao = new ActivateDao();
				activate=activateDao.getActivateRecord(obj.getDeviceId(),obj.getGameId());
				if(activate != null)
				{
					activate.setReg(1);
					LoginDao.loginData(login, null, 0,1);
					activate.loginDate = obj.date;
					activateDao.edit(activate);
				}
				obj.setValid(1);
				if(ConstValue.OPTIMIZE == 1)
				{
				  register(obj,this);
				  bFirst = 1;
				}
				DebuUtil.log("修改注册激活标志");
			}
			else
			{
				obj.setValid(0);
			}
			
		   add(obj);
		   
		  
		   
		   LoginDao logindao = new LoginDao();
		   logindao.add(login);//lsl
		   if(ConstValue.OPTIMIZE == 1)
		   {
			   if(activate == null)
			   {
			       ActivateDao activateDao = new ActivateDao();
			       activate=activateDao.getActivateRecord(obj.getDeviceId(),obj.getGameId());
			   }
			   
			  if(activate != null) 
			  {
				  int bnew = 0;
				  if(StringUtil.is_nullString(activate.loginDate))
				  {
					  bnew = 1;
				  }
				  
				  if((!StringUtil.is_nullString(activate.loginDate)) && activate.loginDate.equals(obj.date))
				  {
					  
				  }
				  else
				  {
					  LoginDao.loginData(login, null, 0,bnew);
					  activate.loginDate = obj.date;
					  ActivateDao activateDao = new ActivateDao();
					  activateDao.edit(activate);				  
				  }
			  }
			   
		   }
		   ret = ConstValue.OK;
		   //DebuUtil.log("用户"+obj.getUsername()+"注册成功");
		}
		else
		{
			//DebuUtil.log("用户"+obj.getUsername()+"注册失败");
		}
		return ret;
	}
	
	
	
	/**
	 * 根据用户名，查询绑定手机号
	 * @param username
	 * @return
	 */
	public String getPhoneNumByUsrName(String username){
		String phoneNum = "";
		String sql = String.format("select phone_num from register where username='%s'", username);
		
		phoneNum = (String) getObject(sql).get(0);
		
		return phoneNum;
	}
	
	/**
	 * 根据用户名查询其绑定的号码 若账号已绑定   返回true 若未绑定   返回false
	 * 
	 * @param username
	 * @return boolean
	 */
	public boolean checkIdentiStatus(String username) {
		String identiNum = "";
		identiNum = getPhoneNumByUsrName(username);
		if (identiNum==null) {
			return false;
		} else {
			return true;
		}

	}
	/**
	 * 根据用户名查询拇指币余额
	 * @param username
	 * @return
	 */
	public int getBalanceByName(String username){
		String sql = String.format("select account_balance from register where username='%s'", username);
		return (Integer)getObject(sql).get(0);
	}
}
