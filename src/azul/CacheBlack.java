package azul;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import db.ConnectionFactory;

public class CacheBlack {
	/*public static ArrayList<String> blackList=null;
	public static void initBlackList() {
		System.out.println("更新黑名单列表");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			blackList = new ArrayList<String>();
			conn = ConnectionFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from cfg_black");
			while (rs.next()) {
				String mobile=rs.getString("mobile");
				blackList.add(mobile);
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			ConnectionFactory.close(rs,stmt,conn);
		}
	}*///lsl
}
