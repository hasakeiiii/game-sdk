package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

//dbcp���ӳش���
public abstract class ConnectionFactoryDCBP {
	
	//private static String URL = "";
	//private static String USER = "";
	//private static String PASS = "";
	private static String URL = "jdbc:mysql://119.145.9.39:3306/wuzhou";
	private static String USER = "root";
	private static String PASS = "jsbgsnmm38";
	
	static DataSource instance = setupDataSource();
	
	public ConnectionFactoryDCBP() {
	}
    //ҳ��õ����ӵķ���
	public static synchronized Connection getConnection() throws SQLException {
		return instance.getConnection();
	}

	public static DataSource getDataSource() throws SQLException {
		return instance;
	}

	public static DataSource setupDataSource() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl(URL);
		ds.setUsername(USER);
		ds.setPassword(PASS);
		
		ds.setInitialSize(4);
		ds.setMaxActive(32);
		ds.setMaxIdle(8);
		ds.setMinIdle(4);
		ds.setMaxWait(16L);
		return ds;
	}

	public static void printDataSourceStats(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		System.out.println("NumActive: " + bds.getNumActive());
		System.out.println("NumIdle: " + bds.getNumIdle());
	}

	public static void shutdownDataSource(DataSource ds) throws SQLException {
		BasicDataSource bds = (BasicDataSource) ds;
		bds.close();
	}
	
	public static void main(String args[]) {
		try {
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
			printDataSourceStats(getDataSource());
			System.out.println("Creating statement.");
			stmt = conn.createStatement();
			System.out.println("Executing statement.");
			rset = stmt.executeQuery("select * from blog");
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
			try {
				printDataSourceStats(getDataSource());
			} catch (SQLException ex) {
			}
		} catch (Exception e) {
		}

	}
}
