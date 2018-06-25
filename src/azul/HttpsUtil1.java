package azul;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
  




import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.yeepay.HttpUtils;

import net.sf.json.JSONObject;
  
public class HttpsUtil1 {
  
    private static class TrustAnyTrustManager implements X509TrustManager {
  
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
  
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
  
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
  
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
  
    /**
     * post方式请求服务器(https协议)
     *
     * @param url
     *            请求地址
     * @param content
     *            参数
     * @param charset
     *            编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static byte[] post(String url, String content, String charset)
            throws NoSuchAlgorithmException, KeyManagementException,
            IOException {
   
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
                new java.security.SecureRandom());
  
        URL console = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoOutput(true);
        conn.connect();
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(content.getBytes(charset));
        // 刷新、关闭
        out.flush();
        out.close();
        InputStream is = conn.getInputStream();
        if (is != null) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            is.close();
            return outStream.toByteArray();
        }
        return null;
    }
    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
    
    	String	strUrl = "http://211.154.152.59:8080/sdk/activate";
    	//String	content  = "{'status':'99','out_trade_no':'999','fee':'1000'}";
    	//String	charset = "utf-8";
    	JSONObject json = new JSONObject();
//		json.put("cpfee","111");
//		json.put("cpparam","1234552934c5e42f");
//		json.put("status","0");
		json.put("device_id","357442052451203");
		json.put("packet_id","c026");
		json.put("game_id","5256");
		json.put("ver","1.07.2");
		//json.put("game_ver","5");
		json.put("imsi","460030000000");
		json.put("cid","177063007");
		String content = json.toString();
		String retust = HttpUtils.URLPostUTF82(strUrl, content);
		System.out.println(retust);
    }
}