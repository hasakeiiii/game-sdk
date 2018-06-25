package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;

import azul.BaseDao;
import db.ConnectionFactory;
public class CfgLogDao extends BaseDao{
public CfgLogDao() {
	init();
}
//用于记录日志到数据库
public static void log(int mode,String param,String value){
	Connection conn = null;
	PreparedStatement pstmt = null;
	try {
		conn = ConnectionFactory.getInstance().getConnection();
		pstmt = conn.prepareStatement("insert into cfg_log (mode,param,value,date_time) values (?,?,?,now())");
		pstmt.setInt(1, mode);
		pstmt.setString(2, param);
		pstmt.setString(3, value);
		pstmt.executeUpdate();
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		ConnectionFactory.close(pstmt, conn);
	}
}
}