package com.dis.util;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocator {

	private Context initalContext;

	private Map<String, DataSource> cache;

	private static ServiceLocator ourInstance = new ServiceLocator();

	public static ServiceLocator getInstance() {
		return ourInstance;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ServiceLocator() {
		try {
			this.initalContext = new InitialContext();
			this.cache = Collections.synchronizedMap(new HashMap());

		} catch (NamingException ex) {
            System.out.println("Error in CTX lookup: ServiceLocator()");
            ex.printStackTrace();
		}

	}

	public DataSource getDataSource(String dataSourceName) {

		DataSource datasource = null;
		
		try {

			if (this.cache.containsKey(dataSourceName))
				datasource = (DataSource) this.cache.get(dataSourceName);

			else {
				Context envContext = (Context) initalContext.lookup("java:comp/env");
				datasource = (DataSource) envContext.lookup(dataSourceName);
				this.cache.put(dataSourceName, datasource);
			}

		}
		catch (NamingException ex)
		{
			System.out.println("Error buscando en CTX: getDataSource()");
			ex.printStackTrace();
		}

		return datasource;

	}

	
}