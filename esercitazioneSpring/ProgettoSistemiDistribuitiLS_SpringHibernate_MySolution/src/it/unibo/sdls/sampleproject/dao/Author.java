package it.unibo.sdls.sampleproject.dao;

import java.io.Serializable;
import java.util.Set;

public class Author implements Serializable {
	
	private static final long serialVersionUID = 1L;

	protected int id;
	
	protected String name;

	protected Set<Book> books;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

}
