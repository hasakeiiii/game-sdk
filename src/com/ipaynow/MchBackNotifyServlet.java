package com.ipaynow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.WxPay;
import util.ConstValue;
import util.DebuUtil;
import util.HandleChargePay;

import com.ipaynow.utils.BackEndRequest;
import com.ipaynow.utils.FormDateReportConvertor;
import com.ipaynow.utils.MD5Facade;
import com.ipaynow.utils.NowPayUtils;

import dao.WxPayDao;

/**
 * 服务器端异步通知商户Servlet
 * @author christ
 *
 */
public class MchBackNotifyServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2358940163943334363L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		DebuUtil.log("微信支付后台处理...");
        //拿到自己应用的md5Key
        
		
        StringBuilder reportBuilder = new StringBuilder();
        try(BufferedReader reader = req.getReader()){
        	String tempStr = "";
    		while((tempStr = reader.readLine()) != null){
    			reportBuilder.append(tempStr);
    		}	
        }catch (Exception e) {
        	
		}
        
			
		String reportContent = reportBuilder.toString();		
		Map<String,String> dataMap = FormDateReportConvertor.parseFormDataPatternReportWithDecode(reportContent, "UTF-8", "UTF-8");
		//去除签名类型和签名值
        dataMap.remove("signType");
        String signature = dataMap.remove("signature");
        
        
		InputStream propertiesInput = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(propertiesInput);
        
        
        String md5Key = (String) properties.get("md5Key");
		String md5Key_app = (String) properties.get("md5Key_app");
		String md5Key_sa = (String) properties.get("md5Key_sa");
		
		String appId = (String) properties.get("appId");
		String appId_app = (String) properties.get("appId_app");
		
		  //验证签名
        boolean isValidSignature ;
		if(appId.equals(dataMap.get("appId"))){
			isValidSignature = MD5Facade.validateFormDataParamMD5(dataMap,md5Key,signature);
		}else if(appId_app.equals(dataMap.get("appId"))){
			isValidSignature = MD5Facade.validateFormDataParamMD5(dataMap,md5Key_app,signature);
		}else{
			isValidSignature = MD5Facade.validateFormDataParamMD5(dataMap,md5Key_sa,signature);
		}
		
      
		//验证签名
		//boolean isValidSignature = NowPayUtils.verifySignature(req, md5Key);
        //System.out.println("验签结果："+isValidSignature);
        
        //获取请求体
        //BackEndRequest backEndRequest = NowPayUtils.buildBackEndRequest(req);
        // System.out.println(backEndRequest.getAppId());
        BackEndRequest backEndRequest = new BackEndRequest();
		// 获取通知数据需要从body中流式读取
		/*BufferedReader reader = req.getReader();
		StringBuilder reportBuilder = new StringBuilder();
		String tempStr = "";
		while ((tempStr = reader.readLine()) != null) {
			System.out.println("tempStr" + tempStr);
			reportBuilder.append(tempStr);
		}
		String reportContent = reportBuilder.toString();
		System.out.println("reportContent-->" + reportContent);
		Map<String, String> dataMap = FormDateReportConvertor
				.parseFormDataPatternReportWithDecode(reportContent, "UTF-8",
						"UTF-8");*/
        System.out.println(dataMap);
		
		try {
			backEndRequest.setFuncode(dataMap.get("funcode"));
			backEndRequest.setAppId(dataMap.get("appId"));
			backEndRequest.setMhtOrderNo(dataMap.get("mhtOrderNo"));
			backEndRequest.setMhtOrderName(dataMap.get("mhtOrderName"));
			backEndRequest.setMhtOrderType(dataMap.get("mhtOrderType"));
			backEndRequest.setMhtCurrencyType(dataMap.get("mhtCurrencyType"));
			backEndRequest.setMhtOrderAmt(dataMap.get("mhtOrderAmt"));
			backEndRequest.setMhtOrderTimeOut(dataMap.get("mhtOrderTimeOut"));
			backEndRequest.setMhtOrderStartTime(dataMap.get("mhtOrderStartTime"));
			backEndRequest.setMhtCharset(dataMap.get("mhtCharset"));
			backEndRequest.setDeviceType(dataMap.get("deviceType"));
			backEndRequest.setPayChannelType(dataMap.get("payChannelType"));
			backEndRequest.setNowPayAccNo(dataMap.get("nowPayOrderNo"));
			backEndRequest.setTradeStatus(dataMap.get("tradeStatus"));
			backEndRequest.setMhtReserved(dataMap.get("mhtReserved"));
			backEndRequest.setSignType(dataMap.get("signType"));
			backEndRequest.setSignature(dataMap.get("signature"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
        
        String out_trade_no = backEndRequest.getMhtOrderNo();
    	//交易号
    	String trade_no =  backEndRequest.getNowPayAccNo();
        DebuUtil.log(backEndRequest.getTradeStatus());
        System.out.println(isValidSignature);
      
    	
		boolean ret = false;
        //一定要返回
        if(isValidSignature){
        	if("A001".equals(backEndRequest.getTradeStatus())){
        		WxPay obj = new WxPay();
        		ret = true;
        		DebuUtil.log("微信交易完成");
        		obj.setPayNo(out_trade_no);
        		obj.setWxPayNo(trade_no); 
        		obj.setRet(ConstValue.OK);	
        		
        		if (ret == true) {
        			WxPayDao dao = new WxPayDao();
        			dao.wxpay(obj);
        			
        			HandleChargePay.handleChargePay(out_trade_no, trade_no);
        			
        		}
        		resp.getOutputStream().write("success=Y".getBytes());
        	}
        }
        else
        	resp.getOutputStream().write("success=N".getBytes());
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}