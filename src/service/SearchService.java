package service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.BusinesserDao;
import dao.ChannelDao;
import dao.CooperationDao;
import dao.CpManageDao;
import util.DebuUtil;
import util.StringUtil;
import viewmodel.SearchCom;
import model.Businesser;
import model.Channel;
import model.Userinfo;

public class SearchService {
	public String game_type;
	
	public String game_no;
	public String business_no;
	public String channel_no;
	public String cp_no;
	public String packet_id;
	public String distype;
	public String selyear;
	public String selmonth;
	
	public Map gameTypeMap;
	public Map gameMap;
	
	public Map businesserMap;

	public Map channelMap;
	public Map packetMap;

	public Map yearMap;
	public Map monthMap;
	public String role;
	
	public SearchService(HttpServletRequest request,HttpSession  session)
	{
		game_type = request.getParameter("game_type");//20141203加入游戏类型判断
	
		game_no = request.getParameter("game_no");
		business_no = request.getParameter("business_no");
		channel_no = request.getParameter("channel_no");
		packet_id= request.getParameter("packet_id");//azul.JspUtil.getInt(request.getParameter("packet_id"),-1);
		
		distype =  request.getParameter("distype");
		selyear =  request.getParameter("selyear");
		selmonth =  request.getParameter("selmonth");
		
		
		Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
		role = sysUser.getRole();
        if(role.equals(Userinfo.business))   
        {
        	//business_no = (String)session.getAttribute("BSS");
        	BusinesserDao businesserDao=new BusinesserDao();
        	Businesser bsinesser=businesserDao.getRecord(sysUser.getId());
        	DebuUtil.log("sysUser.getId()="+sysUser.getId());
			if(bsinesser!=null)
			{
				DebuUtil.log("bsinesser找到");
				business_no = bsinesser.getNo();	
			}
			
        }
        else if(role.equals(Userinfo.CPS) || role.equals(Userinfo.CPA) ||role.equals(Userinfo.CPA_R))   
        {
        	business_no = "";
        	
        	ChannelDao channelDao=new ChannelDao();
			Object cidObj=channelDao.loadBySql("select * from channel where login_user="+sysUser.getId());
			
			if(cidObj!=null)
			{
				Channel channel=(Channel)cidObj;
				channel_no = channel.getNo();
				DebuUtil.log("channel_no:"+channel.getNo());
			}
        }
        else if(role.equals(Userinfo.CP))   
        {
        	
        	Integer ID = sysUser.getId();
        	DebuUtil.log("ID="+ID);
        	String appsql = "select cp_no from cp_manage where login_user = "+ID+"";
        	CpManageDao cpManageDao = new CpManageDao();
        	cp_no = (Integer)cpManageDao.getValue(appsql)+"";
			
        }
        
		//selmonth="04";
		DebuUtil.log("selmonth="+selmonth);
		DebuUtil.log("business_no="+business_no);
		//DebuUtil.log("BSS_NO="+BSS_NO);
		//DebuUtil.log("bssSql="+bssSql);
	}
	
	public void Handle()
	{
		selmonth = SearchCom.getCurMonth(selmonth);
		selyear = SearchCom.getCurYear(selyear);
		
		CooperationDao cooperationDao=new CooperationDao();
		gameTypeMap = SearchCom.getGameTypeMap(role);//


		if(StringUtil.is_nullString(game_type))
		{
		   game_type = SearchCom.getGameType(role);
		}

///////////////////////////////////////
/*Userinfo sysUser=(Userinfo)session.getAttribute("sysUser");
Integer ID = sysUser.getId();
String appsql = "select no,name from app where cp_no in(select cp_no from cp_manage where login_user = "+ID+")";
CooperationDao cooperationDao = new CooperationDao()
dataReqService.gameMap=cooperationDao.getSelectMap(appsql);*/
///////////////////////////////////////
		
		gameMap=SearchCom.getGameMap(game_type,game_no, channel_no, business_no,cp_no,role);//cooperationDao.getSelectMap(appsql);

		businesserMap=SearchCom.getBusinessMap(game_type,game_no, channel_no, business_no,role);

		channelMap=SearchCom.getChannelMap(game_type,game_no, channel_no, business_no,role);
		packetMap=SearchCom.getPacketMap(game_type,game_no, channel_no, business_no);

		yearMap=SearchCom.getYearMap();
		monthMap=SearchCom.getMonthMap();
	}
	
	
}
