package it.unibo.sdls.sampleproject.dao.ejb3;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unibo.sdls.sampleproject.dao.Publisher;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;

@Stateless(name="EJBPublisherDAO")
@Local(PublisherDAO.class)
@Remote(PublisherDAO.class)
public class EJBPublisherDAO implements PublisherDAO {

	@PersistenceContext(unitName="SampleProjectUnit")
	EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int insertPublisher(Publisher publisher) {
		em.persist(publisher);
		return publisher.getId();
	}

	
	public Publisher findPublisherByName(String name) {
		return (Publisher) em.createQuery("SELECT p FROM Producer p WHERE p.name = :name").setParameter("name", name).getSingleResult();
	}

	public Publisher findPublisherById(int id) {
		return em.find(Publisher.class, id);
	}

	public List<Publisher> findAllPublishers() {
		return em.createQuery("FROM Producer").getResultList();
	}

	public int removePublisherByName(String name) {
		Publisher p = findPublisherByName(name);
		em.remove(p);
		return p.getId();
	}

	public int removePublisherById(int id) {
		Publisher p = em.find(Publisher.class,id);
		em.remove(p);
		return p.getId();
	}

}
