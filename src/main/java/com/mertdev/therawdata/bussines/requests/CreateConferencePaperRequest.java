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
public class CreateConferencePaperRequest {
	
	private String title;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date date;
	private String conferenceName;
	private String location;
	private String doi;
	private String pages;
	private String comment;
	private List<String> authors;
	private Boolean addOnly;
	private Boolean pdfStatus;
	private MultipartFile pdfFile;
	private String fileEx;
}
