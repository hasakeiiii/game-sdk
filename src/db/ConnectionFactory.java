package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import util.ConstValue;
import util.DebuUtil;
import util.SaConfiguration;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;


//如果需要更换数据库，就在本类中切换
public class ConnectionFactory {   
    private static BoneCP dataSource;
    static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
    //
	private ConnectionFactory() {
		try {
			 BoneCPConfig config = new BoneCPConfig();  
        	 //local
			 //config.setJdbcUrl("jdbc:mysql://localhost/ztysp?rewriteBatchedStatements=true"); 
        	 //config.setUsername("sp");
        	 //config.setPassword("sp");
        	 //config
        	 /*config.setJdbcUrl("jdbc:mysql://119.147.23.178:3306/sp?rewriteBatchedStatements=true"); 
        	 config.setUsername("root");
        	 config.setPassword("rootcdma");*/////
        	 //test
			 /*if(ConstValue.DEBUG == 1)
			 {
				 //211.154.152.59
        	    config.setJdbcUrl("jdbc:mysql://localhost/sdkdata?rewriteBatchedStatements=true"); 
				 //config.setJdbcUrl("jdbc:mysql://localhost/sdkdatabk?rewriteBatchedStatements=true"); 
				  //config.setJdbcUrl("jdbc:mysql://localhost/sdkdata?rewriteBatchedStatements=true"); 
			    //config.setJdbcUrl("jdbc:mysql://183.232.69.61:3306/sdkdata?rewriteBatchedStatements=true");//平台
        	    //config.setJdbcUrl("jdbc:mysql://211.154.152.59:3306/sdkdata?rewriteBatchedStatements=true");//单机
        	    //119.29.15.70
        	    //config.setJdbcUrl("jdbc:mysql://119.29.15.70:3306/sdkdata?rewriteBatchedStatements=true");//单机2
        	    config.setUsername("root");
        	    config.setPassword("rootcdma");
			 }
			 else
			 {
				 //config.setJdbcUrl("jdbc:mysql://119.147.23.178:3306/lsl?rewriteBatchedStatements=true"); 
				 //config.setJdbcUrl("jdbc:mysql://183.232.69.61:3306/sdkdata?rewriteBatchedStatements=true");
				 if(ConstValue.RDR == 1)
				 {
					 
				    config.setJdbcUrl("jdbc:mysql://localhost/lsl?rewriteBatchedStatements=true"); 
				    //config.setJdbcUrl("jdbc:mysql://localhost/lsl?rewriteBatchedStatements=true"); 
				    
				 }
				 else
				 {
				   // config.setJdbcUrl("jdbc:mysql://localhost/sdkdata?rewriteBatchedStatements=true");
				    config.setJdbcUrl("jdbc:mysql://localhost/sdkdata?rewriteBatchedStatements=true");
				 }
	        	 config.setUsername("root");//
	        	 config.setPassword("rootcdma");
			 }*/
			 
			 //////////////////////////////////////////////////////////////
			 SaConfiguration configuration =SaConfiguration.getInstance("msmpay");
			 String JdbcUrl = configuration.getValue("JdbcUrl");
			 DebuUtil.log("JdbcUrl="+JdbcUrl);
			 config.setJdbcUrl(JdbcUrl);
			 config.setUsername("root");
        	 config.setPassword("rootcdma");
			 /////////////////////////////////////////////////////////////
				
        	 config.setPartitionCount(3);
        	 config.setMinConnectionsPerPartition(10);
        	 if(ConstValue.DEBUG == 1)
        	 {
        		 config.setMinConnectionsPerPartition(1);
        	 }
        	 config.setMaxConnectionsPerPartition(50);
        	 config.setAcquireIncrement(5);   
        	 config.setStatementsCacheSize(200);   
        	 config.setReleaseHelperThreads(3);   
        	 //config.set
        	 dataSource= new BoneCP(config);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static class SingletonHolder {
		static ConnectionFactory instance = new ConnectionFactory();
	}

	public static ConnectionFactory getInstance() {
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