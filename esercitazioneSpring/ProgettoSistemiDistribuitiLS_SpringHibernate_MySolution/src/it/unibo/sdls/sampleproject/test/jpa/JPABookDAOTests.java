package it.unibo.sdls.sampleproject.test.jpa;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JPABookDAOTests extends TestCommon {
	
	@Before
	public void init() {
		Publisher publisher = createPublisher(publisherName1);
		publisherDAO.insertPublisher(publisher);
		Author author1 = createAuthorWithoutBook(authorName1);
		Author author2 = createAuthorWithoutBook(authorName2);
		Author author3 = createAuthorWithoutBook(authorName3);
		authorDAO.insertAuthor(author1);
		authorDAO.insertAuthor(author2);
		authorDAO.insertAuthor(author3);
		Book book1 = createBook(bookTitle1, book1_authorsNames, publisherName1, isbn10, isbn13);
		Book book2 = createBook(bookTitle2, book2_authorsNames, publisherName1, isbn10, isbn13);
		bookDAO.addBook(book1);	
		bookDAO.addBook(book2);	
	}
	
	@Test 
	public void getBookByTitle() {
		Book book = bookDAO.getBookByTitle(bookTitle1);
		
		Set<Author> authors = book.getAuthors();
		String[] actualAuthors = new String[2];
		int index = 0;
		for(Iterator<Author> iterator = authors.iterator(); iterator.hasNext(); ) {
			actualAuthors[index] = iterator.next().getName();
			index++;
		}
		Arrays.sort(actualAuthors);
		
		Publisher publisher = book.getPublisher();
		
		Assert.assertEquals(bookTitle1, book.getTitle());
		Assert.assertArrayEquals(book1_authorsNames, actualAuthors);
		Assert.assertEquals(publisherName1, publisher.getName());
	}
	
	 @Test
	 public void deleteBook() {
		 // since id is auto-incremented by the DBMS we necessarily have to
		 // fetch the only Book object from the DB to know its id and then 
		 // attempt its removal
		 Book book = bookDAO.getBookByTitle(bookTitle1);
		 int id = book.getId();
		 bookDAO.deleteBook(id);
		 Book retrievedBook = bookDAO.getBookById(id);
		 Assert.assertNull(retrievedBook);
	 }
	 
	 @Test
	 public void deletingBookDoesNotDeleteAuthorsAndPublisher() {
		 Book book = bookDAO.getBookByTitle(bookTitle1);
		 int id = book.getId();
		 Set<Author> bookAuthors = book.getAuthors();
		 Publisher bookPublisher = book.getPublisher();
		 bookDAO.deleteBook(id);
		 Book retrievedBook = bookDAO.getBookById(id);
		 Assert.assertNull(retrievedBook);
		 // checking if authors still exist in the DB
		 for(Author author : bookAuthors) {
			 Author retrievedAuthor = authorDAO.findAuthorById(author.getId());
			 Assert.assertNotNull(retrievedAuthor);
		 }
		 // checking if publisher still exist in the DB
		 Publisher retrievedPublisher = publisherDAO.findPublisherById(bookPublisher.getId());
		 Assert.assertNotNull(retrievedPublisher);
	 }
	 
	 @Test
	 public void getAllBooksByAuthor() {
		 Author author = authorDAO.findAuthorByName(authorName1);
		 List<Book> booksbyAuthor = bookDAO.getAllBooksByAuthor(author);
		 Assert.assertEquals(2, booksbyAuthor.size());
		 for( Iterator<Book> i = booksbyAuthor.iterator(); i.hasNext(); ) {
			 Book b = i.next();
			 String actualBookTitle = b.getTitle();
			 Assert.assertTrue(actualBookTitle.equals(bookTitle1) || actualBookTitle.equals(bookTitle2));
		 }		 
	 }
	 
	 @Test
	 public void getAllBooksByPublisher() {
		 // Manning published 2 Books
		 Publisher publisher = publisherDAO.findPublisherByName(publisherName1);
		 List<Book> booksByPublisher = bookDAO.getAllBooksByPublisher(publisher);
		 Assert.assertEquals(2, booksByPublisher.size());
		 for( Iterator<Book> i = booksByPublisher.iterator(); i.hasNext(); ) {
			 Book b = i.next();
			 String actualBookTitle = b.getTitle();
			 Assert.assertTrue(actualBookTitle.equals(bookTitle1) || actualBookTitle.equals(bookTitle2));
		 }		 
	 }

}
