package com.mertdev.therawdata.bussines.requests;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateThesisRequest {
	private String title;
	private String degree;
	private String pages;
	private String university;
	private String comment;
	private List<String> authors;
	private Boolean addOnly;
	private MultipartFile pdfFile;
	private String fileEx;
}
