package it.unibo.sdls.sampleproject.dao.jpa;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class JPABookDAO implements BookDAO {

	private Logger logger;

	@PersistenceContext
	private EntityManager entityManager;




	public void setLogger(Logger logger) {
		this.logger = logger;
	}



	public int addBook(Book book) {


		try {
			//book holds relationship to publisher and author

			//need to reattach entities to persistence context andn the save/merge book

			//attached publisher
			Publisher publisher = entityManager.find(Publisher.class, book.getPublisher().getId());
			book.setPublisher(publisher);

			Set<Author> attachedAuthor = new HashSet<Author>();		
			for(Author a : book.getAuthors())
				attachedAuthor.add(entityManager.find(Author.class, a.getId()));

			book.setAuthors(attachedAuthor);

			//try to get book
			Book attachedbook = this.getBookById(book.getId());
			if(attachedbook == null)
				entityManager.persist(book);
			else
				entityManager.merge(book);

			return book.getId();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return -1;
		}



	}



	public int deleteBook(int id) {

		try {
			Book book = entityManager.find(Book.class, id);
			entityManager.remove(book);
			return book.getId();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return -1;
		}
	}



	public Book getBookById(int id) {
		try {
			return entityManager.find(Book.class, id);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}



	public Book getBookByISBN10(String isbn10) {
		try {
			return (Book) entityManager.createQuery("SELECT b FROM Book b WHERE b.isbn10= :isbn").setParameter("isbn", isbn10).getSingleResult();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}



	public Book getBookByISBN13(String isbn13) {
		try {
			return (Book) entityManager.createQuery("SELECT b FROM Book b WHERE b.isbn13= :isbn").setParameter("isbn", isbn13).getSingleResult();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}



	public Book getBookByTitle(String title) {
		try {
			return (Book) entityManager.createQuery("SELECT b FROM Book b WHERE b.title= :title").setParameter("title", title).getSingleResult();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}



	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		try {
			return entityManager.createQuery("FROM Book").getResultList();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}



	public List<Book> getAllBooksByAuthor(Author author) {
		// TODO Auto-generated method stub
		try {
			String queryString = 	"SELECT b " +
					"FROM Book b " +
					"JOIN b.authors a " +
					"WHERE a.id = "+author.getId();
			Query query = entityManager.createQuery(queryString);
			return (List<Book>) query.getResultList();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}



	public List<Book> getAllBooksByPublisher(Publisher publisher) {
		
		try {
			String queryString = "SELECT b FROM Book b JOIN b.publisher p WHERE p.id= :id";
			Query query = entityManager.createQuery(queryString).setParameter("id", publisher.getId());
			return query.getResultList();
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}


}
