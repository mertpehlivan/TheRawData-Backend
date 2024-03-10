package com.mertdev.therawdata.bussines.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequest {
	private String title;
	private String journalName;
	private String volume;
	private String issue;
	private String pages;
	private String doi;
	private String comment;
	private List<String> authors;
	private Boolean addOnly;
	private Boolean pdfStatus;
	private MultipartFile pdfFile;
	private String fileEx;
}
