package db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactoryC3P0 {   
    
    private static ComboPooledDataSource dataSource;
    static   {       
    	try {
    		dataSource = new ComboPooledDataSource();
            dataSource.setDriverClass("com.mysql.jdbc.Driver");
     	    dataSource.setJdbcUrl("jdbc:mysql://localhost/dingxue?autoReconnect=true&useUnicode=true&rewriteBatchedStatements=true");
            dataSource.setUser("root");
            dataSource.setPassword("jsbgsnmm38");
            dataSource.setMaxPoolSize(500);
            dataSource.setMinPoolSize(30);
            dataSource.setInitialPoolSize(100);

		} catch (Exception e) {
			e.printStackTrace();
		} 
    }  

    public static synchronized final Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}    