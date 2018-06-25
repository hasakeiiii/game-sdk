package viewmodel;



import util.DateUtil;
import model.ChannelData;
import model.ChannelDataReq;

public class cfgGameDataReqTableColorStr {
    public String datestr;

    public String actStr;
    public String registerStr;
    public String realregisterNumStr;
    public String loginStr;
    public String actRegistrStr;
    public String date1R;
    public String date7R;
    public String date30R;
    public String telpaystr;
    public String newtelpaystr;
    public String thpaystr;
    public String newthpaystr;
    public String payRatio;
    public String payAccountPay;
    public String AccountPay;
    
    public cfgGameDataReqTableColorStr(ChannelDataReq gamedata,String game_no)
    {
    	String curdate = DateUtil.getDate();
    	long datedif = 0;
    	String newDate = "2014-03-01";
    	
    	//算总数
    	int daypay = ChannelDataReqSta.getDayNewPay(gamedata);
    	datedif = DateUtil.getDayDiff(gamedata.date, curdate);
    	
    	datestr = gamedata.date;
    	actStr = String.format("%d",gamedata.activityNum);
    	registerStr = String.format("%d",gamedata.registerNum);
    	loginStr = String.format("%d",gamedata.loginNum);
    	realregisterNumStr = String.format("%d",gamedata.realregisterNum);
    	
    	float value = 0;
    	
    	if(gamedata.activityNum > 0)
    	{
    		value = gamedata.activityRegNum;
    		value = value/gamedata.activityNum;
    	}
    	
    	actRegistrStr= String.format("%.2f%s",value*100,"%");
    	
    	
    	value = 0;
    	int activateReg = 0;
    	int datevalue = datestr.compareTo(newDate);
    	if(datevalue >= 0)
    	 {
    	       activateReg = gamedata.dayAllAcount;
    	 }
    	 else
    	 {
    		   activateReg = gamedata.registerNum;
          }
    		
    	if(gamedata.activityNum > 0)
    	{
    	    value = activateReg;
    		value = gamedata.day1Num/value;
    	}
    	date1R= "-";
    	if(datedif >= 1)
    	{	   
    	   date1R= String.format("%.2f%s(%d)",value*100,"%",gamedata.day1Num);	
    	}
    	else
    	{
    		date1R = "-";
    	}
    	value = 0;
    	if(gamedata.activityNum > 0)
    	{
    		//value = gamedata.registerNum;
    		 value = activateReg;
    		value = gamedata.day7Num/value;
    	}
    	date7R = "-"; 
    	if(datedif >= 7)
    	{
    	   date7R = String.format("%.2f%s(%d)",value*100,"%",gamedata.day7Num);
    	}
    	
    	value = 0;
    	if(gamedata.activityNum > 0)
    	{
    		//value = gamedata.registerNum;
    		 value = activateReg;
    		value = gamedata.day30Num/value;
    	}
    	date30R = "-"; 
    	if(datedif >= 30)
    	{
    	   date30R = String.format("%.2f%s(%d)",value*100,"%",gamedata.day30Num);
    	}
    	

    	int telpay =ChannelDataReqSta.getDayTelPay(gamedata);
    	int newtelpay = ChannelDataReqSta.getDayTelNewPay(gamedata);
    	int thpay = ChannelDataReqSta.getDayThirdPay(gamedata);
    	int newthpay = ChannelDataReqSta.getDayThirdNewPay(gamedata);
    	

    	telpaystr = String.format("%.2f", telpay/100.0);
    	newtelpaystr = String.format("%.2f", newtelpay/100.0);
    	//String telpaystr = String.format("%.2f(%.2f)",telpay/100.0,newtelpay/100.0);//
    	/*String yeepaystr = String.format("%.2f(%.2f)",gamedata.yeePay/100.0,gamedata.newyeePay/100.0);//gamedata.newyeePay
    	String alipaystr = String.format("%.2f(%.2f)",gamedata.aliPay/100.0,gamedata.newaliPay/100.0);//gamedata.newaliPay
    	String tenpaystr = String.format("%.2f(%.2f)",gamedata.tenPay/100.0,gamedata.newtenPay/100.0);//gamedata.newaliPay
    	String unionpaystr = String.format("%.2f(%.2f)",gamedata.unionPay/100.0,gamedata.newunionPay/100.0);//gamedata.newaliPay
    	*/
    	//String thpaystr = String.format("%.2f(%.2f)",thpay/100.0,newthpay/100.0);
    	thpaystr = String.format("%.2f",thpay/100.0);
    	newthpaystr = String.format("%.2f", newthpay/100.0);
    	payRatio = "0.00%";
    	
    	
    	if(gamedata.dayAllAcount > 0)
    	{
    		payRatio = String.format("%.2f%s",gamedata.dayAllPayAcount*100.0/gamedata.dayAllAcount,"%");
    	}	
    	
    	
    	if(game_no.equals("1000") || game_no.equals("1001"))
    	{
    		 //int v = NumberUtil.getRandom(0, 35);
    		 //payRatio = String.format("%d%s", v ,"%");
    		 payRatio = String.format("%.2f%s",0.0,"%");
    	}
    	
    	payAccountPay = "0.0";
    	
    	
    	if(gamedata.dayAllPayAcount > 0)
    	{
    		//payRatio = String.format("%.2f%s",gamedata.dayAllPayAcount*100.0/gamedata.dayAllAcount,"%");		     
    		payAccountPay = String.format("%.2f",daypay*1.0/100/gamedata.dayAllPayAcount);
    	}
    			
    	
    	
    	if(game_no.equals("1000") )
    	{
    		payAccountPay = String.format("%.2f",2.0);
    	}
    	if(game_no.equals("1001"))
    	{
    		payAccountPay = String.format("%.2f",5.0);
    	}
    	
    	
    	AccountPay ="0.0";
    	
    	
    	if(gamedata.dayAllAcount > 0)
    	{
    		 //payRatio = String.format("%.2f%s",gamedata.dayAllPayAcount*100.0/gamedata.dayAllAcount,"%");		     
    		 AccountPay = String.format("%.2f",daypay*1.0/100/gamedata.dayAllAcount);
    	}	
    	
    	
    	if(game_no.equals("1000"))
    	{
    		AccountPay = String.format("%.2f",2.0);
    	}
    	if(game_no.equals("1001"))
    	{
    		AccountPay = String.format("%.2f",5.0);
    	}
    }
    
}
