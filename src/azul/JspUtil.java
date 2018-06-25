package azul;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class JspUtil {
	
	public static String getStr(Object obj) {
		try {
			if (obj != null) {
				return obj.toString();
			}
		} catch (Exception e) {
			System.out.println("JspUtil.getStr(Object,String) error:str is " + obj + " wrong is:" + e);
		}
		return "";
	}
	
	// 得到字符串,如果为null则得到默认值。一般用于从request.getParameter();得到参数
	public static String getStr(Object obj, String defStr) {
		try {
			if (obj != null) {
				return obj.toString();
			}
		} catch (Exception e) {
			System.out.println("JspUtil.getStr(Object,String) error:str is " + obj);
			e.printStackTrace();
		}
		return defStr.trim();
	}

	public static int getInt(Object obj) {
		try {
			if (obj != null && !"".equals(obj.toString())) {
				return Integer.valueOf(obj.toString());
			}
		} catch (Exception e) {
			System.out.println("JspUtil.getInt(Object) error:obj is " + obj);
			e.printStackTrace();
		}
		return 0;
	}
	
	// 得到int,如果为null则得到默认值
	public static int getInt(Object obj, int def) {
		try {
			if (obj != null && !"".equals(obj.toString())) {
				return Integer.valueOf(obj.toString());
			}
		} catch (Exception e) {
			System.out.println("JspUtil.getInt(Object,int) error:obj is " + obj + " wrong is:" + e);
			e.printStackTrace();
		}
		return def;
	}

	// 从页面得到普通编码字符串转成的数字类型
	public static double getDouble(Object obj,double def) {
		try {
			if (obj != null  && !"".equals(obj.toString())) {
				return (Double)obj;
			}
		} catch (Exception e) {
			System.out.println("azul.JspUtil.getDouble error:obj is " + obj + " wrong is:" + e);
		}
		return def;
	}
	
	//jsp页面uft-8使用ie6传递中文经常有乱码问题，先使用这个方法编码然后解码可解决
	//由于部分变量不知道是否原本就为空，所以在此做空检查
	//传递页面为js的话使用sendText=encodeURI(sendText);，然后解析页面使用sendText=java.net.URLDecoder.decode(sendText,"utf-8");
    public static String decode(String content){
    	if("".equals(content)){
    		return "";
    	}
    	String result="";
    	try {
    		result=String.valueOf(new java.math.BigInteger(content.getBytes()));
		} catch (Exception e) {
			System.out.println("JspUtil.decode error:"+content);
		}
		return result;
    }
    
    //对应上面方法的解码方式
    public static String undecode(String content){
    	if("".equals(content)){
    		return "";
    	}
    	String result="";
    	try {
    		result=new String(new java.math.BigInteger(content).toByteArray());
		} catch (Exception e) {
			System.out.println("JspUtil.undecode error:"+content);
		}
		return result;
    }
	
    //对应实体的类型必须用对应的包装类。不然get方法返回的对象不会为空导致修改操作异常
	@SuppressWarnings("unchecked")
	public static void populate(Object obj,HttpServletRequest request){
        try{
        	if(obj == null)return;
        	HashMap map = new HashMap();
        	Enumeration names = request.getParameterNames(); 
        	while (names.hasMoreElements()){
        	     String name = (String) names.nextElement();
        	     //System.out.println("name:"+name);
        		 map.put(name, request.getParameter(name));
        	}    
        	if(map == null)return;
	        Class cls=obj.getClass();
	        Method[] methods = cls.getDeclaredMethods();
	        Iterator entries = map.entrySet().iterator();
	        do{
	            if(!entries.hasNext())
	                break;
	            java.util.Map.Entry entry = (java.util.Map.Entry)entries.next();
	            String name = (String)entry.getKey();
	            String value = (String)entry.getValue();
	            if(name != null && value!=null){
	            	String tempName="set"+upCaseFirst(chargeStr(name));
	            	for (Method method : methods) {
	    				String methodName = method.getName();
	            	    if(methodName.equals(tempName)){
	            	        Class[] paramTypes=method.getParameterTypes();
	            	        String typeName=paramTypes[0].getName();
	            	        //System.out.println(tempName+"  "+value+"   "+typeName);
	            	        if(typeName.equals("java.lang.Integer")){
	            	        	method.invoke(obj, Integer.valueOf(value));
	            	        }
	            	        else if(typeName.equals("java.lang.Double")){
	            	        	method.invoke(obj, Double.valueOf(value));
	            	        }
	            	        else{
	            	        	method.invoke(obj, value.trim());
	            	        }
	    	            	break;
	            	    }
	            	}
	            } 
	        } while(true);
        }
        catch(Exception e){
        	System.out.println("azul.JspUtil.populate error");
        	System.out.println(e);
        }
	}
	
	// 将数据库中以"_"连接的字段名称改变成以大小写连接的字符串,主要供populate调用
	public static String chargeStr(String str) {
		String temp = str.toLowerCase();
		if (temp.indexOf("_") == -1) {
			return temp;
		}
		String[] arr = temp.split("_");
		StringBuffer strSb = new StringBuffer(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			strSb.append(arr[i].substring(0, 1).toUpperCase());
			strSb.append(arr[i].substring(1, arr[i].length()));
		}
		return strSb.toString();
	}
	
	// 将字符串首字母改成大写,主要供populate调用
	public static String upCaseFirst(String str) {
		String result = "";
		result = str.substring(0, 1).toUpperCase()
				+ str.substring(1, str.length());
		return result;
	}
	
	// 将字符长出的部分字符使用...代替，一般用于截断过长的字符串省略显示
	public static String subTxt(String str, int length) {
		if(str==null){
			return "";
		}
		String result = "";
		try {
			int index=0;
			int tempIndex=0;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) > 127) {
					tempIndex += 2;
				} else {
					tempIndex += 1;
				}
				if (length<tempIndex) {
					index=i;
					break;
				}			
			}
			if(index!=0){
				result = str.substring(0,index)+"...";
			}
			else{
				result=str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("azul.JspUtil.replace(String,int) error:arg is:" + str + " length is:" + length);
		}
		return result;
	}
	
	// 将字符长出的部分字符在有特殊符号的地方换行，一般用于过长的字符串分行显示
	public static String rowTxt(String str, String key,int length) {
		if(str==null){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		try {
			int index=0;
			String[] arr=str.split(key);
			for (int i = 0; i < arr.length; i++) {
				if(index==length){
					index=0;
					sb.append("<br>");
				}
				sb.append(arr[i]);
				sb.append(key);
				index++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("azul.JspUtil.rowTxt() error:arg is:" + str + "key="+key+" length=" + length);
		}
		return sb.toString();
	}
	
	//列表中数字序列转换为对应属性文字显示
	@SuppressWarnings("unchecked")
	public static String getTitle(String[] arr,Object key){
		if(key==null || "".equals(key.toString())){
			return "";
		}
		String result="";
		String value=key.toString();
		try {
			if(value.indexOf(",")>-1){
				String[] tempArr=value.split(",");
				StringBuffer sb=new StringBuffer("");
				for (int i = 0; i < tempArr.length; i++) {
					if(!"".equals(tempArr[i])){
						int tempInt=Integer.valueOf(tempArr[i]);
						if(tempInt<arr.length){
							sb.append(arr[tempInt]);
							sb.append(",");
						}
					}
				}
				result=sb.toString();
			}
			else{
				 int temp=Integer.valueOf(value);
				 if(temp<arr.length){
					 result=arr[temp];
				 }
			}
			//如果没有找到匹配对象就设置为原始值
			if("".equals(result)){
				result=value;
			}
		} catch (Exception e) {
			System.out.println("JspUtil.getTitle error:"+e);
			p(arr);
			System.out.println("key="+key);
		}
		return result;
	}
	
	//页面匹配类型时使用的方法,如果没有对应的值就设置为空，避免页面出现空指针异常
	@SuppressWarnings("unchecked")
	public static String getLink(Object obj,Map map){
		Object result=null;
		if(obj==null){
			return "";
		}
		if(map.containsKey(obj)){
			result=map.get(obj);
		}
		else if(obj instanceof Integer){
			String temp=String.valueOf(obj);
			if(map.containsKey(temp)){
				result=map.get(temp);
			}
			else{
				result=obj.toString();
			}
		}
		else if(result==null){
			result=obj.toString();
		}
		return result.toString();
	}
	
	//生成填充jsp页面下拉框的javascript代码,并使用初始值初始化。如果obj是null 或者为0，-1就不选择某一项
	@SuppressWarnings("unchecked")
	public static String initSelectSortByValuePinyin(String selectName,Map map,Object obj){
		return initSelect(util.MapUtil.sortByValuePinyin(map), selectName, map, obj);
		/*StringBuffer sb=new StringBuffer("");
		try {
			sb.append("var ");
			sb.append(selectName);
			sb.append("Key=new Array();\n");
			sb.append("var ");
			sb.append(selectName);
			sb.append("Value=new Array();\n");
			//对父下拉框进行排序,然后组装输出语句
			Map.Entry[] entries=util.MapUtil.sortByValuePinyin(map);
	        for (int i=0;i<entries.length;i++){
		       Object keyId=entries[i].getKey();
		       Object value=map.get(keyId);
		       sb.append(selectName);
			   sb.append("Key[");
			   sb.append(i);
			   sb.append("] = \"");
			   sb.append(keyId);
			   sb.append("\";\n");
			   sb.append(selectName);
			   sb.append("Value[");
			   sb.append(i);
			   sb.append("] = \"");
			   sb.append(value);
			   sb.append("\";\n");
		    }
		    sb.append("fillSelect(\"");
		    sb.append(selectName);
		    sb.append("\", ");
		    sb.append(selectName);
		    sb.append("Key, ");
		    sb.append(selectName);
		    sb.append("Value");
		    if(obj!= null){
		    	sb.append(",\"");
		    	sb.append(obj);
		    	sb.append("\"");
		    }
		    sb.append(");\n");
		} catch (Exception e) {
			System.out.println("azul.JspUtil.initSelect(String,Map,Object) error"+e);
		}
	    return sb.toString();*/
	}
	//生成填充jsp页面下拉框的javascript代码,并使用初始值初始化。如果obj是null 或者为0，-1就不选择某一项
	@SuppressWarnings("unchecked")
	public static String initSelect(String selectName,Map map,Object obj){
		return initSelect(util.MapUtil.sortByKey(map), selectName, map, obj);
		/*StringBuffer sb=new StringBuffer("");
		try {
			sb.append("var ");
			sb.append(selectName);
			sb.append("Key=new Array();\n");
			sb.append("var ");
			sb.append(selectName);
			sb.append("Value=new Array();\n");
			//对父下拉框进行排序,然后组装输出语句
			Map.Entry[] entries=util.MapUtil.sortByKey(map);
	        for (int i=0;i<entries.length;i++){
		       Object keyId=entries[i].getKey();
		       Object value=map.get(keyId);
		       sb.append(selectName);
			   sb.append("Key[");
			   sb.append(i);
			   sb.append("] = \"");
			   sb.append(keyId);
			   sb.append("\";\n");
			   sb.append(selectName);
			   sb.append("Value[");
			   sb.append(i);
			   sb.append("] = \"");
			   sb.append(value);
			   sb.append("\";\n");
		    }
		    sb.append("fillSelect(\"");
		    sb.append(selectName);
		    sb.append("\", ");
		    sb.append(selectName);
		    sb.append("Key, ");
		    sb.append(selectName);
		    sb.append("Value");
		    if(obj!= null){
		    	sb.append(",\"");
		    	sb.append(obj);
		    	sb.append("\"");
		    }
		    sb.append(");\n");
		} catch (Exception e) {
			System.out.println("azul.JspUtil.initSelect(String,Map,Object) error"+e);
		}
	    return sb.toString();*/
	}
	
	public static String initSelect(Map.Entry[] entries,String selectName,Map map,Object obj){
		StringBuffer sb=new StringBuffer("");
		try {
			sb.append("var ");
			sb.append(selectName);
			sb.append("Key=new Array();\n");
			sb.append("var ");
			sb.append(selectName);
			sb.append("Value=new Array();\n");
			//对父下拉框进行排序,然后组装输出语句
			//Map.Entry[] entries=util.MapUtil.sortByKey(map);
	        for (int i=0;i<entries.length;i++){
		       Object keyId=entries[i].getKey();
		       Object value=map.get(keyId);
		       sb.append(selectName);
			   sb.append("Key[");
			   sb.append(i);
			   sb.append("] = \"");
			   sb.append(keyId);
			   sb.append("\";\n");
			   sb.append(selectName);
			   sb.append("Value[");
			   sb.append(i);
			   sb.append("] = \"");
			   sb.append(value);
			   sb.append("\";\n");
		    }
		    sb.append("fillSelect(\"");
		    sb.append(selectName);
		    sb.append("\", ");
		    sb.append(selectName);
		    sb.append("Key, ");
		    sb.append(selectName);
		    sb.append("Value");
		    if(obj!= null){
		    	sb.append(",\"");
		    	sb.append(obj);
		    	sb.append("\"");
		    }
		    sb.append(");\n");
		} catch (Exception e) {
			System.out.println("azul.JspUtil.initSelect(String,Map,Object) error"+e);
		}
	    return sb.toString();
	}
	
	//生成填充jsp页面关联下拉框的javascript代码,并使用初始值初始化。如果obj是null 或者为0，-1就不选择某一项
	//因为在子表中有键，值，关联项所以子表记录以objectList形式返回
	@SuppressWarnings("unchecked")
	public static String initSelectGroup(String selectParent,String selectSon,Map<String, String> mapParent,List listSon,Object objParent,Object objSon){
		StringBuffer sb=new StringBuffer("");
		try {
			sb.append("var arrParentID_").append(selectParent).append("= new Array();\n");
			sb.append("var arrParentName_").append(selectParent).append("= new Array();\n");
			sb.append("var arrLinkID_").append(selectParent).append("= new Array();\n");
			sb.append("var arrSonID_").append(selectParent).append("= new Array();\n");
			sb.append("var arrSonName_").append(selectParent).append("= new Array();\n");
			//对父下拉框进行排序,然后组装输出语句
			Map.Entry[] entries=util.MapUtil.sortByKey(mapParent);
			for (int i=0;i<entries.length;i++){
			       Object keyId=entries[i].getKey();
			       Object value=mapParent.get(keyId);
			       sb.append("arrParentID_").append(selectParent).append("[").append(i).append("]=\"").append(keyId).append("\";\n");
				  sb.append("arrParentName_").append(selectParent).append("[").append(i).append("]=\"").append(value).append("\";\n");
			}
			for(int j=0;j<listSon.size();j++){
			    List tempList=(List)listSon.get(j);  
				String keyId = (String)tempList.get(0);
				String value = (String)tempList.get(1);
				String link =(String)tempList.get(2);
				sb.append("arrSonID_").append(selectParent).append("[").append(j).append("]=\"").append(keyId).append("\";\n");
				sb.append("arrSonName_").append(selectParent).append("[").append(j).append("]=\"").append(value).append("\";\n");
				sb.append("arrLinkID_").append(selectParent).append("[").append(j).append("]=\"").append(link).append("\";\n");
			}
			sb.append("initSelectGroup('").append(selectParent).append("','").append(selectSon).append("',").append("arrParentID_").append(selectParent).append(",arrParentName_").append(selectParent).append(",arrLinkID_").append(selectParent).append(",arrSonID_").append(selectParent).append(",arrSonName_").append(selectParent).append(",'").append(objParent).append("','").append(objSon).append("');\n");
			//动态添加父组件改变时子组件自动改变的事件
			//如果页面上存在多个两级关联最后一组会把前面的冲掉，所以需要采用另外的变量selectParent来改变
			sb.append("var _js_temp_select_").append(selectParent).append("=document.getElementById(\"").append(selectParent).append("\");\n");
			sb.append("if (document.all){\n");
			sb.append("	_js_temp_select_").append(selectParent).append(".attachEvent('onclick',function _js_select_change_").append(selectParent).append("(){changeSelectGroup('").append(selectParent).append("','").append(selectSon).append("',arrLinkID_").append(selectParent).append(",arrSonID_").append(selectParent).append(",arrSonName_").append(selectParent).append(");});\n");
			sb.append("}\n");
			sb.append("else{\n");
			sb.append("	_js_temp_select_").append(selectParent).append(".addEventListener('click',function _js_select_charnge_").append(selectParent).append("(){changeSelectGroup('").append(selectParent).append("','").append(selectSon).append("',arrLinkID_").append(selectParent).append(",arrSonID_").append(selectParent).append(",arrSonName_").append(selectParent).append(");},false);\n");
			sb.append("}\n");
		} catch (Exception e) {
			System.out.println("azul.JspUtil.initSelect(String,Map,Object) error "+e);
			e.printStackTrace();
		}
	    return sb.toString();
	}
	
	// 截取字符串包括到key位置的字符
	public static String getKeyStr(String str, String key) {
		String result = "";
		try {
			int index = str.indexOf(key);
			if (index == -1) {
				return str;
			} else {
				result = str.substring(0, index + key.length());
			}
		} catch (Exception e) {
			System.out.println("azul.JspUtil.getKeyStr error:str is " + str + "wrong is:" + e);
		}
		return result;
	}

	// 截取字符串包括到key位置的字符
	public static String getKeyEnd(String str, String key, boolean include) {
		String result = "";
		try {
			int index = str.indexOf(key);
			if (index == -1) {
				return str;
			} else {
				if (include) {
					result = str.substring(index);
				} else {
					result = str.substring(index + key.length());
				}
			}
		} catch (Exception e) {
			System.out.println("azul.JspUtil.getKeyStr error:str is " + str + "wrong is:" + e);
		}
		return result;
	}
	
	// 四舍五入格式化数字，显示小数点后2位
	public static String toMoney(Object money) {
		String result = "";
		try {
			if (money instanceof String) {
				String temp = money.toString().trim();
				final String number = "1234567890.";
				boolean isNumber = true;
				for (int i = 0; i < temp.length(); i++) {
					if (number.indexOf(temp.charAt(i)) == -1) {
						isNumber = false;
					}
				}
				if (isNumber) {
					java.text.DecimalFormat decFormat = new java.text.DecimalFormat("##0.00");
					result = decFormat.format(Double.valueOf(temp));
				} else {
					System.out.println("azul.JspUtil.toMoney error:arg is:String:" + money);
				}
			} else if (money instanceof Double || money instanceof Integer || money instanceof Float || money instanceof java.math.BigDecimal) {
				java.text.DecimalFormat decFormat = new java.text.DecimalFormat("##0.00");
				result = decFormat.format(money);
			} else {
				System.err.println("输入数据格式不是数字型:" + money);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("azul.JspUtil.toMoney error:arg is:" + money);
		}
		return result;
	}

	// 将字符串前后 的子字符串去掉，如:ZvZ1ZvZ2ZvZ3ZvZ换成1,2,3 兼容前后不带有subStr的输入
	public static String replace(String str, String subStr, String replaceStr) {
		String result = str;
		try {
			if (str.indexOf(subStr) == 0) {
				result = str.substring(subStr.length(), str.length());
			}
			if (result.lastIndexOf(subStr) == result.length() - subStr.length()) {
				result = result.substring(0, result.length() - subStr.length());
			}
			result = result.replace(subStr, replaceStr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("azul.JspUtil.replace error:arg is:" + str + " subStr is:" + subStr);
		}
		return result;
	}
	
	// 因为split函数不能处理"111,222,333,"这样的情况，取出的内容会少一个元素。所以需要特殊处理
	// 检查字符串是否以key结尾,如果是则直接将split得到的数组后面加一个""元素
	public static String[] split(String str, String key) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String tempStr = str;
			int pos = 0;
			while (true) {
				int index = tempStr.indexOf(key);
				// 如果索引在串中不存在了,就将最后一截加入list,退出循环
				if (index == -1) {
					list.add(tempStr);
					break;
				}
				String inStr = tempStr.substring(pos, index);
				tempStr = tempStr.substring(index + key.length());
				list.add(inStr);
			}
		} catch (Exception e) {
			System.out.println("azul.JspUtil.p error:arg is " + str + " " + e);
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	//得到需要截取的字符串，不包括分割符.如果以分隔符开头则pos=0时得到"",如果以分隔符结尾，则会忽略这个分隔符
	//字符串\\25:=C:\\Documents and Settings\\Administrator\\桌面\\MID铃声\\外婆(周杰伦火热专辑)\\720.mid\\想要得到存放音乐的文件夹名称 使用
	//substring(str, "\\\\", -2)
	//特殊key "\"="\\\\" , "."="\\." ,"/" 还是"/"
	public static String substring(String str,String key,int pos){
		String result = "";
		try {
			String[] arr=str.split(key);
			if(arr.length<2){
				throw new Exception("JspUtil.substring error:split arr.length <2");
			}
			else if(pos>0 && arr.length<pos){
				throw new Exception("JspUtil.substring error:split arr.length <"+pos);
			}
			else if(pos<0 && arr.length<Math.abs(pos)){
				throw new Exception("JspUtil.substring error:split arr.length <"+pos);
			}
			else{
				if(pos>=0){
					return arr[pos];
				}
				else{
					return arr[arr.length+pos];
				}
			}
		} catch (Exception e) {
			System.out.println("JspUtil.substring error:str=" + str + "key=" + key+"  pos="+pos+"  e:"+e);
		}
		return result;
	}
	
	// 根据数据中id的序列得到对应value值的序列，如输入字符串为2，3，4，5将得到对应这些key值的value字符串，一般用于由编码得到对应的中文名称
	@SuppressWarnings("unchecked")
	public static String getParamValue( String idStr,Map<String,String> map) {
		StringBuffer sb = new StringBuffer();
		try {
			if ("".equals(idStr) || map == null) {
				return "";
			}
			Map tempMap=new HashMap();
			for (Map.Entry<String, String> entry : map.entrySet()) {   
				String keyId = entry.getKey();
				String value = entry.getValue();
				tempMap.put(keyId.toString(),value.toString());
	        }   
			/*
			Iterator<Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> entry = it.next();  
				Object keyId = entry.getKey();
				Object value = entry.getValue();
				tempMap.put(keyId.toString(),value.toString());
			}
			*/
			String[] idArr = idStr.split(",");
			for (int i = 0; i < idArr.length; i++) {
				if (tempMap.containsKey(idArr[i])) {
					sb.append(tempMap.get(idArr[i]));
					if (i != idArr.length - 1) {
						sb.append(",");
					}
				}
			}
		} catch (Exception e) {
			System.out.println("azul.JspUtil.getParamValue error:arg is :" + e);
		}
		return sb.toString();
	}

	// 得到当前运行的服务器地址,如http://192.168.1.180:8080/show/
	public static String getHttp(HttpServletRequest request) {
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
	}

	// 得到当前运行应用的服务器的根目录物理地址
	// d:\tomcat5\webapps\solaradmin\ 形式的目录
	public static String getPath(PageContext pageContext) {
		return pageContext.getServletContext().getRealPath("/");
	}

	//直接使用getRemoteAddr方法在经过apache转向时得到ip是错误的
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	// 取得翻页信息的具体代码
	public static String getPageCode(int pageno, int pagesize, int recordcount, HttpServletRequest request, String linkStr) {
		StringBuffer html = new StringBuffer("");
		int pageCount = (recordcount + pagesize - 1) / pagesize;
		try {
			// 记录为空时就直接返回没有翻页的情形
			if (recordcount == 0) {
				return "";
			}
			//  找到翻页连接的页面名称
			String pageName=request.getServletPath();
			pageName=pageName.substring(pageName.lastIndexOf("/")+1);
			// 默认linkStr应该是加有"&"符号的,如果没有加就直接帮加上
			if (!"".equals(linkStr) && !"&".equals(String.valueOf(linkStr.charAt(0)))) {
				linkStr = "&" + linkStr;
			}
			//解决ie6下中文参数传递问题
			//linkStr="encodeURIComponent("+linkStr+")";
			html.append("<!-公用翻页代码,由调用bean自动产生翻页代码-->\n");
			html.append("<div align=\"center\">\n");
			if (pageno > 1) {
				html.append("<a href=\"");
				html.append(pageName);
				html.append("?pageno=1&recordcount=");
				html.append(recordcount + linkStr);
				html.append("\">首页</a>&nbsp;&nbsp;\n");
				html.append("<a href=\"");
				html.append(pageName);
				html.append("?pageno=");
				html.append(pageno - 1);
				html.append("&recordcount=");
				html.append(recordcount);
				html.append(linkStr);
				html.append("\">上页</a>&nbsp;&nbsp;\n");
			} else {
				html.append("<font color=#999999>首页</font>&nbsp;&nbsp;<font color=#999999>上页</font>&nbsp;&nbsp;\n");
			}
			if (pageno < pageCount) {
				html.append("<a href=\"");
				html.append(pageName);
				html.append("?pageno=");
				html.append(pageno + 1);
				html.append("&recordcount=");
				html.append(recordcount);
				html.append(linkStr);
				html.append("\">下页</a>&nbsp;&nbsp;\n");
				html.append("<a href=\"");
				html.append(pageName);
				html.append("?pageno=");
				html.append(pageCount);
				html.append("&recordcount=");
				html.append(recordcount + linkStr);
				html.append("\">尾页</a>&nbsp;&nbsp;\n");
			} else {
				html.append("<font color=#999999>下页</font>&nbsp;&nbsp;<font color=#999999>尾页</font>&nbsp;&nbsp;\n");
			}
			html.append("跳转到第 <select id=\"elemToPage\" onChange=\"elemGoToPage()\">\n");
			for (int elemV = 1; elemV < pageCount + 1; elemV++) {
				html.append("<option value=\"");
				html.append(elemV);
				html.append("\">");
				html.append(elemV);
				html.append("</option>\n");
			}
			html.append("</select>" + "\n");
			html.append("页&nbsp;&nbsp;共<font color=\"#FF0000\">");
			html.append(recordcount);
			html.append("</font>条记录&nbsp;&nbsp;本页为<font color=\"#FF0000\">");
			html.append(pageno);
			html.append("</font>页&nbsp;&nbsp;共<font color=\"#FF0000\">");
			html.append(pageCount);
			html.append("</font>页\n");
			html.append("<script>\n");
			html.append("function elemGoToPage(){\n");
			html.append("var elem=document.getElementById(\"elemToPage\");\n");
			html.append("var pageNo=elem.options[elem.selectedIndex].value;\n");
			html.append("location=\"");
			html.append(pageName);
			html.append("?pageno=\"+pageNo+\"");
			html.append(linkStr);
			html.append("\";\n");
			html.append("}\n");
			html.append("if('");
			html.append(pageno);
			html.append("'!='1'){\n");
			html.append("document.getElementById(\"elemToPage\")[");
			html.append(pageno - 1);
			html.append("].selected=true;\n");
			html.append("}\n");
			html.append("</script>\n");
			html.append("</div>\n");
		} catch (Exception e) {
			System.out.println("页面索引:" + pageno);
			System.out.println("页面大小:" + pagesize);
			System.out.println("页面总数:" + pageCount);
			System.out.println("记录总数:" + recordcount);
			System.out.println("传递参数:" + linkStr);
			e.printStackTrace();
		}
		return html.toString();
	}
	
	//jsp页面得到某个时间段的开始结束时间
	//type=month当月时间;type=week当周时间;type=day当日时间
	public static String[] getDateDay(String startDate,String endDate){
		String[] arr=new String[]{startDate,endDate};
		if("".equals(startDate)){
			arr[0]=util.DateUtil.getDate();
			arr[1]=arr[0];
		}
		return arr;
	}
	
	//得到本周开始到今天的日期时间
	public static String[] getDateWeek(String startDate,String endDate){
		String[] arr=new String[]{startDate,endDate};
		if("".equals(startDate)){
			//得到的开始时间是上月数据就使用本月开头
			Date begWeek=util.DateUtil.getFirstDayWeek(new Date());
			Date begMonth=util.DateUtil.getFirstDayMonth(new Date());
			//System.out.println(date);System.out.println(util.DateUtil.getFirstDayMonth(new Date()));
			//本周起始日期比本月起始日期小，就是用本月起始日期
			if(begMonth.after(begWeek)){
				arr[0]=util.DateUtil.getDate(begMonth,util.DateUtil.DATE_A);
			}
			else{
				arr[0]=util.DateUtil.getDate(begWeek,util.DateUtil.DATE_A);
			}
			//本周结束日期比本月结束日期大,就是用本月结束日期
			Date endWeek=util.DateUtil.getLastDayWeek(new Date());
			Date endMonth=util.DateUtil.getLastDayMonth(new Date());
			if(endWeek.after(new Date())){
				arr[1]=util.DateUtil.getDate(new Date(),util.DateUtil.DATE_A);
			}
			else if(endWeek.after(endMonth)){
				arr[1]=util.DateUtil.getDate(endMonth,util.DateUtil.DATE_A);
			}
			else{
				arr[1]=util.DateUtil.getDate(endWeek,util.DateUtil.DATE_A);
			}
		}
		return arr;
	}
	
	//找出当月开始日期到当日
	public static String[] getDateMonth(String startDate,String endDate){
		String[] arr=new String[]{startDate,endDate};
		if("".equals(startDate)){
			arr[0]=util.DateUtil.getDate(util.DateUtil.getFirstDayMonth(new Date()),util.DateUtil.DATE_A);
			arr[1]=util.DateUtil.getDate(new Date(),util.DateUtil.DATE_A);
		}
		return arr;
	}

	//页面组装sql语句的条件,只支持全部and连接。特殊查询需自行编写
	//appendWhere(conditionStrSB,"cid",cid,"=");
	//appendWhere(conditionStrSB,"param",param,"like");
	//appendWhere(conditionStrSB,"param",param,"likes");
	//appendWhere(conditionStrSB,"date_time",startDate,">=");
	//appendWhere(conditionStrSB,"date_time",endDate,"<=");
	public static void appendWhere(StringBuffer conditionStrSB,String key,Object obj,String condition){
		String value=String.valueOf(obj).trim();
		if(obj instanceof String && !"".equals(value)){
			conditionStrSB.append(" and ");
			if("like".equals(condition)){
				conditionStrSB.append(key).append(" like '").append(value).append("%'");
			
			}
			else if("likes".equals(condition)){
				conditionStrSB.append(key).append(" like '%").append(value).append("%'");
			}
			else{
				if("date_time".equals(key)){
					//有可能搜索条件中有date_time,所以加上前缀判断
					if(conditionStrSB.indexOf("and date_time")>-1){
						conditionStrSB.append(key).append("<='").append(value).append(" 23:59:59'");
					}
					else{
						conditionStrSB.append(key).append(">='").append(value).append("'");
					}
				}
				else{
					conditionStrSB.append(key).append(condition).append("'").append(value).append("'");
				}
			}
		}
		else if(obj instanceof Integer && !"0".equals(value) && !"-1".equals(value)){
			conditionStrSB.append(" and ").append(key).append("=").append(value);
		}
	}
	
	//页面组装sql语句的条件,同时填充翻页使用参数
	public static void appendWhere(StringBuffer paramSB,StringBuffer conditionStrSB,String key,Object obj,String condition){
		appendWhere(conditionStrSB,key,obj,condition);
		paramSB.append("&").append(key).append("=").append(obj.toString());
	}
	
	//页面组装sql语句的条件
	//key 操作字段 obj 检查是否是空变量 def 比较的默认值 condition 条件 param 参数
	//appendWhere("cid",cid,"","=","");
	//appendWhere("param",param,"","like","");
	//appendWhere("date_time",startDate,"",">=","");
	//appendWhere("date_time",endDate,"","<="," 23:59:59");
	//如果需要模糊参数查询，输入c=012||t=001  c=012&&t=001不支持&&和||同时查询
	//appendWhere(paramSB,conditionStrSB,"param",keyWord,"","like",""); 
	public static void appendWhereAA(StringBuffer paramSB,StringBuffer conditionStrSB,String key,Object obj,String def,String condition,String param){
		if(obj instanceof String){
			String value=String.valueOf(obj).trim();
			if (!def.equals(value)){
				//判断参数是否带有&&或者||，如果带有则表示需要支持模糊查询
				 if(value.indexOf("&&")>0) {
					String[] arr=value.split("\\&\\&");
					conditionStrSB.append(" and ");
					for (int i = 0; i < arr.length; i++) {
						conditionStrSB.append(key);
						if("like".equals(condition)){
							conditionStrSB.append(" like '%");
							conditionStrSB.append(arr[i]);
							conditionStrSB.append(param);
							conditionStrSB.append("%'");
						}
						else{
							conditionStrSB.append(condition);
							conditionStrSB.append("'");
							conditionStrSB.append(arr[i]);
							conditionStrSB.append(param);
							conditionStrSB.append("'");
						}
						if(i!=arr.length-1) {
							conditionStrSB.append(" and ");
						}
					}
				}
				else if(value.indexOf("||")>0) {
					String[] arr=value.split("\\|\\|");
					conditionStrSB.append(" and (");
					for (int i = 0; i < arr.length; i++) {
						conditionStrSB.append(key);
						if("like".equals(condition)){
							conditionStrSB.append(" like '%");
							conditionStrSB.append(arr[i]);
							conditionStrSB.append(param);
							conditionStrSB.append("%'");
						}
						else{
							conditionStrSB.append(condition);
							conditionStrSB.append("'");
							conditionStrSB.append(arr[i]);
							conditionStrSB.append(param);
							conditionStrSB.append("'");
						}
						if(i!=arr.length-1) {
							conditionStrSB.append(" or ");
						}
					}
					conditionStrSB.append(")");
				}
				//不需要支持模糊查询
				else {
					conditionStrSB.append(" and ");
					conditionStrSB.append(key);
					if("like".equals(condition)){
						conditionStrSB.append(" like '%");
						conditionStrSB.append(value);
						conditionStrSB.append(param);
						conditionStrSB.append("%'");
					}
					else{
						conditionStrSB.append(condition);
						conditionStrSB.append("'");
						conditionStrSB.append(value);
						conditionStrSB.append(param);
						conditionStrSB.append("'");
					}
					if("date_time".equals(key)){
						if(paramSB.indexOf("startDate")==-1){
							paramSB.append("&startDate=");
							paramSB.append(value);
						}
						else{
							paramSB.append("&endDate=");
							paramSB.append(value);
						}
					}
					else{
						paramSB.append("&");
						paramSB.append(key);
						paramSB.append("=");
						paramSB.append(value);
					}
				}
			}	
		}
		else if(obj instanceof Integer){
			if (!def.equals(obj.toString())){
				conditionStrSB.append(" and ");
				conditionStrSB.append(key);
				conditionStrSB.append("=");
				conditionStrSB.append(obj.toString());
				paramSB.append("&");
				paramSB.append(key);
				paramSB.append("=");
				paramSB.append(obj.toString());
			}
		}
	}
	
	//组装带or条件的语句 key查询的字段 value 配置值
	//azul.JspUtil.appendWhere(paramSB,conditionStrSB,new String[]{"imsi","mobile"},new String[]{keyWord,keyWord});
	public static void appendWhereAA(StringBuffer paramSB,StringBuffer conditionStrSB,String[] key,String[] value){
		StringBuffer tempSb=new StringBuffer();
		for (int i = 0; i < key.length; i++) {
			if (!"".equals(value[i])){
				tempSb.append(key[i]);
				tempSb.append(" like '%");
				tempSb.append(value[i]);
				tempSb.append("%'");
				tempSb.append(" or ");
			}
		}
		if(tempSb.length()>1) {
			String tempString=tempSb.toString();
			conditionStrSB.append(" and (");
			conditionStrSB.append(tempString.substring(0,tempString.length()-4));
			conditionStrSB.append(")");
		}
		
		for (int i = 0; i < key.length; i++) {
			if (!"".equals(value[i])){
				paramSB.append("&");
				paramSB.append(key[i]);
				paramSB.append("=");
				paramSB.append(value[i]);
			}
		}
	}
	
	//组装排序语句
	//azul.JspUtil.appendOrder(paramSB,conditionStrSB,sort_by,sort_order,"date_time","desc");
	public static void appendOrder(StringBuffer conditionStrSB,String order_by,String sort_order,String def_order_by,String def_sort_order){
		if (!"".equals(order_by)) {
			conditionStrSB.append(" order by ");
			conditionStrSB.append(order_by);
			if(!"".equals(sort_order)){
				conditionStrSB.append(" ");
				conditionStrSB.append(sort_order);
			}
		}
		else if(!"".equals(def_order_by)){
			conditionStrSB.append(" order by ");
			conditionStrSB.append(def_order_by);
			if(!"".equals(def_sort_order)){
				conditionStrSB.append(" ");
				conditionStrSB.append(def_sort_order);
			}
		}
	}
	
	//组装排序语句
	//azul.JspUtil.appendOrder(paramSB,conditionStrSB,sort_by,sort_order,"date_time","desc");
	public static void appendOrder(StringBuffer paramSB,StringBuffer conditionStrSB,String order_by,String sort_order,String def_order_by,String def_sort_order){
		if (!"".equals(order_by)) {
			conditionStrSB.append(" order by ");
			conditionStrSB.append(order_by);
			if(!"".equals(sort_order)){
				conditionStrSB.append(" ");
				conditionStrSB.append(sort_order);
			}
			paramSB.append("&sort_by");
			paramSB.append("=");
			paramSB.append(order_by);
			if(!"".equals(sort_order)){
				paramSB.append("&sort_order=");
				paramSB.append(sort_order);
			}
		}
		else if(!"".equals(def_order_by)){
			conditionStrSB.append(" order by ");
			conditionStrSB.append(def_order_by);
			if(!"".equals(def_sort_order)){
				conditionStrSB.append(" ");
				conditionStrSB.append(def_sort_order);
			}
			paramSB.append("&sort_by");
			paramSB.append("=");
			paramSB.append(def_order_by);
			if(!"".equals(def_sort_order)){
				paramSB.append("&sort_order=");
				paramSB.append(def_sort_order);
			}
		}
	}
	
	//得到由于分表所需要查询的真实表名
	public static String getTableName(String tableName,String startDate){
		java.util.Calendar begCal=java.util.Calendar.getInstance();
		java.util.Calendar endCal=java.util.Calendar.getInstance();
		Date tempDate=new java.util.Date();
		if(!"".equals(startDate)){
			tempDate=util.DateUtil.strToDate(startDate);
		}
		begCal.setTime(tempDate); 
		if(begCal.get(java.util.Calendar.YEAR)*100+begCal.get(java.util.Calendar.MONTH)<endCal.get(java.util.Calendar.YEAR)*100+endCal.get(java.util.Calendar.MONTH)){
			tableName=tableName+"_"+startDate.substring(0,7).replace("-","_");
		}
		return tableName;
	}
	
	//用于打印系统部分访问记录到一个文件中,path是文件存储的地址，str是需要打印的内容，内容会以新行的形式添加在文件尾部
	public static void log(String path,String str){
		BufferedWriter bw = null;
		try {
			// new FileWriter(name,true)设置文件为在尾部添加模式,参数为false和没有参数都代表覆写方式
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true), "UTF-8"));
			bw.write(str+"\r\n");
		} catch (Exception e) {
			System.out.println("JspUtil.log path:" +path+"  str:"+str);
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public static void log(ArrayList list){
		BufferedWriter bw = null;
		try {
			// new FileWriter(name,true)设置文件为在尾部添加模式,参数为false和没有参数都代表覆写方式
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(CacheSystem.WEB_PATH+"azul-log.txt", true), "UTF-8"));
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i)+"\r\n");
			}
		} catch (Exception e) {
			System.out.println("JspUtil.log list error");
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	// 页面上使用的快捷打印工具,自动分析出对象类型然后以合适的形式打印出内容，一般用于程序调试
	@SuppressWarnings("unchecked")
	public static void p(Object obj) {
		try {
			if (obj == null) {
				System.out.println("==============>azul.JspUtil.p:input is null");
			}
			else if(obj instanceof String){
				System.out.println(obj.toString());
			}
			else if(obj instanceof java.util.Date){
				System.out.println(util.DateUtil.getDate((Date)obj,util.DateUtil.DATE_B));
			}
			else if (obj instanceof int[]) {
				int[] arr = (int[]) obj;
				for (int i = 0; i < arr.length; i++) {
					System.out.println(i + ":=" + arr[i]);
				}
			} else if (obj instanceof Object[]) {
				Object[] arr = (Object[]) obj;
				for (int i = 0; i < arr.length; i++) {
					System.out.println(i + ":=" + arr[i]);
				}
			}
			// 打印list
			else if (obj instanceof List) {
				int i = 0;
				List list = (List) obj;
				System.out.println("List size:" + list.size());
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Object element = iter.next();
					if(element instanceof List){
						List tempList=(List)element;
						for (int j = 0; j < tempList.size(); j++) {
							System.out.println(j + ":=" + tempList.get(j));
						}
					}
					else{
						System.out.println(i + ":=" + element);
					}
					i++;
				}
			}
			// 打印set
			else if (obj instanceof Set) {
				int i = 0;
				Set set = (Set) obj;
				System.out.println("Set size:" + set.size());
				for (Iterator iter = set.iterator(); iter.hasNext();) {
					Object element = iter.next();
					System.out.println(i + ":=" + element);
					i++;
				}
			}
			// 打印map
			else if (obj instanceof Map) {
				int i = 0;
				Map map = (Map) obj;
				System.out.println("Map size:" + map.size());
				Iterator<Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Object, Object> entry = it.next();
					Object keyId = entry.getKey();
					Object value = entry.getValue();
					System.out.println("------------");
					System.out.println("key="+keyId);
					//判断如果value还是个map继续解压下一级map
					if (value instanceof Map) {
						Map tempMap=(Map)value;
						System.out.println("sub map key:"+keyId+" size:"+tempMap.size());
						int k=0;
						Iterator<Entry<Object, Object>> sonIt = tempMap.entrySet().iterator();
						while (sonIt.hasNext()) {
							Entry<Object, Object> tempEntry = sonIt.next();  
							Object sonKeyId = tempEntry.getKey();
							Object sonValue = tempEntry.getValue();
							System.out.println(k + "   " + sonKeyId + ":=" + sonValue);
							k++;
						}
					}
					else if(value instanceof List){
						List tempList=(List)value;
						for (int j = 0; j < tempList.size(); j++) {
							System.out.println(keyId+"   "+j+":=" + tempList.get(j));
						}
					}
					else if(value instanceof String[]) {
						String[] arr=(String[])value;
						for (int j = 0; j < arr.length; j++) {
							System.out.println(j+":="+arr[j]);
						}
					}
					else if(value instanceof int[]) {
						int[] arr=(int[])value;
						for (int j = 0; j < arr.length; j++) {
							System.out.println(j+":="+arr[j]);
						}
					}
					else{
						System.out.println(i + "   " + keyId + ":=" + value);
						i++;
					}
				}
			}
			//打印request中的元素
			else if (obj instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) obj;
	        	Enumeration names = request.getParameterNames(); 
	        	while (names.hasMoreElements()){
	        	     String name = (String) names.nextElement();
	        	     System.out.println(name+":="+request.getParameter(name));
	        	}
			}
			// 打印object
			else{
				Class cls=obj.getClass();
				Method[] methods = cls.getDeclaredMethods();
				for (Method method : methods) {
					String methodName = method.getName();
					if (methodName.indexOf("get") > -1 && method.getParameterTypes().length==0) {
						try {
							System.out.println(cls.getName()+"."+methodName+"()="+method.invoke(obj, new Object[]{}));
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (NullPointerException ex){
			System.out.println("azul.JspUtil.p error:null arg is " + obj + " " + ex);
		} catch (Exception e) {
			System.out.println("azul.JspUtil.p error:arg is " + obj);
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		String[] cidTypeArr=new String[]{"品牌","五码","三码","外单","方案商"};
		System.out.println(azul.JspUtil.getTitle(cidTypeArr,"1,2"));
		StringBuffer conditionStrSB=new StringBuffer();
		int cid=2;
		appendWhere(conditionStrSB,"cid",cid,"=");
		System.out.println(decode("自2011-12-07日开始，用户分成收益显示方式有所改变。以前是以用户分成形式展现，以后更正为以用户总信息费形式展现，不便之处敬请谅解。"));
		
	}
}
