package model;

import java.util.ArrayList;

import util.StringUtil;
import dao.ChannelDataDao;

public class ReportFormData {
	private String gameName;
	private String channelName;
	private String businesserName;
	private String gameNo;
	private String packetId;
	private Integer activateNum;
	private Integer registerNum;
	private Integer aliPay;
	private Integer yeePay;
	private Integer unionPay;
	private Integer mzPay;
	private Integer onlyPay;
	private Integer tenPay;
	private Integer mmPay;
	private Integer uniPay;
	private Integer tcPay;
	private Integer wxPay;
	
	public String getBusinesserName() {
		return businesserName;
	}
	public void setBusinesserName(String businesserName) {
		this.businesserName = businesserName;
	}
	
	public Integer getMmPay() {
		return mmPay;
	}
	public void setMmPay(Integer mmPay) {
		this.mmPay = mmPay;
	}
	public Integer getUniPay() {
		return uniPay;
	}
	public void setUniPay(Integer uniPay) {
		this.uniPay = uniPay;
	}
	public Integer getTcPay() {
		return tcPay;
	}
	public void setTcPay(Integer tcPay) {
		this.tcPay = tcPay;
	}
	private Integer amountAll;
	
	public String getGameNo() {
		return gameNo;
	}
	public void setGameNo(String gameNo) {
		this.gameNo = gameNo;
	}
	public String getPacketId() {
		return packetId;
	}
	public Integer getWxPay() {
		return wxPay;
	}
	public void setWxPay(Integer wxPay) {
		this.wxPay = wxPay;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public Integer getMzPay() {
		return mzPay;
	}
	public void setMzPay(Integer mzPay) {
		this.mzPay = mzPay;
	}
	public Integer getOnlyPay() {
		return onlyPay;
	}
	public void setOnlyPay(Integer onlyPay) {
		this.onlyPay = onlyPay;
	}
	
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public Integer getActivateNum() {
		return activateNum;
	}
	public void setActivateNum(Integer activateNum) {
		this.activateNum = activateNum;
	}
	public Integer getRegisterNum() {
		return registerNum;
	}
	public void setRegisterNum(Integer registerNum) {
		this.registerNum = registerNum;
	}
	public Integer getAliPay() {
		return aliPay;
	}
	public void setAliPay(Integer aliPay) {
		this.aliPay = aliPay;
	}
	public Integer getYeePay() {
		return yeePay;
	}
	public void setYeePay(Integer yeePay) {
		this.yeePay = yeePay;
	}
	public Integer getUnionPay() {
		return unionPay;
	}
	public void setUnionPay(Integer unionPay) {
		this.unionPay = unionPay;
	}
	public Integer getTenPay() {
		return tenPay;
	}
	public void setTenPay(Integer tenPay) {
		this.tenPay = tenPay;
	}
	public Integer getAmountAll() {
		return amountAll;
	}
	public void setAmountAll(Integer amountAll) {
		this.amountAll = amountAll;
	}
	
	
	@Override
	public String toString() {
		return "ReportFormData [gameName=" + gameName + ", channelName="
				+ channelName + ", gameNo=" + gameNo + ", packetId=" + packetId
				+ ", activateNum=" + activateNum + ", registerNum="
				+ registerNum + ", aliPay=" + aliPay + ", yeePay=" + yeePay
				+ ", unionPay=" + unionPay + ", tenPay=" + tenPay + ", mmPay="
				+ mmPay + ", uniPay=" + uniPay + ", tcPay=" + tcPay + ", wxPay=" + wxPay
				+ ", amountAll=" + amountAll + "]";
	}
	public static ArrayList<ReportFormData> getDataList(String fromData,String toDate,String gameType,String BSS_NO,
			String gameNo,String channelNo,String type){
		ArrayList<ReportFormData> list = new ArrayList<ReportFormData>();
		
		String sql = "select app.name,channel_data.packet_id,channel.name,SUM(register_num),sum(activity_num)," +
				"SUM(ali_pay)/100,sum(ten_pay)/100,SUM(yee_pay)/100,SUM(union_pay)/100," +
				"SUM(settle_ali_pay)/100,SUM(settle_ten_pay)/100,SUM(settle_union_pay)/100," 
				+"SUM(settle_yee_pay)/100,app.no,SUM(mm_pay)/100,SUM(tc_pay)/100,SUM(uni_pay)/100,"+
				"SUM(settle_mm_pay)/100,SUM(settle_tc_pay)/100,SUM(settle_uni_pay)/100," +
				"SUM(mz_pay)/100,SUM(only_pay)/100,SUM(settle_mz_pay)/100,SUM(settle_only_pay)/100,businesser.name,SUM(IFNULL(wx_pay,0))/100,SUM(IFNULL(settle_wx_pay,0))/100" +
				" from app,channel,businesser,channel_data " +
				"WHERE app.no=channel_data.game_id and " +
				"channel.no=channel_data.channel and channel_data.business_no=businesser.no " ;
		if (!StringUtil.is_nullString(gameNo))
		{
			sql += String.format(" and channel_data.game_id='%s' " , gameNo);
		}
		if (!StringUtil.is_nullString(channelNo))
		{
			sql += String.format(" and channel_data.channel='%s' " , channelNo);
		}
		if (!StringUtil.is_nullString(BSS_NO))
		{
			sql += String.format(" and channel_data.business_no='%s' " , BSS_NO);
		}
		if (!StringUtil.is_nullString(gameType))
		{
			sql += String.format(" and app.game_type='%s' " , gameType);
		}
		if (!StringUtil.is_nullString(fromData))
		{
			sql += String.format(" and channel_data.date>='%s' " , fromData);
		}
		if (!StringUtil.is_nullString(toDate))
		{
			sql += String.format(" and channel_data.date<='%s' " , toDate);
		}
		sql += " GROUP BY channel_data.packet_id ";
		
		System.out.println(sql);
		ChannelDataDao dao = new ChannelDataDao();
		//BaseDao dao = new BaseDao();
		ArrayList<ArrayList>list1 = dao.getObjectList(sql);
		if (list1.size()>0) {
			//如果有数据，就看需要数据的类型
			if (type.equals("")) {
				//自己看的数据
				for(int i = 0;i<list1.size();i++){
					ArrayList<Object> list2 = list1.get(i);
					System.out.println(list2.toString());
					
					ReportFormData data = new ReportFormData();
					data.setGameName(list2.get(0).toString());
					data.setPacketId(list2.get(1).toString());
					data.setChannelName(list2.get(2).toString());
					data.setRegisterNum(Integer.valueOf(list2.get(3).toString()));
					data.setActivateNum(Integer.valueOf(list2.get(4).toString()));
					data.setAliPay(getInt(list2.get(5).toString()));
					data.setTenPay(getInt(list2.get(6).toString()));
					data.setYeePay(getInt(list2.get(7).toString()));
					data.setUnionPay(getInt(list2.get(8).toString()));
					data.setGameNo(list2.get(13).toString());
					data.setMmPay(getInt(list2.get(14).toString()));
					data.setUniPay(getInt(list2.get(15).toString()));
					data.setTcPay(getInt(list2.get(16).toString()));
					data.setMzPay(getInt(list2.get(20).toString()));
					data.setOnlyPay(getInt(list2.get(21).toString()));
					data.setBusinesserName(list2.get(24).toString());
					data.setWxPay(getInt(list2.get(25).toString()));
					
					data.setAmountAll(data.getAliPay()+data.getTenPay()+data.getYeePay()
							+data.getUnionPay()+data.getMmPay()+data.getUniPay()+data.getTcPay()
							+data.getMzPay()+data.getOnlyPay()+data.getWxPay());
					list.add(data);
					
				}
				
			}else if (type.equals("A")) {
				//渠道看的数据
				for(int i = 0;i<list1.size();i++){
					ArrayList<Object> list2 = list1.get(i);
					System.out.println(list2.toString());
					ReportFormData data = new ReportFormData();
					data.setGameName(list2.get(0).toString());
					data.setPacketId(list2.get(1).toString());
					data.setChannelName(list2.get(2).toString());
					data.setRegisterNum(Integer.valueOf(list2.get(3).toString()));
					data.setActivateNum(Integer.valueOf(list2.get(4).toString()));
					data.setAliPay(getInt(list2.get(9).toString()));
					data.setTenPay(getInt(list2.get(10).toString()));
					data.setYeePay(getInt(list2.get(11).toString()));
					data.setUnionPay(getInt(list2.get(12).toString()));
					data.setGameNo(list2.get(13).toString());
					data.setMmPay(getInt(list2.get(17).toString()));
					data.setUniPay(getInt(list2.get(18).toString()));
					data.setTcPay(getInt(list2.get(19).toString()));
					data.setMzPay(getInt(list2.get(22).toString()));
					data.setOnlyPay(getInt(list2.get(23).toString()));
					data.setBusinesserName(list2.get(24).toString());
					data.setWxPay(getInt(list2.get(25).toString()));
					
					data.setAmountAll(data.getAliPay()+data.getTenPay()+data.getYeePay()+data.getUnionPay()
							+data.getMmPay()+data.getUniPay()+data.getTcPay()
							+data.getMzPay()+data.getOnlyPay()+data.getWxPay());
					list.add(data);
					
				}
				
			
		}else{
			System.out.println("没找到数据");
		}
		
		}
		return list;
	}
	
	
	private static Integer getInt(String string) {
		String str[] = new String[2];
		str = string.split("\\.");
		//System.out.println(str.length);
		int ret = Integer.valueOf(str[0]);
		return ret;
	}
}
