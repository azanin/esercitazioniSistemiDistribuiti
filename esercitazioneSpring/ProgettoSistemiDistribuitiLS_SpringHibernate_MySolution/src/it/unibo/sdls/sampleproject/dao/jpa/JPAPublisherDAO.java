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
		Publisher publisher;
		Query query = entityManager.createQuery("SELECT p FROM Publisher p WHERE p.id = :id");
		query.setParameter("id", id);
		try {
			publisher = (Publisher) query.getSingleResult();
		} catch (NoResultException e) {
			logger.error(e.getMessage());
			publisher = null;
		} catch (NonUniqueResultException e) {
			logger.error(e.getMessage());
			publisher = null;
		}
		return publisher;
	}

	public Publisher findPublisherByName(String name) {
		Publisher publisher;
		Query query = entityManager.createQuery("SELECT p FROM Publisher p WHERE p.name = :name");
		query.setParameter("name", name);
		try {
			publisher = (Publisher) query.getSingleResult();
		} catch (NoResultException e) {
			logger.info(e.getMessage());
			publisher = null;
		} catch (NonUniqueResultException e) {
			logger.info(e.getMessage());
			publisher = null;
		}
		return publisher;
	}

	public int insertPublisher(Publisher publisher) {
		int operationResult;
		try {
			entityManager.persist(publisher);
			operationResult = 1;
		} catch (EntityExistsException e) {
			logger.error(e.getMessage());
			operationResult = -1;
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
			operationResult = -1;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			operationResult = -1;
		} catch (PersistenceException e) {
			logger.error(e.getMessage());
			operationResult = -1;
		}
		return operationResult;
	}



	public int removePublisherByName(String name) {
		// TODO Auto-generated method stub
		return 0;
	}



	public int removePublisherById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}



	public List<Publisher> findAllPublishers() {
		Query query = entityManager.createQuery("SELECT p FROM Publisher p");
		return query.getResultList();
	}

}
