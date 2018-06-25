package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public abstract class ConnectionFactoryBoneCP {
	
	public static BoneCP connectionPool = null;
	
	public ConnectionFactoryBoneCP() {
	}
	public static synchronized Connection getConnection() throws SQLException {
		return connectionPool.getConnection(); 
	}

	public static void init() 
    {
    	try {   
    	BoneCPConfig config = new BoneCPConfig();  
    	Class.forName("com.mysql.jdbc.Driver"); 
    	 config.setJdbcUrl("jdbc:mysql://localhost/dingxue?rewriteBatchedStatements=true"); 
    	 config.setUsername("root");
    	 config.setPassword("jsbgsnmm38");
    	 config.setMinConnectionsPerPartition(30);
    	 config.setMaxConnectionsPerPartition(200);
    	 config.setPartitionCount(3);
    	 connectionPool= new BoneCP(config);
        } catch (Exception e) {   
            System.out.println("连接获取失败"+e);
        }   
    }   
	
	public static void main(String args[]) {
		try {
			ConnectionFactoryBoneCP.init();
			Connection conn;
			Statement stmt;
			ResultSet rset;
			System.out.println("Setting up data source.");
			System.out.println("Done.");
			conn = null;
			stmt = null;
			rset = null;
			System.out.println("Creating connection.");
			conn = getConnection();
			//printDataSourceStats(getDataSource());
			System.out.println("Creating statement.");
			stmt = conn.createStatement();
			System.out.println("Executing statement.");
			rset = stmt.executeQuery("select * from ad");
			System.out.println("Results:");
			int numcols = rset.getMetaData().getColumnCount();
			for (; rset.next(); System.out.println("")) {
				for (int i = 1; i <= numcols; i++)
					System.out.print("\t" + rset.getString(i));

			}
			System.out.println("操作完毕");
			try {
				rset.close();
			}
			// Misplaced declaration of an exception variable
			catch (Exception e1) {
			}
			try {
				stmt.close();
			}
			// Misplaced declaration of an exception variable
			catch (Exception e2) {
			}
			try {
				conn.close();
			}
			// Misplaced declaration of an exception variable
			catch (Exception e3) {
			}
		} catch (Exception e) {
		}

	}
}
