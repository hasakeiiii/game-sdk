package azul;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import common.Constant;

import db.ConnectionFactory;

public class KeyTool {
	static final String INSERT_CHARGE_STR="insert into charge (linkid,sid,cid,tid,cfg_sell_id,fee,operator,mobile,msg,spnum,province,city,date_time) values (?,?,?,?,?,?,?,?,?,?,?,?,now())";
	static final String UPDATE_CHARGE_STR="update charge set ok=1 where linkid=?";
	static final String INSERT_CHARGE_ACT_STR="insert into charge (linkid,sid,cid,tid,cfg_sell_id,fee,operator,mobile,msg,spnum,province,city,ok,date_time) values (?,?,?,?,?,?,?,?,?,?,?,?,1,now())";
	
	//插入上行记录
	public static boolean mo(String linkid,String sid,int fee,String mobile,String msg,String spnum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(INSERT_CHARGE_STR);
			String[] area=getAreaByMobile(mobile);
			String[] cidTid=getCidTid(msg);
			pstmt.setString(1,linkid);
			pstmt.setString(2,sid);
			pstmt.setString(3,cidTid[0]);
			pstmt.setString(4,cidTid[1]);
			pstmt.setInt(5,Integer.valueOf(cidTid[2]));
			pstmt.setInt(6, fee);
			pstmt.setInt(7,getWap("",mobile));
			pstmt.setString(8,mobile);
			pstmt.setString(9,msg);
			pstmt.setString(10,spnum);
			pstmt.setString(11,area[2]);
			pstmt.setString(12,area[3]);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			ConnectionFactory.close(pstmt,conn);
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean mr(String linkid,String msg,String key){
		if(!msg.equals(key)){
			return false;
		}
		Connection conn = null;
		PreparedStatement pstmtA = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			pstmtA = conn.prepareStatement(UPDATE_CHARGE_STR);
			pstmtA.setString(1,linkid);
			pstmtA.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			ConnectionFactory.close(pstmtA,conn);
		}
		return true;
	}
	
	//二道贩子通道使用插入费用工具
	@SuppressWarnings("unchecked")
	public static boolean charge(String linkid,String sid,int fee,String mobile,String msg,String spnum){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(INSERT_CHARGE_ACT_STR);
			String[] area=getAreaByMobile(mobile);
			pstmt.setString(1,linkid);
			pstmt.setString(2,sid);
			String[] cidTid=getCidTid(msg);
			pstmt.setString(3,cidTid[0]);
			pstmt.setString(4,cidTid[1]);
			pstmt.setInt(5,Integer.valueOf(cidTid[2]));
			pstmt.setInt(6, fee);
			pstmt.setInt(7,getWap("",mobile));
			pstmt.setString(8,mobile);
			pstmt.setString(9,msg);
			pstmt.setString(10,spnum);
			pstmt.setString(11,area[2]);
			pstmt.setString(12,area[3]);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			ConnectionFactory.close(pstmt,conn);
		}
		return true;
	}
	
	//删除费用中24小时以前失败的数据减小数据量
	public static boolean deleteChargeFail(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement("delete from charge where ok=0 and date_time<date_sub(now(),interval 1 day)");
			pstmt.executeUpdate();
			//JspUtil.log("c://2.txt","shan chu duoyu wenjian "+util.DateUtil.getDate());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			ConnectionFactory.close(pstmt,conn);
		}
		return true;
	}
	
	private static String[] getCidTid(String msg){
		String[] result=new String[]{"","","-1"};
		int index=msg.indexOf("O");
		if(index>-1){
			String[] arr=msg.split("O");
			if(arr.length>1){
				result[0]=arr[1];
				//不加tid防止指令过长
				//result[1]=msg.substring(lastIndex+1,msg.length());
				//得到对应的商务cfg_sell_id
				//if(azul.CacheCompany.companySellMap.containsKey(result[0])){
				//	result[2]=azul.CacheCompany.companySellMap.get(result[0]);
				//}
			}
		}
		return result;
	}
	
	//带超大参数的方法调用util.FileUtil.runJspParam(String url,HashMap map)
	public static String send(String url){
		StringBuffer result=new StringBuffer("");
		OutputStream os=null;
		BufferedReader br=null;
		HttpURLConnection urlConn=null;
		try{
			urlConn= (HttpURLConnection) new URL(url).openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			
			os = urlConn.getOutputStream();
			os.close();
			
			String line = "";
			br = new BufferedReader(new InputStreamReader(urlConn.getInputStream(),"utf-8"));
			while ((line = br.readLine()) != null) {
				result.append(line.trim());
			}
			System.out.println(url);
		}
        catch(IOException e){
			System.out.println("keyTool.send io exception");
			e.printStackTrace();
			return "-1";
		}
		catch(Exception e){
			System.out.println(e);
		}
		finally{
			try {if(br!=null)br.close();if(urlConn!=null)urlConn.disconnect();} catch (IOException e) {System.out.println(e);}
		}
		//做同步日志
		//dao.CfgLogDao.log(1,"",url);
		return result.toString();
	}
	
	public static String sendGetData(String get_url) {
        String result = "";
        URL getUrl = null;
        BufferedReader reader = null;
        String lines = "";
        HttpURLConnection connection = null;
        try {
            getUrl = new URL(get_url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            // 取得输入流，并使用Reader读取
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
            while ((lines = reader.readLine()) != null) {
                result = result + lines;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
            	try {
            		reader.close();
				} catch (Exception e) {
				}
                reader = null;
            }
            connection.disconnect();
        }
        System.out.println(get_url);
        return result;
    }

	public static String sendPostData(String POST_URL, String content){
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		String line = "";
		String result = "";
		try {
			URL postUrl = new URL(POST_URL);
			connection = (HttpURLConnection) postUrl.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connection.connect();
			out = new DataOutputStream(connection.getOutputStream());
			// content = URLEncoder.encode(content, "utf-8");
			// DataOutputStream.writeBytes将字符串中的16位的unicode字符�?8位的字符形式写道流里�?
			out.writeBytes(content);
			out.flush();
			out.close();
			// 获取结果
			reader = new BufferedReader(new InputStreamReader(connection
					.getInputStream(), "utf-8"));// 设置编码
			while ((line = reader.readLine()) != null) {
				result = result + line;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (Exception e) {
			}
			connection.disconnect();
		}
		System.out.println(POST_URL);
		return result;
	}
	
	//判断所属运营商
	public static int getWap(String smsCenter,String mobile){
		int operator=1;
		//先用短信中心号码判断
		if(!"".equals(smsCenter)){
			smsCenter=smsCenter.replace("+86", "");
			if(smsCenter.indexOf("86")==0){
				smsCenter=smsCenter.substring(2,smsCenter.length());
			}
			if(smsCenter.length()>2){
				String oper=smsCenter.substring(0,3);
				if(common.Constant.CNC.indexOf(oper)>-1){
					operator=2;
				}
				else if(common.Constant.CTC.indexOf(oper)>-1){
					operator=3;	
				}
			}
		}
		else if(!"".equals(mobile)){
			mobile=mobile.replace("+86", "");
			if(mobile.indexOf("86")==0){
				mobile=mobile.substring(2,mobile.length());
			}
			if(mobile.length()>2){
				String oper=mobile.substring(0,3);
				if(common.Constant.CNC.indexOf(oper)>-1){
					operator=2;
				}
				else if(common.Constant.CTC.indexOf(oper)>-1){
					operator=3;	
				}
			}
		}
		return operator;
	}
	
	public static String[] getArea(String mobile,String smsCenter,String ip){
		String[] area=new String[]{"00","00","00","00","0"};
		/*
		//首先使用手机号码
		area=getAreaByIp(ip);
		if("00".equals(area[0])){
			//再使用短信中心号码判断
			//area=getAreaBySms(smsCenter);
			area=getAreaByMobile(mobile);
			if("00".equals(area[0])){
				//最后使用ip判断
				area=getAreaBySms(smsCenter);
			}
		}
		*/
		area=getAreaBySms(smsCenter);
		if("00".equals(area[0])){
			//再使用短信中心号码判断
			//area=getAreaBySms(smsCenter);
			area=getAreaByMobile(mobile);
			if("00".equals(area[0])){
				//最后使用ip判断
				area=getAreaByIp(ip);
			}
		}
		
		return area;
	}
	
	/*
	public static String[] getArea(String mobile,String smsCenter,String ip){
		String[] area=new String[]{"00","00","00","00"};
		//首先使用手机号码
		//area=getAreaByMobile(mobile);
		area=getAreaByMobile(mobile);
		if("00".equals(area[0])){
			//再使用短信中心号码判断
			//area=getAreaBySms(smsCenter);
			area=getAreaBySms(smsCenter);
			if("00".equals(area[0])){
				//最后使用ip判断
				area=getAreaByIp(ip);
			}
		}
		return area;
	}
	*/
	
	//返回的地区信息是一个4长度数组，第一项是中文省名，第二项是中文地区，
	//第三位是省份代号，第四位是城市代号
	@SuppressWarnings("unchecked")
	public static String[] getAreaByMobile(String mobile){
		String[] area=new String[]{"00","00","00","00","0"};
		//首先使用短信中心号码判断，如果判断不出来使用ip判断
		mobile=mobile.trim();
		mobile=mobile.replace("+86", "");
		if(mobile.indexOf("86")==0){
			mobile=mobile.substring(2,mobile.length());
		}
		if(mobile.length()<11){
			return area;
		}
		mobile=mobile.substring(0,7);
		int operator=0;
		String oper=mobile.substring(0,3);
		if(common.Constant.CMC.indexOf(oper)>-1){
			operator=1;
		}
		else if(common.Constant.CNC.indexOf(oper)>-1){
			operator=2;
		}
		area[4]=operator+"";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			String sql="select province,province_code,city,city_code from cfg_area_cmc where mobile=?";
			if(operator==2){
				sql="select province,province_code,city,city_code from cfg_area_cnc where mobile=?";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mobile);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				area[0] = rs.getString("province_code")==null?"00":rs.getString("province_code");
				area[1] = rs.getString("city_code")==null?"00":rs.getString("city_code");
				area[2] = rs.getString("province")==null?"00":rs.getString("province");
				area[3] = rs.getString("city")==null?"00":rs.getString("city");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(rs,pstmt,conn);
		}
		return area;
	}
	
	//根据短信中心号码得到手机归属地 0 省份代码 1 地区代码 2 省份 3 地区
	public static String[] getAreaBySms(String smsCenter){
		String[] area=new String[]{"00","00_00","","","0"};
		//首先使用短信中心号码判断，如果判断不出来使用ip判断
		smsCenter=smsCenter.trim();
		smsCenter=smsCenter.replace("+86", "");
		if(smsCenter.indexOf("86")==0){
			smsCenter=smsCenter.substring(2,smsCenter.length());
		}
		else if(smsCenter.indexOf("086")==0){
			smsCenter=smsCenter.substring(3,smsCenter.length());
		}
		else if(smsCenter.indexOf("0086")==0){
			smsCenter=smsCenter.substring(4,smsCenter.length());
		}
		else if(smsCenter.indexOf("0013")==0) {
			smsCenter=smsCenter.substring(2,smsCenter.length());
		}
		if(smsCenter.length()<9){
			return area;
		}
		else{
			smsCenter=smsCenter.substring(0,9);
		}
		
		//首先找内存中的缓存数据
		/*
		if(CacheFee.smsCenterMap!=null && CacheFee.smsCenterMap.containsKey(smsCenter)){
			area=CacheFee.smsCenterMap.get(smsCenter);
		}
		*/
		return area;
	}
	
	//根据短信中心号码得到手机归属地 0 省份代码 1 地区代码 2 省份 3 地区
	public static String[] getAreaBySmsTable(String smsCenter){
		String[] area=new String[]{"00","00_00","",""};
		//首先使用短信中心号码判断，如果判断不出来使用ip判断
		smsCenter=smsCenter.trim();
		smsCenter=smsCenter.replace("+86", "");
		if(smsCenter.indexOf("86")==0){
			smsCenter=smsCenter.substring(2,smsCenter.length());
		}
		else if(smsCenter.indexOf("086")==0){
			smsCenter=smsCenter.substring(3,smsCenter.length());
		}
		else if(smsCenter.indexOf("0086")==0){
			smsCenter=smsCenter.substring(4,smsCenter.length());
		}
		else if(smsCenter.indexOf("0013")==0) {
			smsCenter=smsCenter.substring(2,smsCenter.length());
		}
		if(smsCenter.length()<9){
			return area;
		}
		else{
			smsCenter=smsCenter.substring(0,9);
		}
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement("select * from cfg_area_sms where mobile=?");
			pstmt.setString(1,smsCenter);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				area[0] = rs.getString("province_code");
				area[1] = rs.getString("city_code")==null?"00":rs.getString("city_code");;
				area[2] = rs.getString("province");
				area[3] = rs.getString("city")==null?"00":rs.getString("city");;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(rs,pstmt,conn);
		}
		return area;
	}
	
	//使用ip检测ip归属地，同时得到运营商
	public static String[] getAreaByIp(String ip){
		String[] area=new String[]{"00","00_00","","","0"};
		String[] arr=ip.split("\\.");
		if(arr.length!=4){
			return area;
		}
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			long ipNum=ipToLong(ip);
			conn = ConnectionFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement("select * from cfg_area_ip where StartIPNum<=? and EndIpNum>=? order by StartIPNum limit 1");
			//System.out.println("select * from iptable where StartIPNum<="+ipNum+" and EndIpNum>="+ipNum+" order by StartIPNum limit 1");
			pstmt.setLong(1, ipNum);
			pstmt.setLong(2, ipNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				area[0] = rs.getString("province_code");
				area[1] = rs.getString("city_code")==null?"00":rs.getString("city_code");;
				area[2] = rs.getString("province");
				area[3] = rs.getString("city")==null?"00":rs.getString("city");
				area[4] = rs.getString("operator")==null?"0":rs.getString("operator");
			}
		} catch (Exception e) {
			System.out.println("ip:"+ip);
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(rs,pstmt,conn);
		}
		return area;
	}
	
	public static int getOperatorByIpTable(String ip){
		int operator=0;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			long ipNum=ipToLong(ip);
			conn = ConnectionFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement("select * from iptable where StartIPNum<=? and EndIpNum>=? order by StartIPNum limit 1");
			//System.out.println("select * from iptable where StartIPNum<="+ipNum+" and EndIpNum>="+ipNum+" order by StartIPNum limit 1");
			pstmt.setLong(1, ipNum);
			pstmt.setLong(2, ipNum);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				operator = rs.getInt("operator");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(rs,pstmt,conn);
		}
		return operator;
	}
	
	//将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理   
    public static long ipToLong(String strIp){   
        long[] ip = new long[4];   
        //先找到IP地址字符串中.的位置   
        int position1 = strIp.indexOf(".");   
        int position2 = strIp.indexOf(".", position1 + 1);   
        int position3 = strIp.indexOf(".", position2 + 1);   
        //将每个.之间的字符串转换成整型   
        ip[0] = Long.parseLong(strIp.substring(0, position1));   
        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));   
        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));   
        ip[3] = Long.parseLong(strIp.substring(position3+1));   
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];   
    }   
       
    //将十进制整数形式转换成127.0.0.1形式的ip地址   
    public static String longToIP(long longIp){   
        StringBuffer sb = new StringBuffer("");   
        //直接右移24位   
        sb.append(String.valueOf((longIp >>> 24)));   
        sb.append(".");   
        //将高8位置0，然后右移16位   
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));   
        sb.append(".");   
        //将高16位置0，然后右移8位   
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));   
        sb.append(".");   
        //将高24位置0   
        sb.append(String.valueOf((longIp & 0x000000FF)));   
        return sb.toString();   
    } 
	
	//根据省份编码得到省份
	public static String getAreaByCode(String area_code){
		StringBuffer sb=new StringBuffer();
		String[] arr=area_code.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(!"".equals(arr[i]) && !"00".equals(arr[i])){
				for (int j = 0; j < Constant.AREA_CODE.length; j++) {
					if(Constant.AREA_CODE[j].equals(arr[i])){
						sb.append(Constant.AREA[j]).append(",");
						break;
					}
				}
			}
		}
		return sb.toString();
	}
	
	//根据省份得到省份编码
	public static String getCodeByArea(String area){
		StringBuffer sb=new StringBuffer();
		String[] arr=area.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(!"".equals(arr[i]) && !"00".equals(arr[i])){
				for (int j = 0; j < Constant.AREA.length; j++) {
					if(Constant.AREA[j].equals(arr[i])){
						sb.append(Constant.AREA[j]).append(",");
						break;
					}
				}
			}
		}
		return sb.toString();
	}
	
	//根据一个省份城市编码得到城市,支持多个省市
	public static String getCityByCode(String city_code){
		if(city_code==null || "".equals(city_code)){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		String[] arr=city_code.split(",");
		for (int i = 0; i < arr.length; i++) {
			if(!"".equals(arr[i]) && !"00_00".equals(arr[i])){
				String[] area=arr[i].split("_");
				for (int j = 0; j < Constant.AREA_CODE.length; j++) {
					if(Constant.AREA_CODE[j].equals(area[0])){
						for (int k = 0; k < Constant.CITY_CODE[j].length; k++) {
							if(Constant.CITY_CODE[j][k].equals(arr[i])){
								sb.append(Constant.CITY[j][k]).append(",");
								break;
							}
						}
					}
				}
			}
		}
		return sb.toString();
	}
	
	public static String[] replaceArea(String[] area){
		if(!"00".equals(area[0])){
			for (int i = 0; i < Constant.AREA.length; i++) {
				if(Constant.AREA[i].equals(area[0])){
					area[2]=Constant.AREA_CODE[i];
					break;
				}
			}
		}
		return area;
	}
	
	public static String getImsiTableName(String imsi){
		String tableName="";
		if(imsi.length()<8){
			return "user_sms_9400000";
		}
		String imsiStr=imsi.substring(0,7);
		String nums= "0123456789 "; 
        for(int i=0;i<imsiStr.length();i++){ 
              if(nums.indexOf(imsiStr.charAt(i))==-1){
            	  return "user_sms_9400000";
              }
        }
		int tableNameInt=Integer.valueOf(imsi.substring(0,7));
		if(tableNameInt>=9460000 && tableNameInt<9460030){
			tableName="user_sms_"+tableNameInt;
		}
		else{
			tableName="user_sms_9400000";
		}
		return tableName;
	}
	
	//根据imsi找对应的手机号码
	public static String getMobileByImsi(String imsi){
		String mobile="";
		Connection connC = null;
		PreparedStatement pstmtC = null;
		ResultSet rsC = null; 
		try{
			connC = ConnectionFactory.getInstance().getConnection();
			pstmtC = connC.prepareStatement("select * from "+getImsiTableName(imsi)+" where imsi=?");
			pstmtC.setString(1,imsi);
			rsC=pstmtC.executeQuery();
           	if(rsC.next()){
           		mobile=rsC.getString("mobile")==null?"":rsC.getString("mobile");
           	}
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
        	ConnectionFactory.close(rsC,pstmtC,connC);
        }
        return mobile;
	}
	
	//根据mobile找对应的cid
	public static String[] getCidByMobile(String mobile){
		String[] cidTid=new String[]{"",""};
		Connection connC = null;
		PreparedStatement pstmtC = null;
		ResultSet rsC = null; 
		try{
			connC = ConnectionFactory.getInstance().getConnection();
			pstmtC = connC.prepareStatement("select * from charge where mobile=? and cid is not null and cid<>''");
			pstmtC.setString(1,mobile);
			rsC=pstmtC.executeQuery();
           	if(rsC.next()){
           		cidTid[0]=rsC.getString("cid")==null?"":rsC.getString("cid");
           		cidTid[1]=rsC.getString("tid")==null?"":rsC.getString("tid");
           	}
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
        	ConnectionFactory.close(rsC,pstmtC,connC);
        }
        return cidTid;
	}
	
	//解析类似<version>200</version><customer>CP736</customer>结构
    public static String getParam(String str,String param){
    	String result="";
    	int i=str.indexOf("<"+param+">")+("<"+param+">").length();
    	int j=str.indexOf("</"+param+">");
    	if(i>-1 && j>-1 && i<j){
    		result=str.substring(i,j);
    	}
    	return result;
    }
	
    //得到xml脚本中的某个属性的对应值
    public static String getValue(String[] arr,String key){
    	String result="";
    	for (int i = 1; i < arr.length; i++) {
    		if(arr[i].indexOf(key)>-1){
    			result=arr[i].substring(arr[i].indexOf("\"")+1,arr[i].lastIndexOf("\""));
    			break;
    		}
    	}
    	return result;
    }
	
	//自动同步两台服务器一个表的数据,第一个参数是要同步的表名,第二个参数是是否根据主键判断是否重复，第三个参数是接收页面地址
	public static void statTableHost(String tableName,String key,String guestPage){
		Connection connC = null;
		PreparedStatement pstmtC = null;
		ResultSet rsC = null; 
		StringBuffer dataSb=new StringBuffer(tableName).append("Z_^Z").append(key).append("Z_^Z");
		try{
			connC = ConnectionFactory.getInstance().getConnection();
			pstmtC = connC.prepareStatement("select * from "+tableName);
			rsC=pstmtC.executeQuery();
			ResultSetMetaData meta = rsC.getMetaData();
			String[] nameArr=new String[meta.getColumnCount()];
			String[] typeArr=new String[meta.getColumnCount()];
			String typeStr="";
			for (int i = 0; i < meta.getColumnCount(); i++) {
				nameArr[i]=meta.getColumnName(i+1);
				typeArr[i]=getType(meta.getColumnTypeName(i+1));
				dataSb.append(nameArr[i]);
				if("String".equals(typeArr[i])){
					typeStr+="String";
       			}
				if(i!=meta.getColumnCount()-1){
					typeStr+=",";
					dataSb.append(",");
				}
			}
			dataSb.append("Z_^Z");
			dataSb.append(typeStr).append("Z_^Z");
           	while(rsC.next()){
           		StringBuffer paramSb=new StringBuffer();
           		for (int i= 0; i< nameArr.length; i++) {
           			if("String".equals(typeArr[i])){
           				dataSb.append("'");
           			}
           			dataSb.append(rsC.getString(i+1));
           			if("String".equals(typeArr[i])){
           				dataSb.append("'");
           			}
           			//防止内容里面有","所以用特殊分隔符间隔数据内容
           			if(i!=nameArr.length-1){
           				dataSb.append("Z^_Z");
           			}
				}
           		if(!rsC.isLast()) {
           			dataSb.append("Z_^Z");
           		}
           	}
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
        	ConnectionFactory.close(rsC,pstmtC,connC);
        }
		System.out.println(dataSb.toString());
		//防止传输中文乱码，加密后传输
		if(!"".equals(dataSb.toString())){
			HashMap<String,String> map=new HashMap<String,String>();
			map.put("param", JspUtil.decode(dataSb.toString()));
			//util.FileUtil.runJspParam(guestPage+"?op=statTable",map);
			//send(guestPage+"?op=statTable&param="+JspUtil.decode(allSb.toString()));
		}
	}
	
	//上面方法对应接数据的方法,注意页面根据需要选择是否清除表对应的缓存
	public static void statTableGuest(String info){
		String tempInfo=JspUtil.undecode(info);
		//String tempInfo="ad_videoZ_^Zad_video_idZ_^Zad_video_id,name,pathZ_^Z,String,StringZ_^Z1Z^_Z'搞笑视频A'Z^_Z'upload/ad/1/20101125-220238-646.3GP'Z_^Z2Z^_Z'搞笑视频B'Z^_Z'upload/ad/1/20101125-220253-381.3GP'Z_^Z3Z^_Z'搞笑视频C'Z^_Z'upload/ad/1/20101125-220306-835.3GP'Z_^Z4Z^_Z'搞笑视频D'Z^_Z'upload/ad/1/20101125-220319-679.3GP'Z_^Z5Z^_Z'搞笑视频E'Z^_Z'upload/ad/1/20101125-220332-492.3GP'Z_^Z6Z^_Z'搞笑视频F'Z^_Z'upload/ad/1/20101125-220415-509.3GP'Z_^Z7Z^_Z'搞笑视频G'Z^_Z'upload/ad/1/20101125-220434-494.3GP'Z_^Z8Z^_Z'搞笑视频H'Z^_Z'upload/ad/1/20101125-220451-416.3GP'Z_^Z9Z^_Z'搞笑视频I'Z^_Z'upload/ad/1/20101125-220505-73.3GP'Z_^Z10Z^_Z'搞笑视频J'Z^_Z'upload/ad/1/20101125-220518-526.3GP'";
        String[] arr=JspUtil.split(tempInfo,"Z_^Z");
        HashMap<String,Integer> mapData=new HashMap<String,Integer>();
        ArrayList<String> list=new ArrayList<String>();
        //第一个数据是表名，第二个参数是主键，第三个数据是字段名,第四个是字段类型
        for (int i = 3; i < arr.length; i++) {
        	if("".equals(arr[i])){
        		continue;
        	}
        	mapData.put(arr[i],0);
		}
        Connection connC = null;
		PreparedStatement pstmtC = null;
		ResultSet rsC = null; 
		StringBuffer dataSb=new StringBuffer();
		try{
			connC = ConnectionFactory.getInstance().getConnection();
			String sql="select ";
			if(!"".equals(arr[1])) {
				sql+=arr[1]+",";
			}
			sql+=arr[2]+" from "+arr[0];
			pstmtC = connC.prepareStatement(sql);
			rsC=pstmtC.executeQuery();
			int num=arr[2].split(",").length;
			String[] typeArr=arr[3].split(",");
           	while(rsC.next()){
           		//有主键
           		if(!"".equals(arr[1])) {
           			
           		}
           		else {
           			StringBuffer paramSb=new StringBuffer();
               		for (int i= 0; i< num; i++) {
               			String temp=rsC.getString(i)==null?"":rsC.getString(i);
               			if("String".equals(typeArr[i])) {
               				paramSb.append("'").append(temp).append("'");
               			}
               			else {
               				paramSb.append(temp);
               			}
    				}
               		paramSb.append("Z^_Z");
               	    //少的记录删除
           			if(!mapData.containsKey(paramSb.toString())) {
           				//add
           				list.add("insert into "+arr[0]+" "+arr[2]+" values ("+paramSb.toString()+")"); 
           			}
           			else {
           				//标记记录已经存在
           				mapData.put(paramSb.toString(), 1);
           			}
                	//改变的记录修改
           		    //多的记录插入
           		}
           		Iterator<Entry<String,Integer>> it = mapData.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String,Integer> entry = it.next();
					int value = entry.getValue();
					if(value==0) {
						String keyId = entry.getKey();
						//list.add(e);
					}
           	}
           	}
        } catch (Exception ex) {
            System.out.println(ex);
        }
        finally{
        	ConnectionFactory.close(rsC,pstmtC,connC);
        }
        
        if(list.size()<1){
        	System.out.println("KeyTool.statTableGuest:无差异数据");
        	return;
        }
        
	}
	
	private static String getType(String key){
		//INTEGER 和 TINYINT
        if(key.indexOf("INT")>-1){
        	return "int";
        }
        else if(key.indexOf("DOUBLE")>-1){
        	return "double";               	           	
        }
        else{
        	return "String";                 	
        }
	}
	
	//----------------------------------------------------ivr
	public static void main(String[] args) {
		//String[] area=azul.KeyTool.getAreaByMobile("14777696389");
		//long num=ipToLong("211.138.104.18");System.out.println(num);
		//JspUtil.p(num);
		//JspUtil.p(getAreaByIp("211.137.220.152"));
		
		System.out.println("ok");
		//statTableHost("ad_video","ad_video_id","http://localhost:8080/dingxue/_menu/index.jsp");
		//statTableGuest("");
		//JspUtil.p(KeyTool.getArea("","13800512500","222.186.19.121"));//13800755500
		//sendGetData("http://114.80.96.28:85/yuanjin.aspx?linkid=1234567azsttt&mobile=13751180191&spNum=10628315&momsg=CX");
		sendGetData("http://localhost:8080/dingxue/test.jsp?linkid=1234567azsttt&mobile=13751180191&spNum=10628315&momsg=CX");
		//JspUtil.p(KeyTool.getAreaByMobile("18221448338"));
		//JspUtil.p(KeyTool.getAreaBySmsTable("13800512500"));
		//JspUtil.p(getCityByCode("19_01,19_02,19_03,19_04,19_05,19_06,19_07,19_08,19_09,19_11,19_12,19_13,19_14"));
		//System.out.println(getAreaByCode("22"));
		//System.out.println(getImsiTableName("9460021123047717"));
		//System.out.println(getCityByCode("20_01,20_16,04_01,06_03,06_05,06_06,06_08,06_10,06_12,06_18,06_20"));
	}
}