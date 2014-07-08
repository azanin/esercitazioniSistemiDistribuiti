package it.unibo.sdls.sampleproject.management.dao;

import org.springframework.context.ApplicationContextAware;

public interface DaoDataSourceManager extends ApplicationContextAware {
	
	public String getApplicationContextInfo();
	
	public int setMaxActive(int maxActive);
	public int getMaxActive();
	
	public int setMaxIdle(int maxIdle);
	public int getMaxIdle();
	
	public int setMinIdle(int minIdle);
	public int getMinIdle();
	
	public int getNumActive();
	
	public int getNumIdle();
	
	public void close();

}
