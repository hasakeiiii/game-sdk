package model;

import java.util.ArrayList;

import util.DebuUtil;
import dao.ChannelDataDao;
import dao.PayDao;
/**
 * 
 * 网游数据分析详情
 *
 */
public class Gamedata1 {

	private String date;
	private String gameName;
	private String channelName;
	private String activateNum;
	private String registerNum;
	private String aRrate;
	private String loginNum;
	private String day1;
	private String day7;
	private String day30;
	private String newUserPay;
	private String newUserPayRate;
	private String allPay;
	private String payRate;
	private String aRPPU;
	private String aRPU;

	@Override
	public String toString() {
		return "Gamedata1 [date=" + date + ", gameName=" + gameName
				+ ", channelName=" + channelName + ", activateNum="
				+ activateNum + ", registerNum=" + registerNum + ", aRrate="
				+ aRrate + ", loginNum=" + loginNum + ", day1=" + day1
				+ ", day7=" + day7 + ", day30=" + day30 + ", newUserPay="
				+ newUserPay + ", newUserPayRate=" + newUserPayRate
				+ ", allPay=" + allPay + ", payRate=" + payRate + ", aRPPU="
				+ aRPPU + ", aRPU=" + aRPU + "]";
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
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



	public String getActivateNum() {
		return activateNum;
	}



	public void setActivateNum(String activateNum) {
		this.activateNum = activateNum;
	}



	public String getRegisterNum() {
		return registerNum;
	}



	public void setRegisterNum(String registerNum) {
		this.registerNum = registerNum;
	}



	public String getaRrate() {
		return aRrate;
	}



	public void setaRrate(String aRrate) {
		this.aRrate = aRrate;
	}



	public String getLoginNum() {
		return loginNum;
	}



	public void setLoginNum(String loginNum) {
		this.loginNum = loginNum;
	}



	public String getDay1() {
		return day1;
	}



	public void setDay1(String day1) {
		this.day1 = day1;
	}



	public String getDay7() {
		return day7;
	}



	public void setDay7(String day7) {
		this.day7 = day7;
	}



	public String getDay30() {
		return day30;
	}



	public void setDay30(String day30) {
		this.day30 = day30;
	}



	public String getNewUserPay() {
		return newUserPay;
	}



	public void setNewUserPay(String newUserPay) {
		this.newUserPay = newUserPay;
	}



	public String getNewUserPayRate() {
		return newUserPayRate;
	}



	public void setNewUserPayRate(String newUserPayRate) {
		this.newUserPayRate = newUserPayRate;
	}



	public String getAllPay() {
		return allPay;
	}



	public void setAllPay(String allPay) {
		this.allPay = allPay;
	}



	public String getPayRate() {
		return payRate;
	}



	public void setPayRate(String payRate) {
		this.payRate = payRate;
	}



	public String getaRPPU() {
		return aRPPU;
	}



	public void setaRPPU(String aRPPU) {
		this.aRPPU = aRPPU;
	}



	public String getaRPU() {
		return aRPU;
	}



	public void setaRPU(String aRPU) {
		this.aRPU = aRPU;
	}



	/**
	 * 运营分析报表调用
	 * @param bdate
	 * @param edate
	 * @param business_no
	 * @param game_no
	 * @param channel_no
	 * @param packet_id
	 * @return
	 */
	public static ArrayList<Gamedata1> getDataList(String bdate,String edate,
			String business_no,String game_no,
			String channel_no,String packet_id){
		String sql = "SELECT date,app.name as app_name,game_id,channel.name as channel_name ,channel,activity_num,register_num,"+
		"login_num,day1_num,day7_num,day30_num,"+
		"(newali_pay+newten_pay+newunion_pay+newyee_pay+newmz_pay+newonly_pay)/100 as newuserpay,"+
		"day_all_acount as newuser_num,"+
		"day_all_pay_acount as newpay_usernum,"+
		"(ali_pay+ten_pay+yee_pay+mz_pay+only_pay)/100 as allpay "+
		"from channel_data,app,channel "+ 
		"WHERE app.no=channel_data.game_id and channel.no=channel_data.channel "+
		"and game_type='on_line' ";
		if (business_no!=null&&!business_no.isEmpty()) {
			sql = String.format(sql+"and business_no='%s' ",business_no);
		}
		if (packet_id!=null&&!packet_id.isEmpty()) {
			sql = String.format(sql+"and packet_id='%s' ",packet_id);
		}else{
			if (game_no!=null&&!game_no.isEmpty()) {
				sql = String.format(sql+"and game_id='%s' ",game_no);
			}
			if (channel_no!=null&&!channel_no.isEmpty()) {
				sql = String.format(sql+"and channel='%s' ",channel_no);
			}
		}
		
		sql = String.format(sql+" and date>='%s' and date<='%s' ",bdate,edate);
		sql +="order by game_id,date";
		DebuUtil.log(sql);
		ChannelDataDao dao = new ChannelDataDao();
		ArrayList<Gamedata1> dataList = new ArrayList<Gamedata1>();
		
		
		ArrayList<ArrayList<Object>>list1 = dao.getObjectList(sql);
		//System.out.println("结果长度："+list1.size());
		
		if (list1.size()>0) {
			for(int i = 0;i<list1.size();i++){
				String gameId,channelId,date;
				int activiteNum=0;int day1Num=0; int day7Num= 0;int day30Num=0;
				int daypayUserNum = 0;
				int registerNum=0;int loginNum=0;int newUserNum=0;int newPayUserNum=0;
				float day1_rate=0;float day7_rate=0;float day30_rate=0;
				String day1Rate="";String day7Rate="";String day30Rate="";
				float a_r_rate=0;float newUserPay_rate=0; float pay_rate=0;float dayallPay = 0;
				float arppu = 0;float arpu = 0;
				String payRate="0";String aRpu="0";String arRate="0.00%";String newUserPayRate="0.00%";
				
				ArrayList<Object> list2 = list1.get(i);
				//System.out.println(list2.toString());
				Gamedata1 data = new Gamedata1();
				date = list2.get(0).toString();
				gameId = list2.get(2).toString();
				channelId = list2.get(4).toString();
				dayallPay = Float.valueOf(list2.get(14).toString());
				activiteNum = (Integer)list2.get(5);
				registerNum = (Integer)list2.get(6);
				loginNum = (Integer)list2.get(7);
				day1Num = (Integer)list2.get(8);
				day7Num = (Integer)list2.get(9);
				day30Num = (Integer)list2.get(10);
				newUserNum = (Integer)list2.get(12);
				newPayUserNum = (Integer)list2.get(13);
				
				data.setDate(date);
				data.setGameName(list2.get(1).toString());
				data.setChannelName(list2.get(3).toString());
				data.setNewUserPay(formatPay(list2.get(11).toString()));
				data.setAllPay(formatPay(list2.get(14).toString()));
				data.setActivateNum(activiteNum+"");
				data.setRegisterNum(registerNum+"");
				data.setLoginNum(loginNum+"");
				/*data.setDay1(day1Num+"");
				data.setDay7(day7Num+"");
				data.setDay30(day30Num+"");*/
				
				
				if (activiteNum>0) {
					a_r_rate = registerNum/(float)activiteNum;
					arRate = String.format("%.2f", a_r_rate*100)+"%";
				}
				if (newUserNum>0) {
					newUserPay_rate = newPayUserNum/(float)newUserNum;
					newUserPayRate = String.format("%.2f", newUserPay_rate*100)+"%";
				}
				if (dayallPay>0) {
					String sql1 = "SELECT count(distinct username) from pay where game_id='%s' and channel_no='%s' and date='%s' and state=1";
					sql1 = String.format(sql1, gameId,channelId,date);
					PayDao paydao = new PayDao();
					ArrayList<Object> list3 = new ArrayList<Object>();
					list3 = paydao.getObject(sql1);
					if(list3.size()>0){
						daypayUserNum = Integer.valueOf(list3.get(0).toString());
					}
					System.out.println("当天支付人数："+daypayUserNum);
					arppu = dayallPay/(float)daypayUserNum;
				}
				
				if (loginNum>0) {
					arpu = dayallPay/(float)loginNum;
					pay_rate = daypayUserNum/(float)loginNum;
					aRpu = String.format("%.2f", arpu);
					payRate = String.format("%.2f", pay_rate*100)+"%";
				}
				if (registerNum>0) {
					day1_rate = day1Num/(float)registerNum;
					day1Rate = String.format("%.2f", day1_rate*100)+"%";
					day7_rate = day7Num/(float)registerNum;
					day7Rate = String.format("%.2f", day7_rate*100)+"%";
					day30_rate = day30Num/(float)registerNum;
					day30Rate = String.format("%.2f", day30_rate*100)+"%";
				}
				data.setDay1(day1Rate);
				data.setDay7(day7Rate);
				data.setDay30(day30Rate);
				data.setaRrate(arRate);
				data.setNewUserPayRate(newUserPayRate);
				data.setPayRate(payRate);
				data.setaRPPU(arppu+"");
				data.setaRPU(aRpu);
				//System.out.println(data.toString());
				dataList.add(data);
				
			}
		}
		
		
		return dataList;
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
