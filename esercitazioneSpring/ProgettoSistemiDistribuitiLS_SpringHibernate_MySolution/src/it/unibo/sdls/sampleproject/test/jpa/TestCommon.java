package it.unibo.sdls.sampleproject.test.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.AuthorDAO;
import it.unibo.sdls.sampleproject.dao.Book;
import it.unibo.sdls.sampleproject.dao.BookDAO;
import it.unibo.sdls.sampleproject.dao.Publisher;
import it.unibo.sdls.sampleproject.dao.PublisherDAO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@ContextConfiguration(locations={
		"file:web/WEB-INF/spring/dataSources.xml",
		"file:web/WEB-INF/spring/jpa.xml",
		"file:web/WEB-INF/spring/services.xml",
})
//see jpa.xml
@TransactionConfiguration(transactionManager="jpaTransactionManager",defaultRollback=true)
public class TestCommon extends AbstractTransactionalJUnit4SpringContextTests
{
	protected static final String bookTitle1 = "Spring in Action";
	protected static final String bookTitle2 = "XDoclet in Action";
	protected static final String authorName1 = "Craig Walls";
	protected static final String authorName2 = "Ryan Breidenbach";
	protected static final String authorName3 = "Norman Richards";
	protected static final String[] book1_authorsNames = {authorName1, authorName2};
	protected static final String[] book2_authorsNames = {authorName1, authorName3};
	protected static final String publisherName1 = "Manning";
	protected static final String publisherName2 = "Wrox";
	protected static final String isbn10 = "123456789";
	protected static final String isbn13 = "987654321";
	
	@Autowired
	protected AuthorDAO authorDAO;
	@Autowired
	protected PublisherDAO publisherDAO;
	@Autowired
	protected BookDAO bookDAO;
	
	/* (non-Javadoc)
	 * @see org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests#setDataSource(javax.sql.DataSource)
	 * Expects a DataSource bean to be defined in the Spring application context.
	 * This method has been overriden in order to avoid conflicts due to duplicate data sources definitions in the application context.
	 */
	@Override
	public void setDataSource(DataSource dataSource) {
		DataSource ds = (DataSource) applicationContext.getBean("mysqlDataSource");
		super.setDataSource(ds);
	}
	

	@Test
	public void autowiring() {
		Assert.assertNotNull(bookDAO);
		Assert.assertNotNull(authorDAO);
		Assert.assertNotNull(publisherDAO);
	}
	
	//------------------------
		// Helpers
		// -----------------------
		public Book createBook(String title, String[] authors, String publisherName, String isbn10, String isbn13) {
			Book book = new Book();
			book.setTitle(title);
			
			// Authors
			Set<Author> authorsSet = new HashSet<Author>();
			try {
				for(int i=0; ; i++) {
					// checking if author already exists in the DB
					Author author = authorDAO.findAuthorByName(authors[i]);
					if (author == null) {
						author = new Author();
						author.setName(authors[i]);
					}
					authorsSet.add(author);
				}
			} catch(ArrayIndexOutOfBoundsException e) {
				// ignore; this just means that i has reached the end of the array
			}
			book.setAuthors(authorsSet);
			
			// checking if publisher already exists in the DB
			Publisher publisher = publisherDAO.findPublisherByName(publisherName);
			if (publisher == null) {
				publisher = new Publisher();
				publisher.setName(publisherName);
			}
			book.setPublisher(publisher);
			
			book.setIsbn10(isbn10);
			book.setIsbn13(isbn13);
			
			return book;
		}
		
		public Author createAuthorWithoutBook(String name) {
			Author author = new Author();
			author.setName(name);
			return author;
		}
		
		public Publisher createPublisher(String name) {
			Publisher publisher = new Publisher();
			publisher.setName(name);
			return publisher;
		}

}
