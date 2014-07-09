package it.unibo.sdls.sampleproject.management.dao.hsql;

import org.apache.commons.dbcp.BasicDataSource;

import it.unibo.sdls.sampleproject.management.dao.DaoDataSourceManagerImpl;

public class HsqlDaoDataSourceManager extends DaoDataSourceManagerImpl {

	@Override
	public void init() {
		dataSource = (BasicDataSource) applicationContext.getBean("hsqldbDataSource");		
	}

}
