package it.unibo.sdls.sampleproject.dao.ejb3;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.Publisher;
@Stateless(name="EJBBookDAO")
@Local(BookDAO.class)
@Remote(BookDAO.class)
public class EJBBookDAO implements BookDAO{
	
	@PersistenceContext(name="SampleProjectUnit")
	EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int addBook(Book book) {
		//riattacco i publisher al contesto di persistenza
		Publisher publisher = book.getPublisher();
		book.setPublisher(em.merge(publisher));
		em.persist(book);
		return book.getId();
	}
	//se rimuovo il libro elimino anche il suo editore e autori
	public int deleteBook(int id) {
		Book book = em.find(Book.class, id);
		em.remove(book);
		return book.getId();	
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Book getBookById(int id) {
		return em.find(Book.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Book getBookByISBN10(String isbn10) {
		Query q = em.createQuery("SELECT b FROM Book b WHERE b.isbn10= :isbn10").setParameter("isbn10", isbn10);
		Book book = (Book) q.getSingleResult();
		return book;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Book getBookByISBN13(String isbn13) {
		Query q = em.createQuery("SELECT b FROM Book b WHERE b.isbn13= :isbn13").setParameter("isbn13", isbn13);
		Book book = (Book) q.getSingleResult();
		return book;
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Book getBookByTitle(String title) {
		return (Book) em.createQuery("SELECT b FROM Book b WHERE b.title= :title").setParameter("title", title).getSingleResult();
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Book> getAllBooks() {
		return em.createQuery("FROM Book b ").getResultList();

	}
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Book> getAllBooksByAuthor(Author author) {
		if (author != null){
			em.merge(author); // riattacco l'autore al contesto di persistenza con una merge
			return em.createQuery("SELECT DISTINCT (b) FROM Book b JOIN FETCH b.authors JOIN FETCH b.publisher WHERE :author MEMBER OF b.authors").
				setParameter("author", author).getResultList();
		}
		else 
			return em.createQuery("SELECT DISTINCT (b) FROM Book b JOIN FETCH b.authors JOIN FETCH b.publisher").getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Book> getAllBooksByPublisher(Publisher publisher) {
		return em.createQuery("FROM Book b WHERE :publisherId = b.publisher.id").
				setParameter("publisherId", publisher.getId()).getResultList();
	}

}
