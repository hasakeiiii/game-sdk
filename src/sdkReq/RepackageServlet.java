package sdkReq;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class RepackageServlet extends HttpServlet {

	public static boolean IsBusy = false;
	public static String FAIL = "fail";
	public static String SUCCESS = "success";
	public static String baseUrl = "http://sa2.91muzhi.com/game/";
	public static String tomcat = "F:/tomcat7/apache-tomcat-7.0.57/webapps/game/";
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
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (IsBusy) {
			response.setContentType("text/html;charset=utf8");
			PrintWriter out = response.getWriter();
			out.print("服务器忙，请稍后再试...");
			out.flush();
			out.close();
		}else{
			handleRequest(request, response);
		}
		
	}

	private void handleRequest(HttpServletRequest request,
			HttpServletResponse response)throws IOException {
		IsBusy = true;
		String fileName = "";
		String finalfilePacket = "";
		String packetIds = "";
		String gameId = "";
		String packetNo = "";
		String result = "";
		String msg = "";
		String[] packIds = null;
		String[] channels = null;
		String[] finalfileNames = null;
		
		fileName = request.getParameter("fileName");
		
		finalfilePacket = fileName.split("\\.")[0];//+"_mzyw";
		packetIds = request.getParameter("packetIds");

		if (fileName.isEmpty() || packetIds.isEmpty()) {
			result = FAIL;
			msg = "缺少参数";
		} else {
			packIds = packetIds.split(",");
			int len = packIds.length;
			finalfileNames = new String[len];
			channels = new String[len];
			gameId = packIds[0].substring(3, 6);
			packetNo = packIds[0].substring(6, 9);
			for (int i = 0; i < len; i++) {
				channels[i] = packIds[i].substring(0,3);
				finalfileNames[i] = finalfilePacket + "_"+channels[i] + gameId
						+ packetNo + ".apk";
			}
			String commandFileName = createCommandFile(fileName,
					finalfilePacket, gameId, packetNo, channels);

			if (commandFileName != null) {
				result = runRepackTask(commandFileName, finalfilePacket, finalfileNames);
				msg = result.equals(SUCCESS)?"文件打包成功":"文件打包失败";
			} else {
				result = FAIL;
				msg = "创建命令文件失败";
			}
		}

		response.setContentType("text/html;charset=utf8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		json.put("result", result);
		json.put("msg", msg);
		if (result.equals(SUCCESS)) {
			for (int j = 0; j < finalfileNames.length; j++) {
				String url = baseUrl+finalfilePacket+"/"+finalfileNames[j];
				json.put(packIds[j], url);
			}
		}else{
			
		}
		out.print(json.toString());
		out.flush();
		out.close();
		IsBusy = false;
		
	}

	/**
	 * 创建执行文件
	 * 
	 * @param fileName
	 * @param finalfilePacket
	 * @param gameId
	 * @param packetNo
	 * @param channels
	 * @return 执行文件名
	 * @throws IOException
	 */
	private String createCommandFile(String fileName, String finalfilePacket,
			String gameId, String packetNo, String[] channels)
			throws IOException {
		String commandFileName = "E:/decode/exe_" + gameId + ".bat";

		File taskFile = new File(commandFileName);
		if (!taskFile.exists()) {
			taskFile.createNewFile();
		}
		FileWriter fwr = new FileWriter(taskFile);
		BufferedWriter bfr = new BufferedWriter(fwr);
		bfr.write("e:");
		bfr.newLine();
		bfr.write("cd \\decode");
		bfr.newLine();
		bfr.write("call=\"step1.bat\" " + fileName + " " + finalfilePacket);
		bfr.newLine();
		String temp = "for %%a in (";
		for (int i = 0; i < channels.length; i++) {
			if (i != channels.length - 1) {
				temp += channels[i] + ",";
			} else {
				temp += channels[i];
			}
		}
		temp = temp + ") do step2.bat %%a " + gameId + packetNo + " "
				+ finalfilePacket;
		bfr.write(temp);
		bfr.flush();
		bfr.close();
		if (taskFile.exists())
			return commandFileName;
		else
			return null;
	}

	private String runRepackTask(String file, String finalfilePacket,
			String[] finalfileNames) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process process = rt.exec(file);

			InputStream in = process.getInputStream();
			BufferedReader buf = new BufferedReader(new InputStreamReader(in));

			int c = 0;
			String str = null;
			while (((str = buf.readLine()) != null)) {
				System.out.println(str);
			}
			buf.close();
			in.close();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("执行完毕");
			int successNo = 0;
			int length = finalfileNames.length;
			for (int i = 0; i < length; i++) {
			File finalfile = new File(tomcat + finalfilePacket + "/"
						+ finalfileNames[i]);
				if (finalfile.exists()) {
					successNo++;
					System.out.println(finalfileNames[i] + "打包成功");
				}
			}
			
			return successNo==length?"success":"fail";
			
		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (IsBusy) {
			response.setContentType("text/html;charset=utf8");
			PrintWriter out = response.getWriter();
			out.print("服务器忙，请稍后再试...");
			out.flush();
			out.close();
		}else{
			handleRequest(request, response);
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
