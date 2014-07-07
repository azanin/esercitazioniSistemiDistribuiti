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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name="Book")
public class Book {
	
	/**
	 * Serial Version UID 
	 */
	private static final long serialVersionUID = 6476221132667629595L;
	
	@Id
	@GeneratedValue
	@Column(name="book_id")
	protected int id;

	@Column(unique=true)
	protected String title;
	@Column(unique=true)
	protected String isbn10;
	@Column(unique=true)
	protected String isbn13;
	
	@ManyToMany(fetch=FetchType.EAGER,mappedBy="books",cascade={CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE})
	protected Set<Author> authors;
	
	@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.MERGE})
	@JoinColumn(name="publisher_id")
	protected Publisher publisher;
	
	// ------------------------------------------
	// Constructors
	// ------------------------------------------
	
	// default
	public Book() {}
	
	// ------------------------------------------
	// Getter and Setter methods
	// ------------------------------------------

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIsbn10() {
		return isbn10;
	}

	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	public String getIsbn13() {
		return isbn13;
	}

	public void setIsbn13(String isbn13) {
		this.isbn13 = isbn13;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}	
	
}
