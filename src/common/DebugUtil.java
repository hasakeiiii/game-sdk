package common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class DebugUtil {
	
	public static void main(String[] args) {
		DebugUtil.printJava("   org.apache.jsp.smsSend.cidSmsSendOkInfoList_jsp._jspService(cidSmsSendOkInfoList_jsp.java:105)");
	}
	
	public static void printJava(String filePath) {//adAction_jsp.java
		String tempLineNum=filePath.substring(filePath.indexOf(":")+1,filePath.indexOf(")"));
		String fileName=filePath.substring(filePath.indexOf("(")+1,filePath.indexOf(".java"));
		String realPath=filePath.substring(filePath.indexOf("org.apache.jsp.")+15,filePath.indexOf("."+fileName));
		realPath=realPath.replaceAll("\\.", "/");
		System.out.println(realPath);
		int lineNum=Integer.valueOf(tempLineNum);
		//找不到文件请注意应用是否正确
		String tomcatPath = "F:/tomcat6/work/Catalina/localhost/dingxue/org/apache/jsp/" + realPath+"/"+fileName+".java";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(tomcatPath));
			String lineStr = "";
			int i=1;
			while ((lineStr = br.readLine()) != null) { 
				if(i==lineNum){
					System.out.println("=============================================");
					System.out.println(lineStr+"---"+i);
					System.out.println("=============================================");
				}
				else{
					System.out.println(lineStr+"---"+i);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				System.out.println("DebugUtil.printJava colse br wrong:" + e);
			}
		}
		
	}
	@SuppressWarnings("unchecked")
	public static String p(Object obj) {
		StringBuffer sb=new StringBuffer("");
		try {
			if (obj == null) {
				System.out.println("==============>DebugUtil.p:input is null");
				sb.append("==============>DebugUtil.p:input is null");
				return sb.toString();
			}
			if(obj instanceof String){
				System.out.println(obj.toString());
				sb.append(obj.toString());
				return sb.toString();
			}
			else if(obj instanceof java.util.Date){
				System.out.println(util.DateUtil.getDate((java.util.Date)obj,util.DateUtil.DATE_B));
				sb.append(util.DateUtil.getDate((java.util.Date)obj,util.DateUtil.DATE_B));
				return sb.toString();
			}
			else if (obj instanceof int[]) {
				int[] arr = (int[]) obj;
				for (int i = 0; i < arr.length; i++) {
					System.out.println(i + ":=" + arr[i]);
					sb.append(i).append(":=").append(arr[i]).append("\n");
				}
				return sb.toString();
			} else if (obj instanceof Object[]) {
				Object[] arr = (Object[]) obj;
				for (int i = 0; i < arr.length; i++) {
					System.out.println(i + ":=" + arr[i]);
					sb.append(i).append(":=").append(arr[i]).append("\n");
				}
				return sb.toString();
			}
			else if (obj instanceof List) {
				int i = 0;
				List list = (List) obj;
				System.out.println("List size:" + list.size());
				sb.append("List size:" + list.size()).append("\n");
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					Object element = iter.next();
					if(element instanceof List){
						List tempList=(List)element;
						for (int j = 0; j < tempList.size(); j++) {
							System.out.println(j + ":=" + tempList.get(j));
							sb.append(j).append(":=").append(tempList.get(j)).append("\n");
						}
					}
					else{
						System.out.println(i + ":=" + element);
						sb.append(i).append(":=").append(element).append("\n");
					}
					i++;
				}
				return sb.toString();
			}
			else if (obj instanceof Set) {
				int i = 0;
				Set set = (Set) obj;
				System.out.println("Set size:" + set.size());
				sb.append("Set size:" + set.size()).append("\n");
				for (Iterator iter = set.iterator(); iter.hasNext();) {
					Object element = iter.next();
					System.out.println(i + ":=" + element);
					sb.append(i).append(":=").append(element).append("\n");
					i++;
				}
				return sb.toString();
			}
			else if (obj instanceof Map) {
				int i = 0;
				Map map = (Map) obj;
				System.out.println("Map size:" + map.size());
				sb.append(i).append("Map size:").append(map.size()).append("\n");
				Iterator<Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Object, Object> entry = it.next();
					Object keyId = entry.getKey();
					Object value = entry.getValue();
					if (value instanceof Map) {
						Map tempMap=(Map)value;
						System.out.println("sub map key:"+keyId+" size:"+tempMap.size());
						sb.append("sub map key:").append(keyId).append(" size:").append(map.size()).append("\n");
						int k=0;
						Iterator<Entry<Object, Object>> sonIt = tempMap.entrySet().iterator();
						while (sonIt.hasNext()) {
							Entry<Object, Object> tempEntry = sonIt.next();  
							Object sonKeyId = tempEntry.getKey();
							Object sonValue = tempEntry.getValue();
							System.out.println(k + "   " + sonKeyId + ":=" + sonValue);
							sb.append(k).append("   ").append(sonKeyId).append(":=").append(sonValue).append("\n");
							k++;
						}
					}
					else if(value instanceof List){
						List tempList=(List)value;
						for (int j = 0; j < tempList.size(); j++) {
							System.out.println(keyId+"   "+j+":=" + tempList.get(j));
							sb.append(keyId).append("   ").append(j).append(":=").append(tempList.get(j)).append("\n");
						}
					}
					else{
						System.out.println(i + "   " + keyId + ":=" + value);
						sb.append(i).append("   ").append(keyId).append(":=").append(value).append("\n");
						i++;
					}
				}
				return sb.toString();
			}
			else if (obj instanceof HttpServletRequest) {
				HttpServletRequest request = (HttpServletRequest) obj;
	        	Enumeration names = request.getParameterNames(); 
	        	while (names.hasMoreElements()){
	        	     String name = (String) names.nextElement();
	        	     System.out.println(name+":="+request.getParameter(name));
	        	     sb.append(name).append(":=").append(request.getParameter(name)).append("\n");
	        	}
	        	return sb.toString();
			}
			else{
				Class cls=obj.getClass();
				Method[] methods = cls.getDeclaredMethods();
				for (Method method : methods) {
					String methodName = method.getName();
					if (methodName.indexOf("get") > -1 && method.getParameterTypes().length==0) {
						try {
							System.out.println(cls.getName()+"."+methodName+"()="+method.invoke(obj, null));
							sb.append(cls.getName()).append(".").append(methodName).append("()=").append(method.invoke(obj, null)).append("\n");
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
			System.out.println("DebugUtil.p error:null arg is " + obj + " " + ex);
		} catch (Exception e) {
			System.out.println("DebugUtil.p error:arg is " + obj);
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	//得到PreparedStatement组成的sql语句
	public static String getParam(PreparedStatement pstmt){
		StringBuffer sb=new StringBuffer();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
