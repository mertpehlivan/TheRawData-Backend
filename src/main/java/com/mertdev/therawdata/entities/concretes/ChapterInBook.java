package com.mertdev.therawdata.entities.concretes;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.abstracts.PublicationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "chapterInBooks")
public class ChapterInBook extends Publication implements PublicationType{

	private String chapterNumber;
	private String bookTitle;
	private Date date;
	private String pages;
	private String doi;
	private String publisher;
	private String isbn;
	private String editor;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", updatable = false)
    private Date creationTime;

    @PrePersist
    protected void onCreate() {
        creationTime = new Date();
    }

	public String getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(String chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPages() {
		return pages;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public PublicationType getObject() {
		return this;
	}

	@Override
	public String getSummary() {
		return String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
			    getTitle(),
			    getChapterNumber(),
			    getBookTitle(),
			    getDate().toString(),
			    getPages(),
			    getDoi(),
			    getPublisher(),
			    getIsbn(),
			    getEditor(),
			    getPublicationAuthors().stream()
			                            .map(author -> author.getAuthor().getFirstname() + " " + author.getAuthor().getLastname())
			                            .collect(Collectors.joining(", "))
			);
	}


}
