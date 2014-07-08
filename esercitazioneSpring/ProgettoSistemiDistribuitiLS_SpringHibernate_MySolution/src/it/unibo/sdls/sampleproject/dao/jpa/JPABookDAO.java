package it.unibo.sdls.sampleproject.dao.jpa;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class JPABookDAO implements BookDAO {
	
	private Logger logger;

	private EntityManager entityManager;



	public void setLogger(Logger logger) {
		this.logger = logger;
	}



	public int addBook(Book book) {
		// TODO Auto-generated method stub
		return 0;
	}



	public int deleteBook(int id) {
		// TODO Auto-generated method stub
		return 0;
	}



	public Book getBookById(int id) {
		// TODO Auto-generated method stub
		return null;
	}



	public Book getBookByISBN10(String isbn10) {
		// TODO Auto-generated method stub
		return null;
	}



	public Book getBookByISBN13(String isbn13) {
		// TODO Auto-generated method stub
		return null;
	}



	public Book getBookByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<Book> getAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}



	public List<Book> getAllBooksByAuthor(Author author) {
		// TODO Auto-generated method stub
		return null;
	}



	public List<Book> getAllBooksByPublisher(Publisher publisher) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
