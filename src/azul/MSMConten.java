package azul;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class MSMConten {

	
	public static void test()
	{
		String str = "";
		String strApkPublicKey="832f044b348774fdf7602f0cf7e1fd5665271960d2285370e2d363c6224dd9d52f1502bc038641f7315f1a14b9ffd02465b5fbcb847932ff9ed391f7ef09bea579e3cfef912c2d38e7a175bdee0c2f0dcd01d71b249e760571e0e81e4cf397feb6ee70c6276229eac506613828e3c34cad7bf01554b1d9e8bab0c3ae08661947";
				             //"832f044b348774fdf7602f0cf7e1fd5665271960d2285370e2d363c6224dd9d52f1502bc038641f7315f1a14b9ffd02465b5fbcb847932ff9ed391f7ef09bea579e3cfef912c2d38e7a175bdee0c2f0dcd01d71b249e760571e0e81e4cf397feb6ee70c6276229eac506613828e3c34cad7bf01554b1d9e8bab0c3ae08661947"
		//"ac9c536b357e1df7cb4f06a631c005068ba73dcb0eb65c689aff21893a8244f87d7c985c91244eb6ba4b9b31a881c475c3d422b30a408483a87936e4661a074847204e5a8699578c4fec6ba4688ebb9b16507f608dd4bdf4dac826ec560026297c2b60859ca912b0ec513290edaf0a5330c94709ec7dc55bbb4c7c08fe6c23b3bf052913adeb2cadc9a9739020fb0d23a508db23f5dcc963e50045ae5a1f3d5f02b3f77975f4a99c31fc3f341d1e09acd21640ec6f0aeacf2c6b1d1e632b09ab314294749485e18dbbed65a5e4c8a6a41bd284103054242ddbd6acc675162f7f2d0c7ea038445807d280a97518e5ff090c54855e72e399dc5dff8e262a3afff7";//lsl,用户数据
		
		String strProID = "3043167";// LongToString(parseLong("3043167"));//"1DOHE3Y";
		
		String strAppKey ="73EF7A0A650B2981564C472CBBE5147C";//"B56711EBA69EBA19";
		String md5 = "57475A84A8D9527AB50E8070066D1E9F";//"379094F6A6685668B874FE692B9FFC69";
		String md5dex = "T8UsMmgJeoVGpmip68Q136td6XM=";//"T8UsMmgJeoVGpmip68Q136td6XM="	
		
		// String str2 = ztya.en(MMPayUtil.parseLong(programId));//"1LQWK";//"1NJCHR";//程序ID
         
        //String str3 =  ztya.en(MMPayUtil.parseLong(channle));//"0";//渠道ID
         
		String strChannelID = "3003973308";//LongToString(parseLong());//"GL21Y7RLN";
		String strPayCode = "30000876344109";//LongToString(parseLong("30000876344109"));//"5XZ9TPT4";
		String strImei = "355458054168001";//"865903029745048";//LongToString(parseLong("865903029745048"));//"2M7XDP";//strTime_MakeOrderStr="a3#"+strProID+"#"+strChannelID+"#"+strImsi+"#"+strImei+"#"+strTime+"#"+strPayCode+"#"
		String strImsi = "460027099285388";//"460021180748016";//LongToString(parseLong("460021180748016"));//"AMU70FO4T";//a3#1DOHE3Y#GL21Y7RLN#AMU70FO4T#2M7XDP#1PC9W#5XZ9TPT4#6228C86F0A8CADBC#CDF5A10431CE7287#0FB02C3619687269#3254
		//a3#1DOHE3Y#GL21Y7RLN#AMU70FO4T#2M7XDP#1PC9W#5XZ9TPT4#6228C86F0A8CADBC#CDF5A10431CE7287#0FB02C3619687269#3254
		//a3#1T84F#1DOHKN0#4J2DJ5D7D8#3HZZ6BSOG1#2MKVW7#AMU7N6BL9#0588F72A4B2DF4E7#11E0BF6ED2A1EE25#D7B83F67B12AA7F9#2368

		String strUserdata = "3254";//6228C86F0A8CADBC
		
		String msm = MSMConten.getMSGContent(strApkPublicKey, strAppKey, md5, md5dex,
				strProID, strChannelID, strPayCode, strImei, strImsi,strUserdata);
	     System.out.println("sms="+msm);
	}
	
	
	public static String getTimeStr()
	{
	    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Object localObject = new Date();
	    try
	    {
	      Date localDate=new Date();
		try {
			localDate = localSimpleDateFormat.parse("2010-01-01 0:0:0");
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      localObject = localDate;
	      return LongToString((new Date().getTime() - ((Date)localObject).getTime()) / 1000L);
	    }
	    catch (ParseException localParseException)
	    {
	      while (true)
	        localParseException.printStackTrace();
	    }
	 }
		  
	  public static String getMSGContent(String strApkPublicKey,String strAppKey,String md5,String md5dex,
				  String strTime,String strProID,String strChannelID,String strPayCode,String strImei,String strImsi,String strUserdata)
	  {
			  //String strTime = getTime();
			  String strTime_MakeOrderStr="";
			  
			  String szAABuf = "a3#";
			  szAABuf += strTime + "#";
			  szAABuf += strProID + "#";
			  szAABuf += strChannelID + "#";
			  szAABuf += strPayCode + "#";
			  szAABuf += strImsi + "#";
			  szAABuf += strImei + "#";
			  szAABuf += strAppKey + "#";	 
			  szAABuf += md5 + "";
			  //System.out.println("szAABuf="+szAABuf);	  
			  String strTranID = util.Rsa.getMD5_16(szAABuf);//时间,	程序ID ,渠道ID,计费代码,IMEI,Imsi,AppKey
			  //System.out.println("strTranID="+strTranID);
			  
			  strTime_MakeOrderStr="a3#"+strProID+"#"+strChannelID+"#"+strImsi+"#"+strImei+"#"+strTime+"#"+strPayCode+"#";
			  strTime_MakeOrderStr += strTranID + "#";
			 
			  //System.out.println("strTranID="+strTranID);
			 // strTime_MakeOrderStr = szAABuf;
			  
			  String sz258Buf = strApkPublicKey;
			  sz258Buf += strTranID;
			  sz258Buf += strAppKey;
			  String pMd5_sz258Buf = util.Rsa.getMD5_16(sz258Buf);
			  //System.out.println("sz258Buf="+sz258Buf);
			  //System.out.println("pMd5_sz258Buf="+pMd5_sz258Buf);
			  strTime_MakeOrderStr += pMd5_sz258Buf + "#";
			  
			  //832f044b348774fdf7602f0cf7e1fd5665271960d2285370e2d363c6224dd9d52f1502bc038641f7315f1a14b9ffd02465b5fbcb847932ff9ed391f7ef09bea579e3cfef912c2d38e7a175bdee0c2f0dcd01d71b249e760571e0e81e4cf397feb6ee70c6276229eac506613828e3c34cad7bf01554b1d9e8bab0c3ae086619470588F72A4B2DF4E773EF7A0A650B2981564C472CBBE5147C
			  //832f044b348774fdf7602f0cf7e1fd5665271960d2285370e2d363c6224dd9d52f1502bc038641f7315f1a14b9ffd02465b5fbcb847932ff9ed391f7ef09bea579e3cfef912c2d38e7a175bdee0c2f0dcd01d71b249e760571e0e81e4cf397feb6ee70c6276229eac506613828e3c34cad7bf01554b1d9e8bab0c3ae080588F72A4B2DF4E773EF7A0A650B2981564C472CBBE5147C

			  String sz80Buf = md5dex +"#";
			  sz80Buf += strTranID + "#";
			  sz80Buf += strAppKey;
			  //System.out.println("sz80Buf="+sz80Buf);
			  String pMd5_sz80Buf = util.Rsa.getMD5_16(sz80Buf);
			  strTime_MakeOrderStr += pMd5_sz80Buf + "#";
			  //System.out.println("pMd5_sz80Buf="+pMd5_sz80Buf);
			  
			  strTime_MakeOrderStr += strUserdata;
			  
			  //System.out.println("strTime_MakeOrderStr="+strTime_MakeOrderStr);
			  //szAABuf += strTranID + "#";
			  //g_Md5MFFile = util.Rsa.getMD5(str);
			  return strTime_MakeOrderStr;
	   }
		  
	  public static String getMSGContent(String strApkPublicKey,String strAppKey,String md5,String md5dex,
			  String strProID,String strChannelID,String strPayCode,String strImei,String strImsi,String strUserdata)
	  {
	  
		  String strTime = getTimeStr();//"1PC9W";
		  strProID =  LongToString(parseLong(strProID));//"1DOHE3Y";
	
	         
		 strChannelID = LongToString(parseLong(strChannelID));//"GL21Y7RLN";
		 strPayCode = LongToString(parseLong(strPayCode));//"5XZ9TPT4";
		 strImei = LongToString(parseLong(strImei));//"2M7XDP";//strTime_MakeOrderStr="a3#"+strProID+"#"+strChannelID+"#"+strImsi+"#"+strImei+"#"+strTime+"#"+strPayCode+"#"
		 strImsi = LongToString(parseLong(strImsi));
			
		  String ret = getMSGContent( strApkPublicKey, strAppKey, md5, md5dex,strTime,
				 strProID, strChannelID, strPayCode, strImei, strImsi, strUserdata);
		  return ret;
		  
	  }
		  
	  public static String b(long paramLong)
	  {
		if (paramLong < 10L)
			return paramLong + "";
		long l1 = 55L + paramLong;
		char c1 = (char) (int) l1;
		return String.valueOf(c1);
	 }


			  
	  public static String LongToString(long paramLong)
	  {
	    //d.c("MMBillingSDk", "t10=  " + paramLong);
	    String str1 = paramLong + "";
	    //d.c("MMBillingSDk", "tt=  " + str1);
	    String str2 = "";
	    long l1 = paramLong / 36L;
	    long l2 = paramLong % 36L;
	    for (str2 = str2 + b(l2); l1 > 0L; str2 = str2 + b(l2))
	    {
	       paramLong = l1;
	       l1 = paramLong / 36L;
	       l2 = paramLong % 36L;
	    }
	    StringBuffer localStringBuffer = new StringBuffer(str2);
	    localStringBuffer.reverse().toString();
	  
	    return localStringBuffer.toString();
	  }
		  
	  public static long parseLong(String str)
	  {	 	 
	 	 str.replaceAll("[a-zA-Z`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#�?…�?&*（）—�?+|{}【�?‘；：�?“�?。，、？]", "");
	 	 return Long.parseLong(str);	 	 
	 }
			  
}
