package util;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import model.App;
import model.Channel;
import dao.AppDao;
import dao.AppPayDataDao;
import dao.ChannelDao;
import dao.PayDao;

/**
 * 任务调度bean
 * @author wuchengf
 *
 */
public class MyTask {
	public void test(){
		
		System.out.println("test----  " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}
	
	private String setMailContext(){
		PayDao payDao = new PayDao();
		AppDao appDao = new AppDao();
		ChannelDao channelDao = new ChannelDao();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		List<ArrayList> rawList = payDao.getRecordByTask();
		System.out.println(rawList.size());
		StringBuilder sb = new StringBuilder("<table style='width:100%;border:1px;font-size:12px;border-width:0px 1px 1px 0px;border-style: none solid solid none;border-collapse:collapse;text-align:center;color:#192E32'>");
		sb.append("<tr style='background-color: rgb(187, 221, 229); color: #192E32; font-weight: bold; height: 25px;'>");
		sb.append("<td>日期</td><td>时间</td><td>游戏ID</td><td>渠道名称</td><td>渠道包号</td><td>金额</td>");
		//sb.append("<td>指令转短信成功率</td><td>回调成功数</td><td>短信成功率</td><td>话费支付</td></tr>");
		
		for(int i = 0 ; i < rawList.size();i++){
			ArrayList al = rawList.get(i);
			
			sb.append("<tr ");
			if(i % 2 != 0 )sb.append("style='background-color:#E2EBED'");
			sb.append(">");
			String date = ((java.sql.Date)al.get(0)).toString();
			BigDecimal money = (BigDecimal)al.get(1);
			int pay = money.intValue() /100;
			String gameId = (String)al.get(2);
			App app = appDao.getAppRecord(gameId);
			if(app != null){
				gameId = app.name;
			}
			
			String channelNo = (String)al.get(3);
			Channel channel = channelDao.getRecord(channelNo);
			if(channel != null){
				channelNo = channel.name;
			}
			String packetId = (String)al.get(4);
/*			int reqNum = (Integer)al.get(5);
			int reqOkNum = (Integer)al.get(6);
			int msmNum = (Integer)al.get(7);
			int feeNum = (Integer)al.get(8);
			int mmPay = (Integer)al.get(9) / 100;*/
			
			
/*			switch(company){
				case "mzhy" : company = "拇指互娱"; break;
				case "zty" : company = "中泰源"; break;
				case "mzyw" : company = "拇指游玩"; break;
			}*/
			
			sb.append("<td>" + date + "</td>");
			sb.append("<td>" + format.format(new Date()) + "</td>");
//			sb.append("<td>" + company + "</td>");
			sb.append("<td>" + gameId + "</td>");
			sb.append("<td>" + channelNo + "</td>");
//			sb.append("<td>" + channelNo + "</td>");
			sb.append("<td>" + packetId + "</td>");
/*			sb.append("<td>" + reqOkNum + "</td>");
			sb.append("<td>" + getPorprotion(reqOkNum ,reqNum) + "%</td>");
			sb.append("<td>" + msmNum + "</td>");
			sb.append("<td>" + getPorprotion(msmNum , reqOkNum) + "%</td>");
			sb.append("<td>" + feeNum + "</td>");
			sb.append("<td>" + getPorprotion(feeNum, msmNum) + "%</td>");*/
			sb.append("<td>" + pay + "元</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	private String setTwoMailContext(){
		AppPayDataDao dao = new AppPayDataDao();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		List<ArrayList> rawList = dao.getTaskByAppPay();
		System.out.println(rawList.size());
		StringBuilder sb = new StringBuilder("<table style='width:100%;border:1px;font-size:12px;border-width:0px 1px 1px 0px;border-style: none solid solid none;border-collapse:collapse;text-align:center;color:#192E32'>");
		sb.append("<tr style='background-color: rgb(187, 221, 229); color: #192E32; font-weight: bold; height: 25px;'>");
		sb.append("<td>日期</td><td>时间</td><td>公司</td><td>计费游戏</td><td>金额</td>");
		
		for(int i = 0 ; i < rawList.size();i++){
			ArrayList al = rawList.get(i);
			
			sb.append("<tr ");
			if(i % 2 != 0 )sb.append("style='background-color:#E2EBED'");
			sb.append(">");
			String date = (al.get(0)).toString();
			String gameName = (String)al.get(1);
			int money = ((BigDecimal) al.get(2)).intValue();
			int pay = money /100;
			String company = al.get(3).toString();
			switch(company){
				case "qt" : company = "其他"; break;
				case "zty" : company = "中泰源"; break;
				case "mzyw" : company = "拇指游玩"; break;
				case "mzhy" : company = "拇指互娱"; break;
				case "jy" : company = "竟游"; break;
				case "cq" : company = "创趣"; break;
				case "gy" : company = "光游"; break;
				case "hlx" : company = "宏乐欣"; break;
				case "qszd" : company = "奇硕智达"; break;
				case "mykj" : company = "妙悟科技"; break;
				case "lst" : company = "联思泰"; break;
				case "ktx" : company = "科特迅"; break;
				case "yssj" : company = "原宿设计"; break;
			}
			
			sb.append("<td>" + date + "</td>");
			sb.append("<td>" + format.format(new Date()) + "</td>");
			sb.append("<td>" + company + "</td>");
			sb.append("<td>" + gameName + "</td>");
			sb.append("<td>" + pay + "元</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	
	public void sendMail() throws AddressException, IOException, MessagingException{
//		new MailSender().send_email(setMailContext());
//		new MailSender().send_email(setTwoMailContext());
	}
	
	
	public static void main(String[] args) {
		try {
			new MyTask().sendMail();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private int getPorprotion(int a,int b){
		if(b == 0)return 0;
		return a * 100 / b;
	}
}
