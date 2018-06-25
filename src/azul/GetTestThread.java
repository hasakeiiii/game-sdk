package azul;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;

import util.DateUtil;
import util.DebuUtil;
import util.StringUtil;

public class GetTestThread extends Thread {

	private ServerSocket serverSocket;
	private String urlpath;
	private int sleeptm;
	int requestItems=0;
			int failedItems = 0;
	
	public GetTestThread(String turl,int sleeptime) {
		urlpath = turl;
		sleeptm = sleeptime;
	}
	
	@Override
	public void run() {
		super.run();
		do {
			requestItems++;
			/*try {
				sleep(sleeptm);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			getContent(urlpath);
			
		} while (true);

	}

	public void getContent(String turl) {
		
		URL url;
		try {
			
			long reqBefore = System.currentTimeMillis();
			
			url = new URL(turl);
			// 打开和URL之间的连接
			URLConnection conn = url.openConnection();
			// 设置通用的请求时间
			conn.setConnectTimeout(10000);
			// 建立实际的连接
			conn.connect();
			
			BufferedReader in;
			
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));  
            String line; 
            String result = "";
            while ((line = in .readLine()) != null) {  
                result += line;  
            }  
            if (result==null||result.length()<30) {
				failedItems++;
			}
            long now = System.currentTimeMillis();
            String time = DateUtil.getDateTime();//
            System.out.println("访问次数："+requestItems+"失敗次数："+failedItems);
            System.out.println("时间："+(now-reqBefore)+" 请求结果："+result+"\n");
           
            in.close();
            
            
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
