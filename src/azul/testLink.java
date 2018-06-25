package azul;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.text.StrTokenizer;

import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class testLink {
	
	public String test(String url_s, String gameName){
		try{  
		    URL url = new URL(url_s);  
		    HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
		    /** 
		     * public int getResponseCode()throws IOException 
		     * 从 HTTP 响应消息获取状态码。 
		     * 例如，就以下状态行来说：  
		     * HTTP/1.0 200 OK 
		     * HTTP/1.0 401 Unauthorized 
		     * 将分别返回 200 和 401。 
		     * 如果无法从响应中识别任何代码（即响应不是有效的 HTTP），则返回 -1。  
		     *  
		     * 返回 HTTP 状态码或 -1 
		     */  
		    int state = conn.getResponseCode();  
		    if(state == 200){  
		         return "1";
		    }  
		    else{  
		        /*System.out.println(gameName+"  "+url_s+"此链接失效");
		        MailSendUtils msu = new MailSendUtils();
		    	try {
					msu.sendMail(url_s,gameName);
				} catch (Exception e) {
					e.printStackTrace();
				}*/
		    	return "2";
		    }  
		}  
	    catch(IOException e){  
	        //e.printStackTrace();
	    	return "2";
	    }
	}
}
