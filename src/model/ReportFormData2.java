package model;

import java.util.ArrayList;

import dao.ChannelDataDao;

import util.StringUtil;

public class ReportFormData2 {

	private String gameName;
	private String gameID;

	private String businesserName;
	private String packetId;
	private String channelName;

	private Integer activateNum;
	private Integer registerNum;
	private Integer day1;
	private Integer day7;
	private Integer day30;
	private Integer thirdPay;
	private Integer callPay;
	private Integer consumerNum;
	
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameID() {
		return gameID;
	}
	public void setGameID(String gameID) {
		this.gameID = gameID;
	}
	public String getBusinesserName() {
		return businesserName;
	}
	public void setBusinesserName(String businesserName) {
		this.businesserName = businesserName;
	}
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
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
	
	public Integer getDay1() {
		return day1;
	}
	public void setDay1(Integer day1) {
		this.day1 = day1;
	}
	public Integer getDay7() {
		return day7;
	}
	public void setDay7(Integer day7) {
		this.day7 = day7;
	}
	public Integer getDay30() {
		return day30;
	}
	public void setDay30(Integer day30) {
		this.day30 = day30;
	}
	public Integer getThirdPay() {
		return thirdPay;
	}
	public void setThirdPay(Integer thirdPay) {
		this.thirdPay = thirdPay;
	}
	public Integer getCallPay() {
		return callPay;
	}
	public void setCallPay(Integer callPay) {
		this.callPay = callPay;
	}
	public Integer getConsumerNum() {
		return consumerNum;
	}
	public void setConsumerNum(Integer consumerNum) {
		this.consumerNum = consumerNum;
	}
	public static ArrayList<ReportFormData2> getDataList(String fromData,
			String toDate, String gameType, String gameNo, String channelNo) {
		ArrayList<ReportFormData2> list = new ArrayList<ReportFormData2>();
		String sql = "select app.name as '游戏名',app.no as '游戏ID',businesser.name as '商务',"
				+ "channel_data.packet_id as '包号',channel.name '渠道名',"
				+ "sum(activity_num) as '激活用户数',SUM(register_num) as '注册用户数',"
				+ "SUM(channel_data.day1_num) as '次留',SUM(channel_data.day7_num) as '7留',SUM(channel_data.day30_num) as '月留',"
				+ "(SUM(settle_ali_pay)+SUM(settle_ten_pay)+SUM(settle_union_pay)+SUM(settle_yee_pay)+SUM(settle_mz_pay)+SUM(IFNULL(settle_wx_pay,0)))/100 as '第三方支付',"
				+ "(SUM(settle_mm_pay)+SUM(settle_tc_pay)+SUM(settle_uni_pay))/100 as '话费支付总额',sum(day_all_pay_acount) as '付费用户数' "
				+ "from app,channel,businesser,channel_data WHERE app.no=channel_data.game_id and "
				+ "channel.no=channel_data.channel and channel_data.business_no=businesser.no ";

		if (!StringUtil.is_nullString(gameNo)) {
			sql += String.format(" and channel_data.game_id='%s' ", gameNo);
		}
		if (!StringUtil.is_nullString(channelNo)) {
			sql += String.format(" and channel_data.channel='%s' ", channelNo);
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
		
		if (StringUtil.is_nullString(channelNo)) {
			sql += "ORDER BY game_id ";
		}
		System.out.println(sql);
		ChannelDataDao dao = new ChannelDataDao();
		ArrayList<ArrayList> list1 = dao.getObjectList(sql);
		if (list1.size() > 0) {
			// 自己看的数据
			for (int i = 0; i < list1.size(); i++) {
				ArrayList<Object> list2 = list1.get(i);
				System.out.println(list2.toString());

				ReportFormData2 data = new ReportFormData2();
				data.setGameName(list2.get(0).toString());
				data.setGameID(list2.get(1).toString());
				data.setBusinesserName(list2.get(2).toString());
				data.setPacketId(list2.get(3).toString());
				data.setChannelName(list2.get(4).toString());
				data.setActivateNum(Integer.valueOf(list2.get(5).toString()));
				data.setRegisterNum(Integer.valueOf(list2.get(6).toString()));
				data.setDay1(Integer.valueOf(list2.get(7).toString()));
				data.setDay7(Integer.valueOf(list2.get(8).toString()));
				data.setDay30(Integer.valueOf(list2.get(9).toString()));
				data.setThirdPay(getInt(list2.get(10).toString()));
				data.setCallPay(getInt(list2.get(11).toString()));
				data.setConsumerNum(Integer.valueOf(list2.get(12).toString()));
				list.add(data);
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
