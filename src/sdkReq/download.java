package sdkReq;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import util.DebuUtil;
import util.FileUtil;

public class download extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3605521301084327145L;
	
    private synchronized int getCurrentCount(String flie)
    {
    	int current = 0;
    	String strfile = "C:\\"+flie+".txt";
    	List<String> list = FileUtil.read(strfile);
    	String content="";
    	JSONObject json = null;//IT
    	
    	for(String str:list)
    	{
    		content += str;
    	}
    	if(content.length() > 0)
    	{
    	    json = JSONObject.fromObject(content);
    	    current = json.getInt("count");
    	}
    	else
    	{
    		json = new JSONObject();
    		
    	}
    	current++;
    	json.put("count", current);
    	list = new ArrayList<String>();
    	list.add(json.toString());
    	FileUtil.write(strfile,list,false);
        return current;
    }
  
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("download doGet");
		String file = request.getParameter("file");
		HandleReq(file,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//DebuUtil.log("download doPost");
		String file = request.getParameter("file");
		HandleReq(file,response);
	}
	
	public void HandleReq(String flie, HttpServletResponse response)
	{
		//DebuUtil.log("flie="+flie);
		getCurrentCount(flie);
		try {
			response.sendRedirect(flie+".apk");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}
}
