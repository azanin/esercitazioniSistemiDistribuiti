package it.unibo.sdls.sampleproject.test.jpa;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.Publisher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JPAAuthorDAOTest extends TestCommon{
	
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
	public void findAuthorByName() {
		Author author = authorDAO.findAuthorByName(authorName1);
		Assert.assertEquals(authorName1, author.getName());
	}
	
	@Test
	public void findAllAuthors() {
		List<Author> authorsList = authorDAO.findAllAuthors();
		Assert.assertEquals(3, authorsList.size());
	}
	
	@Test
	public void removeAuthorById() {
		// since id is auto-incremented by the DBMS we necessarily have to
		// fetch Author object from the DB to know its id and then 
		// attempt its removal
		Author author = authorDAO.findAuthorByName(authorName1);
		int id = author.getId();
		authorDAO.removeAuthorById(id);
		Author retrievedAuthor = authorDAO.findAuthorById(id); 
		Assert.assertNull(retrievedAuthor);
	}
	
	@Test
	public void removeAuthorByName() {
		authorDAO.removeAuthorByName(authorName1);
		Author retrievedAuthor = authorDAO.findAuthorByName(authorName1); 
		Assert.assertNull(retrievedAuthor);
	}
	
	//@Test
	public void deletingAuthorDoesNotDeleteBooks() {
		Publisher publisher = new Publisher();
		publisher.setName("Wrox");
		
		Book book1 = new Book();
		book1.setTitle("J2EE Development without EJB");
		book1.setPublisher(publisher);
		
		Book book2 = new Book();
		book2.setTitle("J2EE Design and Development");
		book2.setPublisher(publisher);
		
		Set<Book> books = new HashSet<Book>();
		books.add(book1);
		books.add(book2);
		
		Author author = new Author();
		author.setName("Rod Johnson");
		author.setBooks(books);
		
		authorDAO.insertAuthor(author);
		
		Author retrievedAuthor = authorDAO.findAuthorByName(authorName1);
		authorDAO.removeAuthorByName(authorName1);
		// checking that Author does not exist anymore in the DB
		retrievedAuthor = authorDAO.findAuthorByName(authorName1);
		Assert.assertNull(retrievedAuthor);
		// checking that Books still exist in the DB
		Assert.assertNotNull(bookDAO.getBookByTitle("J2EE Development without EJB"));
		Assert.assertNotNull(bookDAO.getBookByTitle("J2EE Design and Development"));
		
	}
	
	@Test
	public void insertingAuthorWithBooksAlsoPersistsBooks() {
		
		// books are set on the inverse side of the
		// many-to-many relationship
		
		Publisher publisher = new Publisher();
		publisher.setName("Wrox");
		
		Book book1 = new Book();
		book1.setTitle("J2EE Development without EJB");
		book1.setPublisher(publisher);
		
		Book book2 = new Book();
		book2.setTitle("J2EE Design and Development");
		book2.setPublisher(publisher);
		
		Set<Book> books = new HashSet<Book>();
		books.add(book1);
		books.add(book2);
		
		Author author = new Author();
		author.setName("Rod Johnson");
		author.setBooks(books);
		
		authorDAO.insertAuthor(author);
		
		Author retrievedAuthor = authorDAO.findAuthorByName("Rod Johnson");
		
		for (Book book : retrievedAuthor.getBooks()) {
			Assert.assertTrue(
					book.getTitle().equals("J2EE Development without EJB") 
					|| 
					book.getTitle().equals("J2EE Design and Development")
			);
		}
		
		
	}
}
