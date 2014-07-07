package it.unibo.sdls.sampleproject.dao;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity(name="Author")
@Table(name="authors")
public class Author {
	
	
	private static final long serialVersionUID = 3103905839384102117L;

	@Id
	@Column(name="author_id")
	@GeneratedValue
	protected int id;
	
	@Column(unique=true)
	protected String name;
	
	
	public Author() {}
	
	public Author(String name) {
		this.name = name;
	}
	
	public Author(String name, Set<Book> books) {
		this.name = name;
		this.books = books;
	}

	@ManyToMany(fetch=FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinTable(name="authors_books",
	joinColumns=@JoinColumn(name="author_id"),
	inverseJoinColumns=@JoinColumn(name="book_id"))	
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
