package com.mertdev.therawdata.bussines.responses;

import java.util.Date;
import java.util.UUID;

import com.mertdev.therawdata.bussines.responses.abstracts.PublicationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChapterInABookResponse implements PublicationResponse{
	private UUID id;
	private String title;
	private String chapterNumber;
	private String bookTitle;
	private Date date;
	private String pages;
	private String doi;
	private String publisher;
	private String isbn;
	private String editor;
	private String comment;
	private Date creationTime;
}
