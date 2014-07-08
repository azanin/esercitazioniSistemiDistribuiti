package it.unibo.sdls.sampleproject.dao.jpa;

import it.unibo.sdls.sampleproject.dao.Author;
import it.unibo.sdls.sampleproject.dao.AuthorDAO;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

public class JPAAuthorDAO implements AuthorDAO {

	private Logger logger;

	private EntityManager entityManager;

	public int insertAuthor(Author author) {
		return 0;
	}

	public int removeAuthorByName(String name) {
		return 0;
	}

	public int removeAuthorById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Author findAuthorByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Author findAuthorById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Author> findAllAuthors() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
