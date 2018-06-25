package azul;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import db.ConnectionFactory;

public class CacheSp {
	//真实数据同步缓存
	/*@SuppressWarnings("unchecked")
	public static HashMap<String,String[]> sendMap = null;
	@SuppressWarnings("unchecked")
	public static HashMap<String,String> urlMap = null;
	//原始命令数据缓存
	@SuppressWarnings("unchecked")
	public static HashMap<String,String[]> paramMap = null;
	//真实匹配数据缓存
	@SuppressWarnings("unchecked")
	public static HashMap<String,ArrayList<common.ObjectVO>> codeMap = null;
	
	public static HashMap<String,String> ivrUrlMap = null;
	public static HashMap<String,String> ivrCidMap = null;
	public static HashMap<String,Double> ivrRateMap = null;
	
	public static HashMap<String,Integer> limitMap=new HashMap<String,Integer>();
	
	@SuppressWarnings("unchecked")
	public static void initSpParam() {
		System.out.println("初始化sp工具和对应缓存");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			paramMap=new HashMap<String,String[]>();
			codeMap = new HashMap<String,ArrayList<common.ObjectVO>>();
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			//因为需要从长到短比较模糊指令的原始指令是那个，所以倒序排列
			rs = stmt.executeQuery("select * from cfg_sp_code order by LENGTH(sp_code) desc");
			while (rs.next()) {
				String sid=rs.getString("sid");
				String sp_code=rs.getString("sp_code").trim().toLowerCase();
				String cid=rs.getString("cid");
				String rate=rs.getString("rate");
				String province=rs.getString("province")==null?"00":rs.getString("province");
				String tempKey=sid+"_"+sp_code;
				String[] arr=province.split(",");
				for (int i = 0; i < arr.length; i++) {
					//默认配置不用加到缓存中
					if(!"".equals(arr[i]) && !"00".equals(arr[i])) {
						paramMap.put(tempKey+"_"+arr[i],new String[] {rate,cid});
					}
				}
				if (codeMap.containsKey(sid)) {
					ArrayList tempList=(ArrayList)codeMap.get(sid);
					//带地区和不带地区的命令有可能重复。所以不用重复添加
					if(!tempList.contains(sid)) {
						common.ObjectVO vo=new common.ObjectVO();
						vo.setObj1(sp_code);
						vo.setObj2(cid);
						vo.setObj3(rate);
						vo.setObj4(province);
						tempList.add(vo);
						codeMap.put(sid, tempList);
					}
				}
				else{
					ArrayList tempList=new ArrayList();
					common.ObjectVO vo=new common.ObjectVO();
					vo.setObj1(sp_code);
					vo.setObj2(cid);
					vo.setObj3(rate);
					vo.setObj4(province);
					tempList.add(vo);
					codeMap.put(sid, tempList);
				}
			}
			//azul.JspUtil.p(paramMap);
			//azul.JspUtil.p(cidMap);
			//azul.JspUtil.p(rateMap);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rs, stmt, conn);
		}
		System.out.println("初始化计费上限");
		initFeeLimit();
		System.out.println("初始化IVR");
		//初始化ivr参数
		Connection connA = null;
		Statement stmtA = null;
		ResultSet rsA = null;
		try {
			ivrCidMap=new HashMap<String,String>();
			ivrRateMap=new HashMap<String,Double>();
			ivrUrlMap = new HashMap<String,String>();
			connA = ConnectionFactory.getInstance().getConnection();
			stmtA = connA.createStatement();
			rsA = stmtA.executeQuery("select * from cfg_sp_code_ivr");
			while (rsA.next()) {
				String sp_code=rsA.getString("sp_code").trim().toLowerCase();
				String cid=rsA.getString("cid");
				double rate=rsA.getDouble("rate");
				ivrCidMap.put(sp_code,cid);
				ivrRateMap.put(sp_code, rate);
			}
			rsA = stmtA.executeQuery("select * from cfg_cid_url_ivr");
			while (rsA.next()) {
				ivrUrlMap.put(rsA.getString("cid"),rsA.getString("url"));
			}
			//azul.JspUtil.p(paramMap);
			//azul.JspUtil.p(cidMap);
			//azul.JspUtil.p(rateMap);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rsA, stmtA, connA);
		}
	}
	
	//初始化cid同步url
	public static void initCidUrl() {
		System.out.println("初始化cid同步url");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			urlMap=new HashMap<String,String>();
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from cfg_cid_url");
			while (rs.next()) {
				urlMap.put(rs.getString("cid"), rs.getString("url"));
			}
			//azul.JspUtil.p(urlMap);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rs, stmt, conn);
		}
	}
	
	public static void initSpSend() {
		System.out.println("初始化cid同步url");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			sendMap=new HashMap<String,String[]>();
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from cfg_sp_send");
			while (rs.next()) {
				sendMap.put(rs.getString("sid")+"_"+rs.getString("spnum")+"_"+rs.getString("msg"),new String[] {rs.getString("mo"),rs.getString("mr")});
			}
			//azul.JspUtil.p(urlMap);
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rs, stmt, conn);
		}
	}
	
	//根据sid和msg直接找到扣量设置
	public static String[] getParam(String sid,String msg,String province){
		String[] arr=new String[] {"0",""};
		msg=msg.toLowerCase();
		String tempKey=sid+"_"+msg+"_"+province;
		//先找带省份的分配
		//System.out.println(sid+"---"+msg+"---"+province);
		if(paramMap.containsKey(tempKey)){
			//System.out.println("缓存读取");
			return paramMap.get(tempKey);
		}
		else{
			//System.out.println("查询读取");
			//如果缓存里面没有这个命令就加入到缓存中
			String tempRate="0";
			String tempCid="";
			//缓存中没有配置这个通道的信息，就说明没有分配参数
			if(!codeMap.containsKey(sid)) {
				return arr;
			}
			ArrayList<common.ObjectVO> tempList=(ArrayList<common.ObjectVO>)codeMap.get(sid);
			for (int i = 0; i < tempList.size(); i++) {
				common.ObjectVO vo=tempList.get(i);
				String sp_code=(String)vo.getObj1();
				//简单判断会使AD11246归属到AD1命令中，所以需要在list中找到最长的命令来匹配
				//momsg.toLowerCase()兼容大小写问题,一定要判断以这个代码开头的，否则有可能找到其他配置
				if(msg.trim().toLowerCase().indexOf(sp_code)==0){
					String tempProvince=(String)vo.getObj4();
					//暂时保留默认配置一边加入缓存
					//System.out.println(tempProvince+"==="+province);
					if("00".equals(tempProvince) && "".equals(tempCid)) {
						tempRate=(String)vo.getObj3();
						tempCid=(String)vo.getObj2();
					}
					if(tempProvince.indexOf(province)>-1) {
						//System.out.println("找到配置:"+msg+"---"+sp_code+"---"+tempProvince+"---"+province);
						arr[0]=(String)vo.getObj3();
						arr[1]=(String)vo.getObj2();	
						paramMap.put(sid+"_"+msg+"_"+province,arr);
						//找到配置就出来。防止被其他指令的信息覆盖
						break;
					}
				}
			}
			//没有找到对应省份的配置就使用默认00的配置
			if("".equals(arr[1])) {
				//System.out.println("使用默认");
				arr[0]=tempRate;
				arr[1]=tempCid;
				paramMap.put(sid+"_"+msg+"_"+province,arr);
			}
		}
		//System.out.println("返回结果:"+arr[0]+"---"+arr[1]);
		return arr;
	}
	
	//根据cid直接找到同步url
	@SuppressWarnings("unchecked")
	public static String getUrl(String cid){
		if(urlMap.containsKey(cid)){
			return urlMap.get(cid);
		}
		return "";
	}

	//-----------------------------------------------计费上限------------------
	//初始化发送上限缓存
	public static void initFeeLimit() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from cfg_stat");
			while (rs.next()) {
				String cidTid=rs.getString("sid")+"_"+rs.getString("cid")+"_"+rs.getString("province")+"_"+rs.getString("msg");
				int num=getNum(rs.getString("sid"),rs.getString("cid"),rs.getString("province"),rs.getString("msg"));
				int max=rs.getInt("max_num");
				//如果已经大于同步上限就不添加到缓存
				System.out.println(cidTid+"   "+max+"   "+num);
				if(num<max){
					limitMap.put(cidTid,max-num);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
		//azul.JspUtil.p(limitMap);
	}
	
	static int getNum(String sid,String cid,String province,String msg) {
		//查找今日各条件已经成功的条数
		int result=0;
		Connection connA = null;
		PreparedStatement pstmtA = null;
		ResultSet rsA=null;
		try {
			connA = ConnectionFactory.getInstance().getConnection();
			pstmtA = connA.prepareStatement("select count(*) as num from charge where ok=1 and sid=? and cid=? and province=? and msg like ? and TO_DAYS(date_time)=TO_DAYS(NOW())");
			pstmtA.setString(1,sid);
			pstmtA.setString(2,cid);
			pstmtA.setString(3,province);
			pstmtA.setString(4,msg+"%");
			rsA=pstmtA.executeQuery();
			if(rsA.next()){
				result=rsA.getInt("num");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			ConnectionFactory.close(rsA,pstmtA, connA);
		}
		return result;
	}*///lsl
}
