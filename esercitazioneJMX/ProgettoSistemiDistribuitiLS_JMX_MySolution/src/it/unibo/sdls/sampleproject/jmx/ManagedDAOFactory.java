package it.unibo.sdls.sampleproject.jmx;

import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.jdbc.pooled.PooledGenericJDBCDAOFactory;

import javax.servlet.ServletContext;

public class ManagedDAOFactory implements ManagedDAOFactoryMBean{
	
	private ServletContext servletContext;
	
	public ManagedDAOFactory(ServletContext servletContext)
	{
		this.servletContext = servletContext;
		DAOFactory daoFactory = DAOFactory.getDAOFactory(servletContext.getInitParameter("dao"));
		servletContext.setAttribute("daoFactory", daoFactory);	
	}
	

	public int getPoolSize() {
		PooledGenericJDBCDAOFactory daoFactory = (PooledGenericJDBCDAOFactory) servletContext.getAttribute("daoFactory");
		return  daoFactory.getPoolSize();
	}

	public void setPoolSize(int ps) {
		PooledGenericJDBCDAOFactory daoFactory = (PooledGenericJDBCDAOFactory) servletContext.getAttribute("daoFactory");
		daoFactory.setPoolSize(ps);
	}

	public long getMaxWait() {
		PooledGenericJDBCDAOFactory daoFactory = (PooledGenericJDBCDAOFactory) servletContext.getAttribute("daoFactory");
		return daoFactory.getMaxWait();
	}

	public void setMaxWait(long mw) {
		PooledGenericJDBCDAOFactory daoFactory = (PooledGenericJDBCDAOFactory) servletContext.getAttribute("daoFactory");
		daoFactory.setMaxWait(mw);		
	}

	public String using() {
		PooledGenericJDBCDAOFactory daoFactory = (PooledGenericJDBCDAOFactory) servletContext.getAttribute("daoFactory");
		return daoFactory.getClass().getCanonicalName();
	}

	public boolean switchTo(String className) {
		Object factory = null;
		try {
			factory = (PooledGenericJDBCDAOFactory) DAOFactory.getDAOFactory( className );
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if ( factory == null )
			return false;
		servletContext.setAttribute("daoFactory", factory);
		return true;
	}

}
