package com.dis.util;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceBD {
	
	public static Connection openConnection(){
		Connection con = null;
		try {
		DataSource ds = ServiceLocator.getInstance().getDataSource("jdbc/myDataSource");
		con = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
		
	}
	
	public static void closeConnection(Connection con){
		try {
			if(con!=null && !con.isClosed()){con.close();}
		} catch (SQLException e) {
			System.out.println("Error cerrando conexión");
		}
	}

}
