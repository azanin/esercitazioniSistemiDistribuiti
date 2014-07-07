package it.unibo.sdls.sampleproject.dao.ejb3;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.system.server.ServerInfo;

import it.unibo.sdls.sampleproject.dao.AuthorDAO;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.DAOFactory;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;



public class EJB3DaoFactory extends DAOFactory {
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static InitialContext getInitialContext() throws Exception
	{
		Hashtable properties = new Hashtable(); 
		//jnp is the jBoss implementation of Jndi Context interface
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		properties.put(Context.PROVIDER_URL, new ServerInfo().getHostAddress()+":1099");
		
		InitialContext initialContext = new InitialContext(properties);
		return initialContext;
		
		

	}

	@Override
	public AuthorDAO getAuthorDAO()  {
		try {
			InitialContext context = getInitialContext();
			AuthorDAO authorDAO = (AuthorDAO) context.lookup("Progetto_EJB3/EJB3AuthorDAO/remote");
			return authorDAO;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BookDAO getBookDAO() 
	{
		try 
		{
			InitialContext context = getInitialContext();
			BookDAO bookDAO = (BookDAO) context.lookup("Progetto_EJB3/EJB3BookDAO/remote");
			return bookDAO;			
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

	@Override
	public PublisherDAO getPublisherDAO() {
		try 
		{
			InitialContext context = getInitialContext();
			PublisherDAO publisherDAO = (PublisherDAO) context.lookup("Progetto_EJB3/EJB3PublisherDAO/remote");
			return publisherDAO;			
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

}
