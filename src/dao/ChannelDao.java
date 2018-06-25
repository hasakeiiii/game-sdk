package dao;

import java.util.ArrayList;
import java.util.List;

import model.App;
import model.Businesser;
import model.Channel;
import model.Cooperation;
import util.ConstValue;
import util.DebuUtil;
import azul.BaseDao;
import azul.CacheDao;

public class ChannelDao extends CacheDao{
	public ChannelDao() {
		init();
	}
	public void test()
	{
		Channel obj = new Channel();
		
		obj.setNo("001");
		obj.setName("zty");
		obj.setAddr("shenzhen");
		obj.setContact("lsl");
		addChannel(obj);
		//String sql = String.format("insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','%s','中泰源公司提供',0)", "元宝");
		//String sql = "insert into ali_pay (pay_no,ali_pay_no,goods_name,goods_details,ret) values ('no123456789','ali123456789','元宝','中泰源公司提供',0)";
		//this.executeUpdate(sql);
	}
	
	public static int get_SettleRatio(String game_id,String packet_id,String date,String channelType,String type)
	{
		int ret = 0;
		return ret;
	}
	
	public  String getChannelName(String no)//
	{
		String ChannelName = "";
		Channel channel = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			channel = (Channel)objList.get(0);
			ChannelName = channel.name;
		}
		return ChannelName;
	}
	
	public Channel getRecord(String no)
	{
		Channel cooperation = null;
		ArrayList<Object> objList;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,no);
		objList = getList(sql);
		//DebuUtil.log(sql);
		if(objList.size() > 0)
		{
			cooperation = (Channel)objList.get(0);
		}
		return cooperation;
	}
	public int addChannel(Channel obj)
	{
		int ret = ConstValue.Fail;
		String sql;
		sql = String.format("select * from %s where no='%s'", baseTableName,obj.getNo());
		int count = getRecordCount(sql);
	
		DebuUtil.log("渠道表结果="+count);
		if(count < 1)
		{
		   add(obj);
		   ret = ConstValue.OK;
		   DebuUtil.log("添加成功");
		}
		return ret;
	}	
	
	public List<ArrayList> getAllChannelByGameNo(String gameNo){
		String sql = "SELECT  	DISTINCT channel.no,channel.name FROM app,channel,cooperation where app.no = cooperation.app_no " +
				" AND channel.no = cooperation.channel_no AND cooperation.app_no = '%s'";
		sql = String.format(sql, gameNo);
		
		List<ArrayList> list = getObjectList(sql);
		return list;
	}
	
	public List<Channel> getAllChannel(){
		String sql = "SELECT * FROM channel";
		List<Channel> list = getList(sql);
		return list;
	}
	
	public List<Channel> getAllChannelByBusinessNo(String businessNo){
		String sql = "SELECT ch.* FROM channel ch,cooperation co where co.channel_no = ch.no and co.business_no = '%s'";
		sql = String.format(sql, businessNo);
		List<Channel> list = getList(sql);
		return list;
	}
	public List<ArrayList> getAllChannelByGameNoAndBusinessNo(String gameNo,
			String businessNo) {
		String sql = "SELECT  	DISTINCT channel.no,channel.name FROM app,channel,cooperation where app.no = cooperation.app_no " +
				" AND channel.no = cooperation.channel_no AND cooperation.app_no = '%s'  AND cooperation.business_no = '%s'";
		sql = String.format(sql, gameNo,businessNo);
		List<ArrayList> list = getObjectList(sql);
		
		return list;
	}
}
