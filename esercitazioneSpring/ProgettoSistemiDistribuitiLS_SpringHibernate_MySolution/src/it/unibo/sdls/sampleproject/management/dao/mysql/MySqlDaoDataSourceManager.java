package it.unibo.sdls.sampleproject.management.dao.mysql;

import org.apache.commons.dbcp.BasicDataSource;

import it.unibo.sdls.sampleproject.management.dao.DaoDataSourceManagerImpl;

public class MySqlDaoDataSourceManager extends DaoDataSourceManagerImpl{

	@Override
	public void init() {
		dataSource = (BasicDataSource) applicationContext.getBean("mysqlDataSource");
		
	}

}
