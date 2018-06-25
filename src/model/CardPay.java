package model;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;
import util.ConstValue;
import util.DateUtil;
import util.DebuUtil;

import com.yeepay.nonbankcard.NonBankcardPaymentResult;
import com.yeepay.nonbankcard.NonBankcardService;

public class CardPay implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -310671034013467265L;

	public Integer id;
	public String pay_no;
	public String no;
	public String pass;
	public int amount;
	public int ret;
	public int notice;
	
	public String oder_no;
	public String username;
	public String goods_name;
	public String goods_details;
	public String url;
	public String pay_type;
	
	private String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss");
		Date date = new Date();
		String key = format.format(date);

		java.util.Random r = new java.util.Random();
		key += r.nextInt();
		key = key.substring(0, 15);
		//key = "010217153010087";//lsl
		//Log.d(TAG, "outTradeNo: " + key);
		return key;
	}
	public void test()
	{
		CardPay cardPay = new CardPay();
		cardPay.goods_details = "游戏币";
		cardPay.goods_name = "游戏币";;
		cardPay.oder_no = getOutTradeNo();
		cardPay.username = "4654665";
		cardPay.no = "13967011567139992";
		cardPay.pass = "433717723751115980";
		cardPay.amount = 30;
		cardPay.pay_type = "china_mobile";
		cardPay.url = ConstValue.ServerUrl+"/sdk/yeepay/callback.jsp";
	
		DebuUtil.log("url="+cardPay.url);
		
		NonBankcardPaymentResult rs = cardPay.pay();
	}
	public CardPay(JSONObject json)
	{
		no=json.getString("card_no");
		pass=json.getString("card_pass");
		amount=json.getInt("total_fee");
		pay_type = json.getString("payway");
		ret = 0;
		notice = 0;
	}
	
	public CardPay()
    {
		
	}
	
	
	public NonBankcardPaymentResult pay()
	{
		NonBankcardPaymentResult rs = null;
		//request.setCharacterEncoding("GBK");
		// 商户订单号
		String p2_Order = oder_no;

		// 订单金额
		String p3_Amt = "";
		if(no.equals("1503103307236159"))
		{
			p3_Amt= String.valueOf(0.1);//amount//lsl,测试先定0。1元
		}
		else
		{
			p3_Amt= String.valueOf(amount);
		}
		// 是否较验订单金额
		String p4_verifyAmt = "true";//"false";//lsl,如果进行订单金额与支付卡金额的确认，则为true//

		// 产品名称
		String p5_Pid ="";

		// 产品类型
		String p6_Pcat="";
		
		// 产品描述
		String p7_Pdesc = "";
				
		try {
			 p6_Pcat = new String("游戏币".getBytes("iso-8859-1"),"gbk");
			 p5_Pid = new String(goods_name.getBytes("iso-8859-1"),"gbk");
			 p7_Pdesc = new String(goods_details.getBytes("iso-8859-1"),"gbk");
		} catch (UnsupportedEncodingException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		

		// 交易成功通知地址
		String p8_Url = url;

		// 扩展信息
		String pa_MP = "";
		try {
			pa_MP = new String("游戏币".getBytes("iso-8859-1"),"gbk");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 卡面额组
		String pa7_cardAmt = String.valueOf(amount);

		// 卡号组
		String pa8_cardNo = no;

		// 卡密组
		String pa9_cardPwd = pass;

		// 支付通道编码
		
		String pd_FrpId = "SZX";//formatString(request.getParameter("pd_FrpId"));
		if(pay_type.equals("china_mobile"))
		{
			pd_FrpId = "SZX";
		}
		else if(pay_type.equals("china_telecom"))
		{
			pd_FrpId = "TELECOM";
		}
		else if(pay_type.equals("china_unicom"))
		{
			pd_FrpId = "UNICOM";
		}
		else if(pay_type.equals("jcard"))
		{
			pd_FrpId = "JUNNET";
		}
		else if(pay_type.equals("zycard"))
		{
			pd_FrpId = "ZONGYOU";
		}
		else if(pay_type.equals("tscard"))
		{
			pd_FrpId = "THIRTYTWOCARD";
		}
		
        //if(cardPay.pay_type.("china_mobile")
		// 应答机制
		String pr_NeedResponse = String.valueOf(1);

		// 用户唯一标识
		String pz_userId = username;//username

		// 用户的注册时间
		String pz1_userRegTime  = DateUtil.getDateTime();//formatString(request.getParameter("pz1_userRegTime"));
		try {
			 rs = NonBankcardService.pay(p2_Order,
																 p3_Amt,
																 p4_verifyAmt,
																 p5_Pid,
																 p6_Pcat,
																 p7_Pdesc,
																 p8_Url,
																 pa_MP,
																 pa7_cardAmt,
																 pa8_cardNo,
																 pa9_cardPwd,
																 pd_FrpId,
																 pr_NeedResponse,
																 pz_userId,
																 pz1_userRegTime);
			//out.println("提交返回参数列表<br/>");
			//out.println("业务类型[r0_Cmd：" + rs.getR0_Cmd() + "]<br/>");
			//out.println("订单的提交状态[r1_Code：" + rs.getR1_Code() + "]<br/>");
			//out.println("交易订单号[r6_Order：" + rs.getR6_Order() + "] <br/>");
			//out.println("订单的提交状态[rq_ReturnMsg：" + rs.getRq_ReturnMsg() + "]<br/>");
			/* 	该方法是根据《易宝支付非银行卡支付专业版接口文档 v3.0》生成一个模拟的交易结果通知串.
			 * 	商户使用模拟的交易结果通知串可以直接测试自己的交易结果接收程序(callback)的正确性.
			 * 	实际的交易结果通知机制以《易宝支付非银行卡支付专业版接口文档 v3.0》为准，该方法只是
			 * 	模拟了交易结果通知串.正式上线时请不要调用此方法。 
			 */
			// out.println(NonBankcardService.generationTestCallback(p2_Order,p3_Amt,p4_verifyAmt,p5_Pid,p6_Pcat,p7_Pdesc,p8_Url,pa_MP,pa7_cardAmt,pa8_cardNo,pa9_cardPwd,pd_FrpId,pr_NeedResponse,pz_userId,pz1_userRegTime));
		} catch(Exception e) {
			DebuUtil.log(e.getMessage());
		}
		return rs;
	}
	
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}	

	public void setNo(String no){
		this.no=no;
	}
	public String getNo( ){
		return this.no;
	}

	public void setPayNo(String pay_no){
		this.pay_no=pay_no;
	}
	public String getPayNo(){
		return this.pay_no;
	}	
	
	public void setPass(String pass){
		this.pass=pass;
	}
	public String getPass( ){
		return this.pass;
	}	
	
	public void setAmount(Integer amount){
		this.amount=amount;
	}
	public Integer getAmount(){
		return this.amount;
	}	
	
	public void setNotice(Integer notice){
		this.notice=notice;
	}
	public Integer getNotice(){
		return this.notice;
	}	
	public void setRet(Integer ret){
		this.ret=ret;
	}
	public Integer getRet(){
		return this.ret;
	}	
	public void setPayType(String pay_type){
		this.pay_type=pay_type;
	}	
	public String getPayType(){
		return this.pay_type;
	}	
}
