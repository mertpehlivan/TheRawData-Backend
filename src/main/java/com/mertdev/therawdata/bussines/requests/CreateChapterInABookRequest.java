package com.mertdev.therawdata.bussines.requests;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChapterInABookRequest {
	private String title;
	private String chapterNumber;
	private String bookTitle;
	private Date date;
	private String pages;
	private String doi;
	private String publisher;
	private String isbn;
	private String editor;
	private List<String> authors;
}
