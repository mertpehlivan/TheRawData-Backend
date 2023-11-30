package com.mertdev.therawdata.entities.concretes;

import java.util.Date;

import com.mertdev.therawdata.entities.abstracts.Publication;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chapterInBooks")
public class ChapterInBook extends Publication{
	private String chapterNumber;
	private String bookTitle;
	private Date date;
	private String pages;
	private String doi;
	private String publisher;
	private String isbn;
	private String editor;
	
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
	
	
}
