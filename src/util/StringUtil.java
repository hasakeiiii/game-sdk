package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;


import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class StringUtil {
	//得到字符长度中文算2个
	public static int getTextLength(String str) {
		int result = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) > 127) {
				result += 2;
			} else {
				result += 1;
			}
		}
		return result;
	}
	
	// 替换特殊字符
	public static String replaceStr(String str) {
		String result = str;
		try {
			result = result.replaceAll("'", "");
			result = result.replaceAll("&", "");
			result = result.replaceAll("_", "");
			result = result.replaceAll("~", "");
			result = result.replaceAll("@", "");
			result = result.replaceAll("#", "");
			result = result.replaceAll("$", "");
			result = result.replaceAll("^", "");
			result = result.replaceAll("\r\n", "<br>");
			result = result.replaceAll("\\*", "");
			result = result.replaceAll("\\(", "");
			result = result.replaceAll("\\)", "");
			result = result.replaceAll("\\?", "");
			result = result.replaceAll(",", "");
			result = result.replaceAll("\\.", "");
			result = result.replaceAll("/", "");
			result = result.replaceAll("\\\\", "");
			result = result.replaceAll("|", "");
			result = result.replaceAll("\\[", "");
			result = result.replaceAll("\\]", "");
			result = result.replaceAll("\\{", "");
			result = result.replaceAll("\\}", "");
		} catch (Exception e) {
			System.out.println("arg1:" + str);
			e.printStackTrace();
		}
		return result;
	}
	
	//字符串右截取空格
	public static String rightTrim(String source){
		if(source == null||"".equals(source)){
			return "";
		}
		int length = source.length();
		int i = length-1;
		for(;i>-1;i--){
			if(' '==source.charAt(i)){
				continue;
			} 
			break;
		}		
		if(i == length-1){
			return source;
		}		
		return source.substring(0,i+1);
	}	

	/**  
     * 将字符串中的html代码去掉
     */  
    public static String subHtml(String input) {   
        if (input == null || input.trim().equals("")) {   
            return "";   
        }     
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");   
        str = str.replaceAll("[(/>)<]", "");   
        return str;   
    } 	
	
    //将特殊符号编码
	public static final String htmlEncode(String input) {
		if (input == null){
			return null;
		}
		input=input.replaceAll("&amp;", "&");
		input=input.replaceAll("&", "&amp;");
		input=input.replaceAll("<", "&lt;");
		input=input.replaceAll(">", "&gt;");
		input=input.replaceAll("'", "&apos;");
		input=input.replaceAll("\"", "&quot;");
		return input;
	}

	public static String getHtml(String str) {
		if (str == null || str.length() == 0)
			return "";
		StringBuffer filtered = new StringBuffer(str.length());
		char prevChar = '\0';
		try {
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c == '"')
					filtered.append("\\\"");
				else if (c == '\'')
					filtered.append("\\'");
				else if (c == '\\')
					filtered.append("\\\\");
				else if (c == '\t')
					filtered.append("\\t");
				else if (c == '\n') {
					if (prevChar != '\r')
						filtered.append("<br>");
				} else if (c == '\r')
					filtered.append("<br>");
				else if (c == '\f')
					filtered.append("\\f");
				else if (c == '/')
					filtered.append("\\/");
				else if (c == '<')
					filtered.append("&lt;");
				else if (c == '>')
					filtered.append("&gt;");
				else
					filtered.append(c);
				prevChar = c;
			}

		} catch (Exception e) {
			System.out.println((new StringBuilder("arg1:")).append(str)
					.toString());
			e.printStackTrace();
		}
		return filtered.toString();
	}

    public static String setHtml(String str) {
		if (str == null || str.length() == 0)
			return "";
		try {
			str = str.replaceAll("\\\"", "\"");
			str = str.replaceAll("\\'", "'");
			str = str.replaceAll("\\t", "\t");
			str = str.replaceAll("\\f", "\f");
			str = str.replaceAll("\\/", "/");
		} catch (Exception e) {
			System.out.println((new StringBuilder("arg1:")).append(str)
					.toString());
			e.printStackTrace();
		}
		return str;
	}

    public static String htmlFilter(String str) {
		String result = "";
		try {
			if (str == null || str .equals(""))
				return result;
			StringBuffer filtered = new StringBuffer(str.length());
			char prevChar = '\0';
			for (int i = 0; i < str.length(); i++) {
				char c = str.charAt(i);
				if (c == '"')
					filtered.append("\\\"");
				else if (c == '\'')
					filtered.append("\\'");
				else if (c == '\\')
					filtered.append("\\\\");
				else if (c == '\t')
					filtered.append("\\t");
				else if (c == '\n') {
					if (prevChar != '\r')
						filtered.append("\\n");
				} else if (c == '\r')
					filtered.append("\\n");
				else if (c == '\f')
					filtered.append("\\f");
				else if (c == '/')
					filtered.append("\\/");
				else
					filtered.append(c);
				prevChar = c;
			}

			result = filtered.toString();
		} catch (Exception e) {
			System.out.println((new StringBuilder(
					"azul.JspUtil.getHtml error:str is ")).append(str).append(
					"wrong is:").append(e).toString());
		}
		return result;
	}

	public static final String gbkToUtf8(String str) {
		String result = "";
		try {
            
		} catch (Exception e) {
			System.out.println("StringUtil.gbkToUtf8 error:" + e);
		}
		return result;
	}
	
    public static String toUTF8(String str)
    {
    	String result="";
        if(str == null)
            return str;
        try {
        	result=new String(str.getBytes("gb2312"), "utf-8");
		} catch (Exception e) {
		}
        return result;
    }
   
    public static String UTFTogb(String gbString){
        char utfBytes[] = gbString.toCharArray();
        StringBuffer unicodeBytes = new StringBuffer("");
        for(int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++){
            String hexB = Integer.toHexString(utfBytes[byteIndex]);
            if(hexB.length() <= 2)
                hexB = "00" + hexB;
            unicodeBytes.append("\\u").append(hexB);
        }

        System.out.println("gbToUTF is: " + unicodeBytes);
        return unicodeBytes.toString();
    }
    
    public String saveConvert(String theString, boolean escapeSpace) {
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len*2);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            switch(aChar) {
		case ' ':
		    if (x == 0 || escapeSpace) 
			outBuffer.append('\\');

		    outBuffer.append(' ');
		    break;
                case '\\':outBuffer.append('\\'); outBuffer.append('\\');
                          break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>  8) & 0xF));
                        outBuffer.append(toHex((aChar >>  4) & 0xF));
                        outBuffer.append(toHex( aChar        & 0xF));
                    } else {
                        if (specialSaveChars.indexOf(aChar) != -1)
                            outBuffer.append('\\');
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }
    
    private static final String specialSaveChars = "=: \t\r\n\f#!";
    
    private static char toHex(int nibble) {
    	return hexDigit[(nibble & 0xF)];
        }
    /** A table of hex digits */
    private static final char[] hexDigit = {
	'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
    
    public String addPrint(){
    	StringBuffer sb = new StringBuffer();
    	BufferedReader br = null;
		String input="";
		try {
			br = new BufferedReader(new FileReader("c:/1.txt"));
			String lineStr = "";
			while ((lineStr = br.readLine()) != null) { // ���ж�ȡ�ļ�
				input+=lineStr;
			}
	    	sb.append("out.println(\"");
	    	input=Matcher.quoteReplacement(input);
	    	input=input.replaceAll("\\\\\"", "\\\\\\\"");
	    	input=input.replaceAll("\\\"", "\\\\\"");
	    	input=input.replaceAll("\n", "\\\\n");
	    	input=input.replaceAll("    ", "\\\\t");
	    	sb.append(input);
	    	sb.append("\");");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
    }
    
    /*
    public static byte bswap(byte a){
        byte b = 0;
        for(int i = 0; i < 2; ++i)
            b |= ((a & (1 << i)) == 0 ? 0 : 1) << (2-i);
        return b;
    }
    */

    public static String bswap(byte a){
    	byte b=0;
    	String temp=a+"";
    	String tempN="";
        for(int i = 1; i > -1; i--){
        	tempN+=temp.charAt(i);
        }
        System.out.println("ccc:"+tempN);
        return tempN;

    }
    
    public static boolean is_nullString(String str)
    {
    	boolean ret = true;
    	if((str != null) && (str.length() > 0))
    	{
    		ret =false;
    	}
    	return ret;
    }
    
	public static String getString(JSONObject json,String tag)
    {
    	String ret="";
    	if(json.has(tag))
		{
		   ret = json.getString(tag);
		}
		return ret;
		
    }
    
	public static int getInt(JSONObject json,String tag)
    {
    	int ret = 0;
    	if(json.has(tag))
		{
		   ret = json.getInt(tag);
		}
		return ret;
		
    }
	
	public static String getNo(int leng) {
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss"); Date
		 * date = new Date(); String key = format.format(date);
		 * 
		 * java.util.Random r = new java.util.Random(); key += r.nextInt(); key
		 * = key.substring(0, leng);
		 */
		String ret = "";
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,0, 1, 2, 3, 4, 5, 6 };
		java.util.Random rand = new java.util.Random();
		for (int i = array.length-2; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < leng; i++)
			//result = result * 10 + array[i];
			ret +=array[i];
		//ret = String.format("%d", result);
		return ret;
	}
	
	public static void main(String[] args) {
        	try {
        		//13534963461  9460023892357463
        		 //String bbb="%6VSMS8%1&13751180190&4&10,106&7&1&0&,,&F,,&兔,,&吉,,&";
        		 String bbb="6VSMS0-1-2-13751180190-1DWZWX-1066-移动- 106-中国移动-106688888-确认";
        		 String ccc="6VSMS0-1-1-1062806002-23-10-小时----";
        	        String aaa=java.net.URLEncoder.encode(ccc,"UTF-8");
        	        
        	        System.out.println(aaa);
        	    //String bbb=aaa;  
        		 System.out.println(bbb.length());
        	        byte[] b=bbb.getBytes();
        	        byte[] c=new byte[b.length];
        	        StringBuffer sb=new StringBuffer();
        	        for (int i = 0; i < b.length; i++) {
        	        	System.out.println(b[i]);
        	        	sb.append(bswap(b[i]));  	
        			}
        	        System.out.println(sb.toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
        
		// ArrayUtil
		// .printArray("/home/sam/EMMS/gif/128128256/07005782.gif$|$/home/sam/EMMS/hexian/24/05010393.mid$|$30|$|"
		// .split("\\$\\|\\$"));
		// System.out.println("aababccba".replaceAll("abc", "cba"));
		// Pattern p = Pattern
		// .compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");
		// Matcher m = p.matcher("alanxiong@21cn.com");
		// if (!m.find()) {
		// System.err.println("EMAIL����");
		// }

//		 String[] arr = StringUtil.split("||this is|| a test||||of the system||||","||");
//		 for (int i = 0; i < arr.length; i++) {
//		 System.out.println(i + "��" + arr[i]);
//		 }
//		//&#x65e0;
//		StringUtil su=new StringUtil();
//		
//		String aa=su.AddPrint();
//		System.out.println(aa);
//		System.out.println("<card id=\"main\" title=\"<%out.print(new String(\"welcome\".getBytes(\"8859_1\")));%>\" <%=missFeeId%>>");
//		String filePath="http://192.168.16.150/solaradmin/hp.jsp";
//		String searchPath="";
//		int index=StringUtil.indexOf(filePath,"/",3);
//		//System.out.println(index);
//		System.out.println(filePath.substring(index+1,filePath.lastIndexOf("/")+1));
//		if(filePath.toLowerCase().indexOf("root")>-1){
//			searchPath=filePath.substring(filePath.indexOf("root/")+5);
//		}
//		else{
//			searchPath=filePath.substring(filePath.indexOf("webapps/")+8);
//		}
		//System.out.println(rightTrim("  asss dfse sdfe          dfff   "));
		
		//System.out.println(StringUtil.getKeyStr("../../file/img/0/20080523103156985.gif","/img/","end",false));
	}

}