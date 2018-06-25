package azul;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//Program Files
public class Tomcat {
	 private static String javaPath = "C:/Progra~1/Java/jdk1.6.0_43/";//
	 private static String tomcatPath = "C:/Progra~1/Tomcat6/";
	 
    private static void keepTomcatAlive() throws NullPointerException {
       String s;
       java.io.BufferedReader in=null;
       System.out.println("run================");
       try {
           URL url = new URL("http://localhost:8080/dingxue/index12.jsp");
           URLConnection con = url.openConnection();
           in = new java.io.BufferedReader(new java.io.InputStreamReader(con.getInputStream()));
           con.setConnectTimeout(1000);
           con.setReadTimeout(4000);
           while ((s = in.readLine()) != null) {
              if (s.length() > 0) {System.out.println("run----------------");
                  // 如果能够读取到页面则证明可用
                  return;
              }
           }
           in.close();
       } catch (Exception ex) {
    	   System.out.println(ex);
       }
       finally{
    	   if(in!=null)
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
       winStartTomcat();
    }

    public static void stopTomcat() {
       java.io.BufferedReader in =null;
       try {
           java.lang.Process p = java.lang.Runtime.getRuntime().exec(
                  "net stop \"Apache Tomcat\"");
           in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
           String s;
           String t = "成功停止";
           boolean restart = false;
           while ((s = in.readLine()) != null) {
              if (s.indexOf(t) != -1) {
                  restart = true;
                  break;
              }
           }
           System.out.println("<" + new Date() + "> Tomcat is stop "
                  + (restart ? "OK" : "ERROR"));
       } catch (Exception e) {
           e.printStackTrace();
       }
       finally{
    	   if(in!=null)
   			try {
   				in.close();
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
       }
    }

    public static void winStartTomcat() {System.out.println("22222222222222");
       try {
    	   String code=javaPath+"bin/java.exe -Xms128m -Xmx384m -jar -Duser.dir="+tomcatPath+" "+tomcatPath+"bootstrap.jar stop";
           java.lang.Process p = java.lang.Runtime.getRuntime().exec(code);
       } catch (Exception e) {
           e.printStackTrace();
       }
       java.io.BufferedReader in =null;
       try {
    	   String code=javaPath+"bin/java.exe -Xms128m -Xmx384m -jar -Duser.dir="+tomcatPath+" "+tomcatPath+"bootstrap.jar start";
    	   //String code="C:/Program Files/Java/jdk1.6.0_14/bin/java.exe -Xms128m -Xmx384m -jar -Duser.dir=\"F:/work/tomcat6/bin/" \"F:\work\tomcat6\bin\bootstrap.jar\" start";
           java.lang.Process p = java.lang.Runtime.getRuntime().exec(code);
           in = new java.io.BufferedReader(new java.io.InputStreamReader(p.getInputStream()));
           String s;
           String t = "启动成功";
           boolean restart = false;
           while ((s = in.readLine()) != null) {
              if (s.indexOf(t) != -1) {
                  restart = true;
                  break;
              }
           }
           System.out.println("<" + new Date() + "> Tomcat is start "+ (restart ? "OK" : "ERROR"));
       } catch (Exception e) {
           e.printStackTrace();
       }
       finally{
    	   if(in!=null)
   			try {
   				in.close();
   			} catch (IOException e) {
   				e.printStackTrace();
   			}
       }
    }
    
    public static void test()
    {
      try
 	   {
     	   Runtime r=Runtime.getRuntime();
     	   Process p=r.exec("cmd.exe /c dir");
            BufferedReader bf=new BufferedReader(new InputStreamReader(p.getInputStream()));
     	   String line="";
     	   while((line=bf.readLine())!=null)
     	   System.out.println(line);
     	   }
     	   catch(Exception exc)
     	   {
     	      exc.printStackTrace();
     	   }
      }
       
        
    

    public static void editfile(String [] args)
    {
 	   List<String> fstr = Tomcat.read("E:\\decode\\AndroidManifest.xml");
 		
 		List<String> ret = new ArrayList<String>();
 		String id = "211153001";//args[1];
 		if(args.length > 0)
 		{
 			id = args[0];
 		}
 		System.out.println("args.length="+args.length);
 		
 		System.out.println(id);
 		for(String str:fstr)
 		{
 			String tstr = str;
 			if(str.contains("ZTY_PACKET_ID"))
 			{
 				/*JSONObject json =  JSONObject.fromObject("{\"device_id\":\"" + "123456789" + "\"," +
 		                "\"game_id\":" + "0005" + "\"," +
 		                "\"packet_id\":\"" + "0005" + "\"}");*/
 				tstr =String.format("<meta-data android:name=\"ZTY_PACKET_ID\" android:value=\"%s\" />",id);//String.format("<meta-data android:name="%s" android:value="%s" %s>", "ZTY_PACKET_ID","211152001","/");
 				System.out.println(tstr);
 			}
 			//tstr +="\n";
 			ret.add(tstr);
 			
 		}
 		Tomcat.delete("E:\\decode\\AndroidManifest.xml");
 		Tomcat.write("E:\\decode\\AndroidManifest.xml", ret, false);
    }
    public static boolean write(String path, List<String> list, boolean append) {
		BufferedWriter bw = null;
		try {
			// new FileWriter(name,true)设置文件为在尾部添加模式,参数为false和没有参数都代表覆写方式
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), "UTF-8"));
			for (String element : list) {
				bw.write(element);
			}
		} catch (Exception e) {
			System.out.println("arg1:" + path);
			System.out.println("arg2:" + list.size());
			System.out.println("arg3:" + append);
			e.printStackTrace();
			return false;
		} finally {
			try {
				if(bw!=null)bw.close();
			} catch (Exception e) {
				System.out.println("FileUtil.write colse bw wrong:" + e);
			}
		}
		return true;
	}
    public static boolean delete(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				System.out.println("输入文件:" + fileName + "不存在");
				return false;
			}
			file.delete();
		} catch (Exception e) {
			System.out.println("arg1:" + fileName);
			e.printStackTrace();
			return false;
		}
		return true;
	}
    public static List<String> read(String name) {
		List<String> list = new ArrayList<String>();
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new FileReader(name));
			String lineStr = "";
			while ((lineStr = br.readLine()) != null) { // 按行读取文件
				//System.out.println(lineStr);
				list.add(lineStr);
			}
			
		} catch (Exception e) {
			System.out.println("arg1:" + name);
			e.printStackTrace();
		} finally {
			try {
				if(br!=null)br.close();
			} catch (Exception e) {
				System.out.println("FileUtil.read colse br wrong:" + e);
			}
		}
		return list;
	}
    public static void main(String[] args) {
       /*while (true) {
           try {
              //Tomcat.keepTomcatAlive();
        	   test();
              Thread.sleep(30000);
           } catch (Exception ex) {
        	   System.out.println(ex);
           }
       }*/
    	editfile(args);
    }
}