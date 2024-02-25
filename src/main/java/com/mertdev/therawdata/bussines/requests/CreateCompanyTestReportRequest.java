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
public class CreateCompanyTestReportRequest {
	private String title;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date date;
	private String companyName;
	private String comment;
	private List<String> authors;
	private Boolean addOnly;
	private MultipartFile pdfFile;
	private String fileEx;
}
