package com.yeepay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import util.DebuUtil;
import util.FileUtil;

import java.util.HashMap;

/**
 *
 * <p>Title: </p>
 * <p>Description: http utils </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author LiLu
 * @version 1.0
 */
public class HttpUtils {

 
  private static final String URL_PARAM_CONNECT_FLAG = "&";
  private static Log log = LogFactory.getLog(HttpUtils.class);

  private HttpUtils() {
  }

  /**
   * GET METHOD
   * @param strUrl String
   * @param map Map
   * @throws IOException
   * @return List
   */
  public static List URLGet(String strUrl, Map map) throws IOException {
    String strtTotalURL = "";
    List result = new ArrayList();
    if(strtTotalURL.indexOf("?") == -1) {
      strtTotalURL = strUrl + "?" + getUrl(map);
    } else {
      strtTotalURL = strUrl + "&" + getUrl(map);
    }
    //DebuUtil.log("strtTotalURL="+strtTotalURL);
    URL url = new URL(strtTotalURL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setConnectTimeout(20*1000);
    con.setReadTimeout(20*1000);
    con.setUseCaches(false);
    con.setFollowRedirects(true);
    
   
    byte[] buf=FileUtil.getByteArrayFromInputstream(con.getInputStream(),-1);
   	String str = new String(buf,"UTF-8");
   	result.add(str);
   	
    /*BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));//
    while (true) {
      String line = in.readLine();
     
      
      if (line == null) {
        break;
      }
      else {
    	  //line =new String(line.getBytes(),"UTF-8");
    	  result.add(line);
      }
    }
    in.close();
    */
    return (result);
  }

  public static List URLGet(String strUrl, Map map,int timeOut) throws IOException {
	    String strtTotalURL = "";
	    List result = new ArrayList();
	    if(strtTotalURL.indexOf("?") == -1) {
	      strtTotalURL = strUrl + "?" + getUrl(map);
	    } else {
	      strtTotalURL = strUrl + "&" + getUrl(map);
	    }
	    //log.debug("strtTotalURL:" + strtTotalURL);
	    URL url = new URL(strtTotalURL);
	    //System.out.println(strtTotalURL);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setConnectTimeout(timeOut);
	    con.setReadTimeout(timeOut);
	    con.setUseCaches(false);
	    con.setFollowRedirects(true);
	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream()));//
	    while (true) {
	      String line = in.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result.add(line);
	      }
	    }
	    in.close();
	    return (result);
	  }

  /**
   * POST METHOD
   * @param strUrl String
   * @param content Map
   * @throws IOException
   * @return List
   */
  public static List URLPost(String strUrl, Map map) throws IOException {

    String content = "";
    content = getUrl(map);
    String totalURL = null;
    if(strUrl.indexOf("?") == -1) {
      totalURL = strUrl + "?" + content;
    } else {
      totalURL = strUrl + "&" + content;
    }
    URL url = new URL(strUrl);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setConnectTimeout(20*1000);
    con.setReadTimeout(20*1000);
    con.setDoInput(true);
    con.setDoOutput(true);
    con.setAllowUserInteraction(false);
    con.setUseCaches(false);
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
        getOutputStream()));
    bout.write(content);
    bout.flush();
    bout.close();
    BufferedReader bin = new BufferedReader(new InputStreamReader(con.
        getInputStream()));
    List result = new ArrayList(); 
    while (true) {
      String line = bin.readLine();
      if (line == null) {
        break;
      }
      else {
    	  result.add(line);
      }
    }
    return (result);
  }

  public static String URLPostUTF82(String strUrl, String content) throws IOException {

	   // String content = "";
	    //content = map;//getUrl(map);
	    String totalURL = null;
	    if(strUrl.indexOf("?") == -1) {
	      totalURL = strUrl + "?" + content;
	    } else {
	      totalURL = strUrl + "&" + content;
	    }
	    URL url = new URL(strUrl);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setConnectTimeout(20*1000);
	    con.setReadTimeout(20*1000);
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setAllowUserInteraction(false);
	    con.setUseCaches(false);
	    con.setRequestMethod("POST");
	    //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	   // con.setRequestProperty("Content-Type", "text/html;charset=utf8");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
	    
	    //con.setRequestProperty("Accept-Charset", "UTF-8,*;q=0.5");
	    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
	        getOutputStream()));
	    bout.write(content);
	    bout.flush();
	    bout.close();
	    
	    InputStream   in=con.getInputStream();
	    String result="";
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		result = str;
		
	   /* BufferedReader bin = new BufferedReader(new InputStreamReader(con.
	        getInputStream()));
	    String result="";
	   // List result = new ArrayList(); 
	    while (true) {
	      String line = bin.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result=line; //result.add(line);
	    	  break;
	      }
	    }*/
	    return (result);
	  }
 
  public static String sendPostDataEncode(String POST_URL, String content){
		HttpURLConnection connection = null;
		DataOutputStream out = null;
		BufferedReader reader = null;
		String line = "";
		String result = "";
		try {
			URL postUrl = new URL(POST_URL);
			connection = (HttpURLConnection) postUrl.openConnection();
			connection.setConnectTimeout(20*1000);
			connection.setReadTimeout(20*1000);
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			connection.connect();
			out = new DataOutputStream(connection.getOutputStream());
			content = URLEncoder.encode(content, "utf-8");
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
  
  public static String URLPostUTF8(String strUrl, byte[] sendbuf) throws IOException {

	   // String content = "";
	    //content = map;//getUrl(map);
	   
	    URL url = new URL(strUrl);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setConnectTimeout(20*1000);
	    con.setReadTimeout(20*1000);
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setAllowUserInteraction(false);
	    con.setUseCaches(false);
	    con.setRequestMethod("POST");
	    //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	   // con.setRequestProperty("Content-Type", "text/html;charset=utf8");
	    String lenstr = String.format("%d", sendbuf.length);
	    con.setRequestProperty("Content-length", lenstr);
	    con.setRequestProperty("Content-Type", "application/octet-stream;charset=utf8");
	    
	    //con.setRequestProperty("Accept-Charset", "UTF-8,*;q=0.5");
	    DataOutputStream bout = new DataOutputStream(con  
	               .getOutputStream());  
	    //out.write(arg0, arg1, arg2);
	    
	   // BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
	     //   getOutputStream()));
	   // bout.write(content);
	    bout.write(sendbuf, 0, sendbuf.length);
	   // bout.;
	    //bout.w;
	    bout.flush();
	    bout.close();
	    
	    InputStream   in=con.getInputStream();
	    String result="";
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		result = str;
		
	   /* BufferedReader bin = new BufferedReader(new InputStreamReader(con.
	        getInputStream()));
	    String result="";
	   // List result = new ArrayList(); 
	    while (true) {
	      String line = bin.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result=line; //result.add(line);
	    	  break;
	      }
	    }*/
	    return (result);
	  }
  
  public static String URLPostUTF8(String strUrl, String content) throws IOException {

	   // String content = "";
	    //content = map;//getUrl(map);
	    String totalURL = null;
	    if(strUrl.indexOf("?") == -1) {
	      totalURL = strUrl + "?" + content;
	    } else {
	      totalURL = strUrl + "&" + content;
	    }
	    URL url = new URL(strUrl);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setConnectTimeout(20*1000);
	    con.setReadTimeout(20*1000);
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setAllowUserInteraction(false);
	    con.setUseCaches(false);
	    con.setRequestMethod("POST");
	    //con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
	   // con.setRequestProperty("Content-Type", "text/html;charset=utf8");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
	    
	    //con.setRequestProperty("Accept-Charset", "UTF-8,*;q=0.5");
	    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
	        getOutputStream()));
	    bout.write(content);
	    bout.flush();
	    bout.close();
	    
	    InputStream   in=con.getInputStream();
	    String result="";
		byte[] buf=FileUtil.getByteArrayFromInputstream(in,-1);
		String str = new String(buf,"UTF-8");
		result = str;
		
	   /* BufferedReader bin = new BufferedReader(new InputStreamReader(con.
	        getInputStream()));
	    String result="";
	   // List result = new ArrayList(); 
	    while (true) {
	      String line = bin.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result=line; //result.add(line);
	    	  break;
	      }
	    }*/
	    return (result);
	  }
  
  public static String URLPost(String strUrl, String content) throws IOException {

	   // String content = "";
	    //content = map;//getUrl(map);
	    String totalURL = null;
	    if(strUrl.indexOf("?") == -1) {
	      totalURL = strUrl + "?" + content;
	    } else {
	      totalURL = strUrl + "&" + content;
	    }
	    URL url = new URL(strUrl);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setConnectTimeout(20*1000);
	    con.setReadTimeout(20*1000);
	    con.setDoInput(true);
	    con.setDoOutput(true);
	    con.setAllowUserInteraction(false);
	    con.setUseCaches(false);
	    con.setRequestMethod("POST");
	    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=GBK");
	    BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(con.
	        getOutputStream()));
	    bout.write(content);
	    bout.flush();
	    bout.close();
	    BufferedReader bin = new BufferedReader(new InputStreamReader(con.
	        getInputStream()));
	    String result="";
	   // List result = new ArrayList(); 
	    while (true) {
	      String line = bin.readLine();
	      if (line == null) {
	        break;
	      }
	      else {
	    	  result=line; //result.add(line);
	    	  break;
	      }
	    }
	    return (result);
	  }
  /**
   * ���URL
   * @param map Map
   * @return String
   */
  private static String getUrl(Map map) {
    if (null == map || map.keySet().size() == 0) {
      return ("");
    }
    StringBuffer url = new StringBuffer();
    Set keys = map.keySet();
    for (Iterator i = keys.iterator(); i.hasNext(); ) {
      String key = String.valueOf(i.next());
      if (map.containsKey(key)) {
    	 Object val = map.get(key);
    	 String str = val!=null?val.toString():"";
    	 try {
			str = URLEncoder.encode(str, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        url.append(key).append("=").append(str).
            append(URL_PARAM_CONNECT_FLAG);
      }
    }
    String strURL = "";
    strURL = url.toString();
    if (URL_PARAM_CONNECT_FLAG.equals("" + strURL.charAt(strURL.length() - 1))) {
      strURL = strURL.substring(0, strURL.length() - 1);
    }
    return (strURL);
  }

}

