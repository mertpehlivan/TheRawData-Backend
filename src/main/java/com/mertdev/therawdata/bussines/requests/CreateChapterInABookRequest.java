package com.mertdev.therawdata.bussines.requests;

import java.util.Date;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

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
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date date;
	private String pages;
	private String doi;
	private String publisher;
	private String isbn;
	private String editor;
	private String comment;
	private List<String> authors;
	private Boolean addOnly;
	private MultipartFile pdfFile;
	private String fileEx;
}
