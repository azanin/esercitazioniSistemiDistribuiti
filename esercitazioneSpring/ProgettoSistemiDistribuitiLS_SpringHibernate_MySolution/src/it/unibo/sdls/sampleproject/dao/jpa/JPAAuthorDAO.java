package it.unibo.sdls.sampleproject.dao.jpa;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.AuthorDAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class JPAAuthorDAO implements AuthorDAO {

	private Logger logger;

	@PersistenceContext
	private EntityManager entityManager;

	public int insertAuthor(Author author) {
		try
		{
			entityManager.persist(author);
			return author.getId();
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return -1;

		}

	}

	public int removeAuthorByName(String name) {
		try
		{
			Author author = (Author) entityManager.createQuery("SELECT a FROM Author a WHERE a.name= :name").setParameter("name", name).getSingleResult();
			entityManager.remove(author);		
			return author.getId();
		}
		catch(Exception e )
		{
			logger.info(e.getMessage());
			return -1;

		}
	}

	public int removeAuthorById(int id) {
		try
		{
		Author author = entityManager.find(Author.class, id);
		entityManager.remove(author);
		return author.getId();
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return -1;
		}
	}

	public Author findAuthorByName(String name) {
		try
		{
			Author author = (Author) entityManager.createQuery("SELECT a FROM Author a WHERE a.name= :name").setParameter("name", name).getSingleResult();
			return author;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}

	public Author findAuthorById(int id) {
		try
		{
		Author author = entityManager.find(Author.class, id);
		return author;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}

	public List<Author> findAllAuthors() {
		try
		{
		List<Author> authors = entityManager.createQuery("FROM Author").getResultList();
		return authors;
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			return null;
		}
	}

	
	public void setLogger(Logger logger) {
		this.logger = logger;
	}




}
