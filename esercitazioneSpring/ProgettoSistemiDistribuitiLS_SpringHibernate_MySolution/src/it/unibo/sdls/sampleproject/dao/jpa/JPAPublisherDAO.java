package it.unibo.sdls.sampleproject.dao.jpa;

import it.unibo.sdls.sampleproject.dao.Publisher;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 * 	The @PersistenceContext annotation ensures automatic EntityManager injection by 
 * 	the Spring Container.
 *  The @Transactional annotation ensures that the persistence operations within a DAO 
 *  method will by executed in the same transaction and hence by the same entity manager.
 *	
 */
@Transactional
public class JPAPublisherDAO implements PublisherDAO {
	
	private Logger logger;
	
	@PersistenceContext
	private EntityManager entityManager;

	


	

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	

	public Publisher findPublisherById(int id) {
		try {
			return entityManager.find(Publisher.class, id);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public Publisher findPublisherByName(String name) {
		Query query = entityManager.createQuery("SELECT p FROM Publisher p WHERE p.name = :name");
		query.setParameter("name", name);
		try {
			return (Publisher) query.getSingleResult();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
		
	}

	public int insertPublisher(Publisher publisher) {
		try {
			entityManager.persist(publisher);
			return publisher.getId();
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return -1;
		}
	}



	public int removePublisherByName(String name) {
		try {
			Publisher publisher = (Publisher) entityManager.createQuery("SELECT p FROM Publisher p WHERE p.name= :name").setParameter("name", name).getSingleResult();
			entityManager.remove(publisher);
			return publisher.getId();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return -1;
		}
	}



	public int removePublisherById(int id) {
		try {
			Publisher publisher = entityManager.find(Publisher.class, id);
			entityManager.remove(publisher);
			return publisher.getId();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return -1;
		}
	}



	public List<Publisher> findAllPublishers() {
		Query query = entityManager.createQuery("SELECT p FROM Publisher p");
		return query.getResultList();
	}

}
