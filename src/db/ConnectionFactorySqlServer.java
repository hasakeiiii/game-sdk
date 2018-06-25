package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


//如果需要更换数据库，就在本类中切换
public class ConnectionFactorySqlServer {   
    private static BoneCP dataSource;
    static {
		try {
			Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver"); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
	private ConnectionFactorySqlServer() {
		try {
			 BoneCPConfig config = new BoneCPConfig();  
        	 //local
			 //config.setJdbcUrl("jdbc:mysql://localhost/ztysp?rewriteBatchedStatements=true"); 
        	 //config.setUsername("sp");
        	 //config.setPassword("sp");
        	 //config
			 
			 String url="jdbc:microsoft:sqlserver://192.168.0.26:1433;DatabaseName=inland"; 
        	 config.setJdbcUrl("jdbc:microsoft:sqlserver://192.168.1.181:1433;DatabaseName=sms2"); 
        	 config.setUsername("sa");
        	 config.setPassword("123456");
        	 //test
        	 //config.setJdbcUrl("jdbc:mysql://localhost/wuzhou?rewriteBatchedStatements=true"); 
        	 //config.setUsername("root");
        	 //config.setPassword("123456");
        	 config.setPartitionCount(3);
        	 config.setMinConnectionsPerPartition(10);
        	 config.setMaxConnectionsPerPartition(50);
        	 config.setAcquireIncrement(5);   
        	 config.setStatementsCacheSize(200);   
        	 config.setReleaseHelperThreads(3);   
        	 dataSource= new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static class SingletonHolder {
		static ConnectionFactorySqlServer instance = new ConnectionFactorySqlServer();
	}

	public static ConnectionFactorySqlServer getInstance() {
		return SingletonHolder.instance;
	}

	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
    
	public static void close(Statement stmt,Connection conn) {
		try {
			if (stmt != null)stmt.close();stmt = null;
			if (conn != null)conn.close();
		} catch (Exception e) {
			System.out.println("ConnectionFactory.close error:" + e);
		}
	}

	public static void close(ResultSet rs, Statement stmt,Connection conn) {
		try {
			if (rs != null)rs.close();
			if (stmt != null)stmt.close();
			if (conn != null)conn.close();
		} catch (Exception e) {
			System.out.println("ConnectionFactory.close error:" + e);
		}
	}

	public static void close(PreparedStatement pstmt,Connection conn) {
		try {
			if (pstmt != null)pstmt.close();
			if (conn != null)conn.close();
		} catch (Exception e) {
			System.out.println("ConnectionFactory.close error:" + e);
		}
	}

	public static void close(ResultSet rs,PreparedStatement pstmt,Connection conn) {
		try {
			if (rs != null)rs.close();
			if (pstmt != null)pstmt.close();
			if (conn != null)conn.close();
		} catch (Exception e) {
			System.out.println("ConnectionFactory.close error:" + e);
		}
	}
}   