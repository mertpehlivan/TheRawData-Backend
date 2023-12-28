package com.mertdev.therawdata.bussines.requests;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor 
public class CreateCompanyTestReportRequest {
	private String title;
	private Date date;
	private String companyName;
	private String comment;
	private List<String> authors;
}
