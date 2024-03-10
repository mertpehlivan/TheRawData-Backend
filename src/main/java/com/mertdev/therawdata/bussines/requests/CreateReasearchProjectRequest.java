package com.mertdev.therawdata.bussines.requests;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
@Service
public class CreateReasearchProjectRequest {
	private String title;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date date;
	private String comment;
	private List<String> authors;
	private Boolean addOnly;
	private Boolean pdfStatus;
	private MultipartFile pdfFile;
	private String fileEx;
}
