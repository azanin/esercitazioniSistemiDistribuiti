package it.unibo.sdls.sampleproject.dao.ejb3;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.AuthorDAO;
import it.unibo.sdls.sampleproject.logging.OperationLogger;

@Stateless(name="EJBAuthorDAO")
@Local(AuthorDAO.class)
@Remote(AuthorDAO.class)
public class EJBAuthorDAO implements AuthorDAO
{
	@PersistenceContext(name="SampleProjectUnit")
	EntityManager em;
	
	@Interceptors(value=OperationLogger.class)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int insertAuthor(Author author) {
		em.persist(author);
		return author.getId();
	}

	//finita la transazione oggetto diventa detached
	//a fine transazione viene eseguito il commit
	public int removeAuthorByName(String name) {
		Query q = em.createQuery("SELECT a FROM Author a WHERE a.name= :name").setParameter("name", name);
		Author author = (Author) q.getSingleResult();
		em.remove(author);
		return author.getId();	
	}

	//ricordarsi che eliminare un autore elimina in cascata anche i suoi libri
	public int removeAuthorById(int id) {
		Author author = em.find(Author.class, id);
		em.remove(author);
		return author.getId();
	}

	//ritornerà anche i libri caricamento eager
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Author findAuthorByName(String name) {
		Query q = em.createQuery("SELECT a FROM Author a WHERE a.name= :name").setParameter("name", name);
		Author author = (Author) q.getSingleResult();
		return author;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Author findAuthorById(int id) {
		Author author = em.find(Author.class, id);
		return author;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Author> findAllAuthors() {
		return em.createQuery("from Author").getResultList();
	}

}
