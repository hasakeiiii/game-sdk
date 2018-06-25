package model;

import java.util.ArrayList;
import java.util.Collections;

import util.DateUtil;
import util.DebuUtil;
import dao.ChannelDataDao;

public class AnalysisData  implements Comparable<AnalysisData>{
	private String date;
	private String gameName;
	private String channelName;
	private String packetId;
	private String mmPay;
	private String pay_times;
	private String allpay_times;
	private String allpay;
	private String paychgratio;
	private Integer pay_user_num;
	
	public Integer getPayUserNum() {
		return pay_user_num;
	}
	public void setPayUserNum(Integer pay_user_num) {
		this.pay_user_num = pay_user_num;
	}
	
	public String getPaychgratio() {
		return paychgratio;
	}
	public void setPaychgratio(String paychgratio) {
		this.paychgratio = paychgratio;
	}
	public String getAllpay() {
		return allpay;
	}
	public void setAllpay(String allpay) {
		this.allpay = allpay;
	}
	public String getAllpay_times() {
		return allpay_times;
	}
	public void setAllpay_times(String allpay_times) {
		this.allpay_times = allpay_times;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getPay_times() {
		return pay_times;
	}
	public void setPay_times(String pay_times) {
		this.pay_times = pay_times;
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
	public String getPacketId() {
		return packetId;
	}
	public void setPacketId(String packetId) {
		this.packetId = packetId;
	}
	public String getMmPay() {
		return mmPay;
	}
	public void setMmPay(String mmPay) {
		this.mmPay = mmPay;
	}
	
	public int compareTo(AnalysisData analysisData)
	{
        return Integer.valueOf(this.getAllpay())-Integer.valueOf(analysisData.getAllpay());
    }
	
	@Override
	public String toString() {
		return "AnalysisData [date=" + date + ", gameName=" + gameName
				+ ", channelName=" + channelName + ", packetId=" + packetId
				+ ", mmPay=" + mmPay + ", pay_times=" + pay_times
				+ ", allpay_times=" + allpay_times + ", allpay=" + allpay
				+ ", paychgratio=" + paychgratio + "]";
	}
	public static ArrayList<AnalysisData> getDataList(String fromDate,String toDate,
			String gameNo,String channelNo){
		ArrayList<AnalysisData> list = new ArrayList<AnalysisData>();
		String datenow = DateUtil.getDate();
		if(toDate.equals(datenow))
			toDate=DateUtil.getYesterday(toDate);
			
		String frombreforeDate = DateUtil.getYesterday(fromDate);
		
		/*DebuUtil.log("起始日期的前一天："+frombreforeDate);
		DebuUtil.log("截止日期："+toDate);
		System.out.println("起始日期的前一天："+frombreforeDate);
		System.out.println("截止日期："+toDate);*/
		
		String sql = "select channel_data.date,app.name," +
				"channel_data.packet_id,channel.name as channelname," +
				"(mm_pay+uni_pay+tc_pay+open_pay+wo_pay)/100 as mm_pay,channel_data.fee_num from app,channel,channel_data " +
				"WHERE app.no=channel_data.game_id and channel.no=channel_data.channel " +
				"and  game_type='off_line' and "+
				"channel_data.date>='%s' and " +
				"channel_data.date<='%s'";
		sql = String.format(sql, fromDate,toDate);
	
		if (channelNo.equals("")&&!gameNo.equals("")) {
			//渠道号为空  游戏id不为空
			sql += " and channel_data.game_id='%s' "; 
			
			sql = String.format(sql, gameNo);

			
		}else if (gameNo.equals("")&&!channelNo.equals("")){
			//渠道号不为空，游戏id为空
			sql += " and channel_data.channel='%s' ";
			sql = String.format(sql, channelNo);
		}else if(!gameNo.equals("")&&!channelNo.equals("")){
			//游戏id不为空  渠道号不为空
				sql +=
						" and channel_data.game_id='%s' and " +
						"channel_data.channel='%s'";
				sql = String.format(sql, gameNo,channelNo);
			}
		DebuUtil.log(sql);
//		System.out.println(sql);
		ChannelDataDao dao = new ChannelDataDao();
		
		ArrayList<ArrayList<Object>>list1 = dao.getObjectList(sql);
		float lastdayPay = 0;
		String tempId = "";
		String paychangeR = "";
		
		if (list1.size()>0) {
			for(int i = 0;i<list1.size();i++){
				ArrayList<Object> list2 = list1.get(i);
				AnalysisData data = new AnalysisData();
				
				data.setDate(list2.get(0).toString());
				data.setGameName(list2.get(1).toString());
				data.setPacketId(list2.get(2).toString());
				data.setChannelName(list2.get(3).toString());
				data.setMmPay(list2.get(4).toString());
				data.setPay_times(list2.get(5).toString());
				
				String nowPId = data.getPacketId();
				String nowmmpay = data.getMmPay();
				
				if(!tempId.equals(nowPId)){
					
					String psql = String.format("select (mm_pay+uni_pay+tc_pay+open_pay+wo_pay) from channel_data where" +
								" date='%s' and packet_id='%s'", frombreforeDate,nowPId);
					//DebuUtil.log("查询昨天的收入语句："+psql);
//					System.out.println("查询昨天的收入语句："+psql);
					ArrayList<Object> obj = dao.getObject(psql);
					if (obj.size()>0) {
						float a = Float.valueOf(obj.get(0).toString());
						if (a!=0) {
							//昨日收入不为0时
							lastdayPay = a/100;
							paychangeR = (Float.valueOf(nowmmpay)-lastdayPay)/lastdayPay*100+"";
							paychangeR = AnalysisData.formatPay(paychangeR)+"%";
						}else{
							paychangeR = "昨日收入为0";
						}
						DebuUtil.log("昨天的收入："+lastdayPay);
					}
					
				}else{
					paychangeR = (Float.valueOf(nowmmpay)-lastdayPay)/lastdayPay*100+"";
					paychangeR = AnalysisData.formatPay(paychangeR)+"%";
				}
				data.setPaychgratio(paychangeR);
				
				tempId = nowPId;
				lastdayPay = Float.valueOf(nowmmpay);
				
				
				String sumPayTimessql = String.format("select sum(fee_num),sum(mm_pay+uni_pay+tc_pay+open_pay+wo_pay)/100.0" +
						" from channel_data where packet_id='%s'  " +
						"and date>='%s' and date<='%s'",data.getPacketId(),fromDate,data.getDate() );
				ArrayList<Object> obj = dao.getObject(sumPayTimessql);
				String sumtimes = obj.get(0).toString();
				String sumpay = obj.get(1).toString();
				
				data.setAllpay_times(sumtimes);
				data.setAllpay(sumpay);
				
				//计算用户数
				 sql = String.format("select * " +
						" from pay where game_id='%s' and packet_id='%s' " +
						" and date='%s'",gameNo,data.getPacketId(),data.getDate() );
				sql += " and state = 1 " + " GROUP BY device_id" ;
				int payUserNum = dao.getRecordCount(sql);
				data.setPayUserNum(payUserNum);
				System.out.println(sql);
				list.add(data);
			     
			}
		}
		//list.sort(arg0);
		//Collections.sort(list);
		return list;
	}
	
	
	
	/**
	 * 奖金额字符串转为保留两位小数的字符串
	 * @param paystr
	 * @return
	 */
	public static String formatPay(String paystr){
		float a = 0;
		a = Float.valueOf(paystr);
		a = (float)(Math.round(a*100))/100;
		paystr = String.valueOf(a);
		return paystr;
	}
	
}
