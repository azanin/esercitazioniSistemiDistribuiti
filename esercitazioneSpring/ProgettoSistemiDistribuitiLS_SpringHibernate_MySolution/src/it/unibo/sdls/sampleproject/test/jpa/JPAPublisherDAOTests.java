package it.unibo.sdls.sampleproject.test.jpa;

import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JPAPublisherDAOTests extends TestCommon {
	
	@Before
	public void init() {
		Publisher publisher1 = new Publisher(publisherName1);
		Book book1 = new Book();
		book1.setTitle(bookTitle1);
		Set<Book> booksSet1 = new HashSet<Book>();
		booksSet1.add(book1);
		publisher1.setBooks(booksSet1);
		publisherDAO.insertPublisher(publisher1);
		
		Publisher publisher2 = new Publisher(publisherName2);
		Book book2 = new Book();
		book2.setTitle(bookTitle2);
		Set<Book> booksSet2 = new HashSet<Book>();
		booksSet2.add(book2);
		publisher2.setBooks(booksSet2);
		publisherDAO.insertPublisher(publisher2);
	}
	
	@Test
	public void findPublisherByName() {
		Publisher publisher1 = publisherDAO.findPublisherByName(publisherName1);
		Assert.assertEquals(publisherName1, publisher1.getName());
		Publisher publisher2 = publisherDAO.findPublisherByName(publisherName2);
		Assert.assertEquals(publisherName2, publisher2.getName());
	}
	
	@Test
	public void findAllPublishers() {
		List<Publisher> publishersList = publisherDAO.findAllPublishers();
		Assert.assertEquals(2, publishersList.size());
	}
	
	@Test
	public void persistingPublisherAlsoPersistItsBooks() {
		Publisher publisher1 = publisherDAO.findPublisherByName(publisherName1);
		Set<Book> books = publisher1.getBooks();
		Assert.assertEquals(1, books.size());
		Book[] booksArray = books.toArray(new Book[0]);
		Assert.assertEquals(bookTitle1, booksArray[0].getTitle());
	}

}
