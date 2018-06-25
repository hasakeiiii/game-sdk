package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import model.App;
import model.Businesser;
import model.Channel;
import model.Userinfo;
import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import util.StringUtil;
import dao.AppDao;
import dao.BusinesserDao;
import dao.ChannelDao;
import dao.CooperationDao;

/**
 * 界面下拉列表加载公用servlet
 * @author wuchengf
 */
public class CommonOperationAction extends HttpServlet {

	
	public CommonOperationAction() {
		super();
	}

	
	public void destroy() {
		super.destroy(); 
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String cmd = request.getParameter("cmd");
		String operatedType = request.getParameter("operatedType");
		
		JSONObject jo = new JSONObject();
		jo.put("info", "error");
		
		if(!StringUtil.is_nullString(cmd) && "chgGameType".equals(cmd)){
			//如果是改变游戏类型事件
			AppDao dao = new AppDao();
			String gameType = operatedType;
			String cpNo = request.getParameter("cpNo");
			List<ArrayList> rawList = null;
			if(cpNo.equals("null")||StringUtil.is_nullString(cpNo)){
				 rawList = dao.getAppNameAndNoListByType(gameType);
			}else{
				 rawList = dao.getAppNameAndNoListByType(gameType,cpNo);
			}
			List<App> list = new ArrayList<App>();
			for(ArrayList rl : rawList){
				App app = new App();
				app.setName((String)rl.get(0));
				app.setNo((String)rl.get(1));
				list.add(app);
			}
			Collections.sort(list, new Comparator<App>(){
				@Override
				public int compare(App a1, App a2) {
					return ToPinYinString(a1.getName()).compareTo(ToPinYinString(a2.getName()));
				}
			});
			jo.put("list", list);
			jo.put("info", "success");
		}
		else if(!StringUtil.is_nullString(cmd) && "getAllBusiness".equals(cmd) && StringUtil.is_nullString(operatedType)){
			//界面加载时加载所有商务
			BusinesserDao dao = new BusinesserDao();
			List<Businesser> list = dao.getAllBusinessers();
			Collections.sort(list, new Comparator<Businesser>(){
				@Override
				public int compare(Businesser o1, Businesser o2) {
					return ToPinYinString(o1.getName()).compareTo(ToPinYinString(o2.getName()));
				}
			});
			jo.put("list", list);
			jo.put("info", "success");
		}else if(!StringUtil.is_nullString(cmd) && "chgGame".equals(cmd)){
			//更改游戏时,显示所有对应的渠道
			String gameNo = operatedType;
			
			String businessNo = request.getParameter("businessNo");
			List<ArrayList> rawList = null;
			ChannelDao dao = new ChannelDao();
			if(StringUtils.isNotEmpty(businessNo)){
				rawList = dao.getAllChannelByGameNoAndBusinessNo(gameNo,businessNo);
			}else{
				rawList = dao.getAllChannelByGameNo(gameNo);
				
			}
			
			
			List<Channel> list = new ArrayList<Channel>();
			
			for(ArrayList rl : rawList){
				Channel c = new Channel();
				c.setNo((String)rl.get(0));
				c.setName((String)rl.get(1));
				list.add(c);
			}
			Collections.sort(list, new Comparator<Channel>(){
				@Override
				public int compare(Channel o1, Channel o2) {
					return ToPinYinString(o1.getName()).compareTo(ToPinYinString(o2.getName()));
				}
			});
			
			jo.put("list", list);
			jo.put("info", "success");
		}else if(!StringUtil.is_nullString(cmd) && "getAllChannel".equals(cmd) && StringUtil.is_nullString(operatedType)){
			//加载所有渠道
			ChannelDao dao = new ChannelDao();
			List<Channel> list = dao.getAllChannel();
			Collections.sort(list, new Comparator<Channel>(){
				@Override
				public int compare(Channel o1, Channel o2) {
					return ToPinYinString(o1.getName()).compareTo(ToPinYinString(o2.getName()));
				}
			});
			jo.put("list", list);
			jo.put("info", "success");
		}else if(!StringUtil.is_nullString(cmd) && "chgChannel".equals(cmd)){
			//
			String channelNo = request.getParameter("channelNo");
			String businessNo = request.getParameter("businessNo");
			
			CooperationDao dao = new CooperationDao();		
			
			List<ArrayList> gameRawList = dao.getAllGameByChlNo(channelNo);
			List<App> gameList = new ArrayList<>();
			
			for(ArrayList al : gameRawList){
				App app = new App();
				app.setName((String)al.get(0));
				app.setNo((String)al.get(1));
				gameList.add(app);
			}
			
			Collections.sort(gameList, new Comparator<App>(){
				@Override
				public int compare(App a1, App a2) {
					return ToPinYinString(a1.getName()).compareTo(ToPinYinString(a2.getName()));
				}
			});
			
			jo.put("gameList", gameList);
			jo.put("info", "success");
			
		}else if (!StringUtil.is_nullString(cmd) && "validRole".equals(cmd)){
			//获取当前session中角色的权限
			String role = ((Userinfo)request.getSession().getAttribute("sysUser")).getRole();
			jo.put("info", "success");
			jo.put("role", role);
		}else if(!StringUtil.is_nullString(cmd) && "ensurePacketId".equals(cmd)){
			//确定APKID
			String channelNo = request.getParameter("channelNo");
			String businessNo = request.getParameter("businessNo");
			String gameNo = request.getParameter("gameNo");
			
			CooperationDao dao = new CooperationDao();		
			List<ArrayList> rawList = dao.getApkNo(gameNo, channelNo, businessNo);
			jo.put("list", rawList);
			jo.put("info", "success");
		}
		
		out.print(jo);
		out.flush();
		out.close();
	}

	
	public void init() throws ServletException {
		// Put your code here
	}
	
	
	/**
	 * 汉语拼音排序
	 * @param str
	 * @return
	 */
	public static  String ToPinYinString(String str){  
        StringBuilder sb=new StringBuilder();  
        String[] arr=null;  
        for(int i=0;i<str.length();i++){  
            arr=PinyinHelper.toHanyuPinyinStringArray(str.charAt(i));  
            if(arr!=null && arr.length>0){  
                for (String string : arr) {  
                    sb.append(string);  
                }  
            }  
        }  
        return sb.toString();  
    }
}
