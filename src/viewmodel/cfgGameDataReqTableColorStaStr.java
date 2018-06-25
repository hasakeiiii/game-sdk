package viewmodel;

public class cfgGameDataReqTableColorStaStr {

	public String AllAccountPay ="0.0";
	public String AllpayAccountPay = "0.0";
	public String AllpayRatio = "0.0";
	public String allactRegistrStr;
	public String alldate1R;
	public String alldate7R;
	public String alldate30R;
	public String telpaycountstr;
	public String newalltelpaystr;
	public String thpaycountstr;
	public String newallthpaystr;
	
	public cfgGameDataReqTableColorStaStr(ChannelDataReqSta channelDataReqSta)
	{
		float value1=0;
		if(channelDataReqSta.allactivityRegNum > 0)
		{
			value1 = channelDataReqSta.allactivityRegNum;
			value1 = value1/channelDataReqSta.actcount;
		}
		allactRegistrStr= String.format("%.2f%s",value1*100,"%");
	
		if(channelDataReqSta.monthdayAllAcount > 0)
		{
			value1 = channelDataReqSta.allday1Num;
			value1 = value1/channelDataReqSta.monthdayAllAcount;
		}
		 alldate1R = String.format("%.2f%s",value1*100,"%");
	
	
		if(channelDataReqSta.monthdayAllAcount > 0)
		{
			value1 = channelDataReqSta.allday7Num;
			value1 = value1/channelDataReqSta.monthdayAllAcount;
		}
		alldate7R = String.format("%.2f%s",value1*100,"%");
	
		if(channelDataReqSta.monthdayAllAcount > 0)
		{
			value1 = channelDataReqSta.allday30Num;
			value1 = value1/channelDataReqSta.monthdayAllAcount;
		}
		alldate30R = String.format("%.2f%s",value1*100,"%");
	
		int alltelpay = channelDataReqSta.mmpaycount+channelDataReqSta.tcpaycount+channelDataReqSta.unipaycount+channelDataReqSta.openpaycount+channelDataReqSta.wopluspaycount;
		int newalltelpay = channelDataReqSta.newmmpaycount+channelDataReqSta.newtcpaycount+channelDataReqSta.newunipaycount+channelDataReqSta.newopenpaycount+channelDataReqSta.newwopluspaycount;
		int allthpay = channelDataReqSta.yeepaycount+channelDataReqSta.alipaycount+channelDataReqSta.tenpaycount+channelDataReqSta.unionpaycount+channelDataReqSta.mzpaycount+channelDataReqSta.olpaycount;
		int newallthpay = channelDataReqSta.newyeepaycount+channelDataReqSta.newalipaycount+channelDataReqSta.newtenpaycount+channelDataReqSta.newunionpaycount+channelDataReqSta.newmzpaycount+channelDataReqSta.newolpaycount;
	
		/*String mmpaycountstr = String.format("%.2f(%.2f)", mmpaycount/100.0,newmmpaycount/100.0);
		String yeepaycountstr = String.format("%.2f(%.2f)", yeepaycount/100.0,newyeepaycount/100.0);
		String alipaycountstr = String.format("%.2f(%.2f)", alipaycount/100.0,newalipaycount/100.0);
		String tenpaycountstr = String.format("%.2f(%.2f)", tenpaycount/100.0,newtenpaycount/100.0);
		String unionpaycountstr = String.format("%.2f(%.2f)", unionpaycount/100.0,newunionpaycount/100.0);*/
	
		telpaycountstr = String.format("%.2f", alltelpay/100.0);
		newalltelpaystr = String.format("%.2f",newalltelpay/100.0);
		thpaycountstr = String.format("%.2f", allthpay/100.0);
		newallthpaystr = String.format("%.2f",newallthpay/100.0);
		int allnewpay = newalltelpay+newallthpay;
	
		if(channelDataReqSta.monthdayAllPayAcount > 0)
		{
			AllpayRatio=String.format("%.2f%s",channelDataReqSta.monthdayAllPayAcount*100.0/channelDataReqSta.monthdayAllAcount,"%");
		}
	
	
		if(channelDataReqSta.monthdayAllPayAcount > 0)
		{
			AllpayAccountPay = String.format("%.2f",allnewpay*1.0/100/channelDataReqSta.monthdayAllPayAcount);
		}
	
	
		if(channelDataReqSta.monthdayAllAcount > 0)
		{
			 AllAccountPay = String.format("%.2f",allnewpay*1.0/100/channelDataReqSta.monthdayAllAcount);
		}
	}
}
