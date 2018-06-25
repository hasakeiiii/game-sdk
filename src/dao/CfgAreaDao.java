package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import db.ConnectionFactory;

public class CfgAreaDao {
    private static final long serialVersionUID = -2528402884125283169L;

	public String msg = "";

	@SuppressWarnings("unchecked")
	public void setArea(String tableName) {
		// 防止连接时间过长造成连接中断
		System.out.println("更新"+tableName+"地区信息");
		Connection conn = null;
		ResultSet rsA = null;
		ResultSet rsB = null;
		Statement stmt = null;
		PreparedStatement pstmtA = null;
		HashMap map=new HashMap();
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			pstmtA = conn.prepareStatement("select left(mobile,7) as mobile from "+tableName+" where city is null and mobile<>'' group by left(mobile,7)");
			stmt = conn.createStatement();
			rsA = pstmtA.executeQuery();
			while (rsA.next()) {
				int mobile=rsA.getInt("mobile");
				String oper=(mobile+"").substring(0,3);
				String sql="select province,city from cfg_area_cmc where mobile="+mobile;
				if(common.Constant.CNC.indexOf(oper)>-1){
					sql="select province,city from cfg_area_cnc where mobile="+mobile;
				}
				else if(common.Constant.CTC.indexOf(oper)>-1){
					sql="select province,city from cfg_area_ctc where mobile="+mobile;
				}
				rsB = stmt.executeQuery(sql);
				if(rsB.next()){
					String province=rsB.getString("province");
					String city=rsB.getString("city");
					map.put(mobile, province+"_"+city);
				}
				else{
					map.put(mobile, "undefind_undefind");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rsA != null) {
					rsA.close();
					rsA = null;
				}
				if (rsB != null) {
					rsB.close();
					rsB = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (pstmtA != null) {
					pstmtA.close();
					pstmtA = null;
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				System.out.println("CfgAreaDao.close error:" + e);
			}
		}
		System.out.println("更新"+tableName+"地区开始:"+map.size());
		PreparedStatement pstmtC = null;
		try {
			conn = ConnectionFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			pstmtC = conn.prepareStatement("update "+tableName+" set province=?,city=? where left(mobile,7)=?");
			Iterator<Entry<Integer, String>> it = map.entrySet().iterator();
			int i=1;
			while (it.hasNext()) {
				Entry<Integer,String> entry = it.next();  
				int mobile=entry.getKey();
				String city=entry.getValue();
				String[] arr=city.split("_");
				pstmtC.setString(1, arr[0]);
				pstmtC.setString(2, arr[1]);
				pstmtC.setInt(3, mobile);
				pstmtC.addBatch();
				if(i%200==0){
					pstmtC.executeBatch();
					conn.commit();
				}
				i++;
			}
			pstmtC.executeBatch();
			conn.commit();
			System.out.println("更新"+tableName+"地区成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(pstmtC, conn);
		}
	}
	
	public static void main(String[] args) {
		long beg = System.currentTimeMillis();
		CfgAreaDao cfgAreaDao = new CfgAreaDao();
		cfgAreaDao.setArea("user_sms");
		System.out.println("ok");
		long end = System.currentTimeMillis();
		System.out.println(end - beg);
	}
}