package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.ChannelDataReq;
import model.Userinfo;
import dao.CooperationDao;
import util.DebuUtil;
import util.StringUtil;
import viewmodel.SearchCom;

public class DataReqService extends SearchService{
	public String op;
	//public String BSS_NO;
	
	public int pagesize=31;//每页记录数
	public int pageno;
	public int recordcount;
	
	public java.util.Map diptypeMap;
	public ArrayList<ChannelDataReq> list;
	
	public DataReqService(HttpServletRequest request,HttpSession  session)
	{
		super(request,session);
		
		op =  request.getParameter("op");
		distype =  request.getParameter("distype");
		pageno=azul.JspUtil.getInt(request.getParameter("pageno"),1);
		//BSS_NO = request.getParameter("BSS_NO");
		
		//channel_no = (String)session.getAttribute("channel_no");
		
	}
	
	public void Handle()
	{
	
        super.Handle();
		diptypeMap=new HashMap<String,String>();
		diptypeMap.put("A", "渠道");
		
		if(role.equals(Userinfo.CPS) || role.equals(Userinfo.CPA) ||role.equals(Userinfo.CPA_R))   
		{
			distype="A";
		}
		list = new ArrayList<ChannelDataReq>();

		if(StringUtil.is_nullString(distype))
		{
			distype = "";
		}
		if((!StringUtil.is_nullString(op)) &&(op.equals("sch")))//
		{
			list = SearchCom.getList(selyear, selmonth, game_no, channel_no, business_no, packet_id, distype,game_type);
			//list = SearchCom.getList(selyear, selmonth, game_no, channel_no, business_no, channel_id, distype,game_type);
			//list =  SearchCom.getList("2015", "08", "289", "756","", -1, distype,game_type);
		  //list =  SearchCom.getList("2015", "08", "289", "756", "", -1, "null","");
			recordcount=list.size();
			
		}
	}
	
	
	
}
