package sdkReq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.ConstValue;
import model.Gamepack;
import model.Packet;
import net.sf.json.JSONObject;
import dao.GamepackDao;
import dao.PacketDao;

public class PreRepackServlet extends HttpServlet {

	public static String FAIL = "fail";
	public static String SUCCESS = "success";
	/**
	 * Constructor of the object.
	 */
	public PreRepackServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		handleRequest(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request,
			HttpServletResponse response)throws IOException {
		System.out.println("******************预打包处理！！！****************");
		String fileName = "";
		String packetIds = "";
		String result = "";
		String sign = "";
		fileName = request.getParameter("fileName");
		packetIds = request.getParameter("packetIds");
		String strUrl = ConstValue.CPPACKETServerUrl+"/RepackageServlet";//"http://www.91muzhi.com:8091/repack/RepackageServlet";//"http://localhost:8080/sdk/RepackageServlet";
		String content = String.format("fileName=%s&packetIds=%s", fileName,packetIds);
		if (!fileName.isEmpty() && !packetIds.isEmpty()) {
			result = post(strUrl, content);
			JSONObject json = JSONObject.fromObject(result);
			sign = (String) json.opt("result");
			if (sign.equals(SUCCESS)) {
				update(packetIds,fileName,json);
			}
		}
		
		
		
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		out.print(result);
		out.flush();
		out.close();
	}

	private String post(String strUrl, String content)
			throws MalformedURLException, IOException, ProtocolException {
		String ret=""; 
		URL url = new URL(strUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setConnectTimeout(20*1000);
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
   // List result = new ArrayList(); 
		while (true) {
		  String line = bin.readLine();
		  if (line == null) {
		    break;
		  }
		  else {
			  ret+=line;
			  System.out.println("******************打包完毕！！！****************");
			  System.out.println(ret);//result.add(line);
			  break;
		  }
		}
		
		return ret;
	}
	/**
	 * 如果打包成功，更新数据库中packet的pre_version到最新，跟新下载链接
	 * @param packetIds
	 * @param fileName
	 */
	private void update(String packetIds,String fileName,JSONObject json) {
		String[] packsIds = packetIds.split(",");
		PacketDao pdao = new PacketDao();
		GamepackDao gpdao = new GamepackDao();
		for (int i = 0; i < packsIds.length; i++) {
			if (!packsIds[i].isEmpty()) {
				Packet pa = pdao.getRecordByPackId(packsIds[i]);
				String durl = json.getString(packsIds[i]);
				
				String pno = pa.getPacketNo();
				Gamepack gmPack = gpdao.getRecordByPackNo(pno);
				String ver = gmPack.getVersion();
				
				pa.setDUrl(durl);
				pa.setPreVersion(ver);
				pdao.edit(pa);
			}
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
