package it.unibo.sdls.sampleproject.dao;

public abstract class DAOFactory {
	
	// List of supported DAO types
	public static final int HSQLDB = 1;
	public static final int MYSQL = 2;
	
	public static DAOFactory getDAOFactory(int whichFactory) {
		try {
			switch ( whichFactory ) {
			case HSQLDB:
				return (DAOFactory) Class.forName("it.unibo.sdls.sampleproject.dao.hsqldb.HsqldbDAOFactory").newInstance();
			case MYSQL:
				return (DAOFactory) Class.forName("it.unibo.sdls.sampleproject.dao.mysql.MySqlDAOFactory").newInstance();
			default:
				return null;
			}
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public abstract AuthorDAO getAuthorDAO();
	
	public abstract BookDAO getBookDAO();
	
	public abstract PublisherDAO getPublisherDAO();
	
	
	

}
