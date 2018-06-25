package viewmodel;

import java.util.ArrayList;

import model.ChannelDataReq;
import dao.PayTypeCountDao;

public class ChannelDataReqSta {
	public int mmpaycount = 0;
	public int tcpaycount = 0;
	public int unipaycount = 0;
	public int alipaycount = 0;
	public int yeepaycount = 0;
	public int tenpaycount = 0;
	public int unionpaycount = 0;
	public int openpaycount = 0;
	public int wopluspaycount = 0;
	public int mzpaycount = 0;
	public int olpaycount = 0;
	public int wxpaycount = 0;
	public int jdpaycount = 0;
	public int ldpaycount = 0;
	public int ananpaycount = 0;
	public int webpaycount = 0;
	public int boxpaycount = 0;
	
	public int settleboxpaycount = 0;
	public int settlejdpaycount = 0;
	public int settleldpaycount = 0;
	public int settleananpaycount = 0;
	public int settlewebpaycount = 0;
	public int settlemmpaycount = 0;
	public int settleopenpaycount = 0;

	public int newmmpaycount = 0;
	public int newtcpaycount = 0;
	public int newunipaycount = 0;
	public int newalipaycount = 0;
	public int newyeepaycount = 0;
	public int newtenpaycount = 0;
	public int newunionpaycount = 0;
	public int newopenpaycount = 0;
	public int newwopluspaycount = 0;
	public int newmzpaycount = 0;
	public int newolpaycount = 0;
	public int newwxpaycount = 0;
	public int newldpaycount = 0;
	public int newjdpaycount = 0;
	public int newananpaycount = 0;
	public int newwebpaycount = 0;
	public int newboxpaycount = 0;

	public int actcount = 0;
	public int regcount = 0;
	public int logincount = 0;
	public int allactivityRegNum = 0;
	public int allrealregisterNum = 0;
	public int monthdayAllPayAcount = 0;
	public int monthdayAllAcount = 0;

	public int allday1Num = 0;
	public int allday7Num = 0;
	public int allday30Num = 0;
		
		 
	public    ChannelDataReqSta(ArrayList<ChannelDataReq> list )
	{
		/*
		 * 这个是针对计费方式结算的总计
		 * 先获得结算比例
		 * begin
		 * */
		PayTypeCountDao payTypeCountDao = new PayTypeCountDao();
		int mmSettle = payTypeCountDao.getPaySettleCount("mmpay");
		int webSettle = payTypeCountDao.getPaySettleCount("jdpay");
		int boxSettle = payTypeCountDao.getPaySettleCount("fanhe");
		int openSettle = payTypeCountDao.getPaySettleCount("openpay");
		int ldSettle = payTypeCountDao.getPaySettleCount("leDong");
		int jdSettle = payTypeCountDao.getPaySettleCount("mmjd");
		int ananSettle = payTypeCountDao.getPaySettleCount("ananpay");
		/*
		 * end
		 * */
		
		for(int i=0;i<list.size();i++)
		{			
			ChannelDataReq gamedata=(ChannelDataReq)list.get(i);
			mmpaycount += gamedata.mmPay;
			tcpaycount += gamedata.tcPay;///////////////////////////
			unipaycount += gamedata.uniPay;////////////////////////
			alipaycount += gamedata.aliPay;
			yeepaycount += gamedata.yeePay;
			tenpaycount += gamedata.tenPay;
			unionpaycount += gamedata.unionPay;
		    openpaycount += gamedata.openPay;
			wopluspaycount += gamedata.woplusPay;
			wxpaycount += gamedata.wxPay;
			ldpaycount += gamedata.ldPay;
			jdpaycount += gamedata.jdPay;
			ananpaycount += gamedata.ananPay;
			webpaycount += gamedata.webPay;
			boxpaycount += gamedata.boxPay;

			mzpaycount += gamedata.mzpay;
		    olpaycount += gamedata.onlypay;
			
			newmmpaycount += gamedata.newmmPay;
			newtcpaycount += gamedata.newtcPay;////////
			newunipaycount += gamedata.newuniPay;////////
			newalipaycount += gamedata.newaliPay;
			newyeepaycount += gamedata.newyeePay;
			newtenpaycount += gamedata.newtenPay;
			newunionpaycount += gamedata.newunionPay;
		    newopenpaycount += gamedata.newopenPay;
			newwopluspaycount += gamedata.newwoplusPay;
			newwxpaycount += gamedata.newwxPay;
			newldpaycount += gamedata.newldPay;
			newjdpaycount += gamedata.newjdPay;
			newwebpaycount += gamedata.newwebPay;
			newboxpaycount += gamedata.newboxPay;
			newananpaycount += gamedata.newananPay;

			newolpaycount += gamedata.newonlypay;
		    newmzpaycount += gamedata.newmzpay;
			
			monthdayAllPayAcount += gamedata.dayAllPayAcount;
		    monthdayAllAcount += gamedata.dayAllAcount;
		    

			actcount += gamedata.activityNum;
			regcount += gamedata.registerNum;
			allrealregisterNum += gamedata.realregisterNum;
			
			logincount += gamedata.loginNum;
			allactivityRegNum += gamedata.activityRegNum;
			allday1Num += gamedata.day1Num;
			allday7Num += gamedata.day7Num;
			allday30Num += gamedata.day30Num;
			
			settleboxpaycount += gamedata.boxPay*boxSettle/100;
			settlemmpaycount += gamedata.mmPay*mmSettle/100;
			settlejdpaycount += gamedata.jdPay*jdSettle/100;
			settleldpaycount += gamedata.ldPay*ldSettle/100;
			settlewebpaycount += gamedata.webPay*webSettle/100;
			settleopenpaycount += gamedata.openPay*openSettle/100;
			
		}
		
	}
	
	public static int getDayNewPay(ChannelDataReq gamedata)
	{
		int ret = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay
				+gamedata.newaliPay+gamedata.newyeePay+gamedata.newtenPay
				+gamedata.newunionPay+gamedata.newmzpay+gamedata.newonlypay
				+gamedata.newopenPay+gamedata.newwoplusPay;
		
		return ret;
	}
	
	public static int getDayThirdPay(ChannelDataReq gamedata)
	{
		int ret = gamedata.yeePay+gamedata.aliPay+gamedata.tenPay+gamedata.unionPay+gamedata.mzpay+gamedata.onlypay + gamedata.wxPay;
		
		return ret;
	}
	
	public static int getDayThirdNewPay(ChannelDataReq gamedata)
	{
		int ret = gamedata.newyeePay+gamedata.newaliPay+gamedata.newtenPay+gamedata.newunionPay+gamedata.newmzpay+gamedata.newonlypay + gamedata.newwxPay;
		
		return ret;
	}
	
	
	public static int getDayTelPay(ChannelDataReq gamedata)
	{
		int ret =gamedata.mmPay+gamedata.tcPay+gamedata.uniPay+gamedata.openPay+gamedata.woplusPay+gamedata.webPay;
		
		return ret;
	}
	public static int getDayTelNewPay(ChannelDataReq gamedata)
	{
		int ret = gamedata.newmmPay+gamedata.newtcPay+gamedata.newuniPay+gamedata.newopenPay+gamedata.newwoplusPay+gamedata.newwebPay;
		
		return ret;
	}
	
}
