package it.unibo.sdls.sampleproject.dao;

import java.io.Serializable;
import java.util.Set;

public class Publisher {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6423572771817481733L;
	protected int id;
	protected String name;
	protected Set<Book> books;
	
	// ------------------------------------------
	// Constructors
	// ------------------------------------------
	public Publisher() {}
	
	public Publisher(String name) {
		this.name = name;
	}
	
	// ------------------------------------------
	// Getter and Setter methods
	// ------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}
	
}
