package it.unibo.sdls.sampleproject.management.dao;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;


public abstract class DaoDataSourceManagerImpl implements DaoDataSourceManager{
	
	private Logger logger = Logger.getLogger(DaoDataSourceManagerImpl.class.getName());
	
	protected ApplicationContext applicationContext;
	protected BasicDataSource dataSource;
	
	//chi implementa init inizializza il datasource
	public abstract void init();

	public void setApplicationContext(ApplicationContext context) throws BeansException 
	{
		this.applicationContext = context;
		
	}

	public String getApplicationContextInfo() {
		return applicationContext.getDisplayName();
	}

	public int setMaxActive(int maxActive) {
		dataSource.setMaxActive(maxActive);
		return dataSource.getMaxActive();
	}

	public int getMaxActive() {
		return dataSource.getMaxActive();
	}

	public int setMaxIdle(int maxIdle) {
		dataSource.setMaxIdle(maxIdle);
		return maxIdle;
	}

	public int getMaxIdle() {
		return dataSource.getMaxIdle();
	}

	public int setMinIdle(int minIdle) {
		dataSource.setMinIdle(minIdle);
		return minIdle;
	}

	public int getMinIdle() {
		return dataSource.getMinIdle();
	}

	public int getNumActive() {
		return dataSource.getNumActive();
	}

	public int getNumIdle() {
		return dataSource.getNumIdle();
	}

	public void close() {
		try {
			dataSource.close();
		} catch (SQLException e) {
			logger.info(e.getMessage());
		}		
	}

}
