package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DebuUtil {
	
    public static void log(String str)
    {
    	System.out.println( getDateTime()+":"+str+"\n");
    	if(ConstValue.DEBUG == 1)
    	   FileUtil.write("C:\\ztylog.txt", getDateTime()+":"+str+"\n");
    }
    
    public static void log3(String str)
    {
    	System.out.println(str);
    	//if(ConstValue.DEBUG == 1)
    	FileUtil.write("C:\\ztylogtemp.txt", getDateTime()+":"+str+"\n");
    }
    
    public static String getDateTime(){
		Calendar calendar=Calendar.getInstance();
		String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
		return time;
	}
    
    public static void log2(String str)
    {
    	System.out.println(str);
    	//if(ConstValue.DEBUG == 1)
    	FileUtil.write("C:\\ztythread.txt", getDateTime()+":"+str+"\n");
    }
    public static void log4(String str)
    {
    	System.out.println(str);
    	//if(ConstValue.DEBUG == 1)
    	FileUtil.write("C:\\bk.txt", getDateTime()+":"+str+"\n");
    }
    public static void log5(String str)
    {
    	System.out.println(str);
    	//if(ConstValue.DEBUG == 1)
    	FileUtil.write("C:\\check.txt", getDateTime()+":"+str+"\n");
    }
    
	public static String printfbuf(byte [] buf)
	{
		String ret = "";
		for(byte b:buf)
		{
			String t = String.format("%x,", b);
			ret += t;
		}
		return ret;
	}
	
	public static void printfStack()
	{
		 Throwable ex = new Throwable();
	     StackTraceElement[] stackElements = ex.getStackTrace();
	
	     if (stackElements != null) {
	         for (int i = 0; i < stackElements.length; i++) {
	        	 log3(stackElements[i].getClassName()+"."+stackElements[i].getMethodName());//返回类的完全限定名，该类包含由该堆栈跟踪元素�?��示的执行点�?
	         }
	     }
	 }
}

