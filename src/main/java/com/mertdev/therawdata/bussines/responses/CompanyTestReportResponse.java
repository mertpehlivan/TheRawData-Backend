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
public class CompanyTestReportResponse implements PublicationResponse{
	private UUID id;
	private String title;
	private Date date;
	private String companyName;
	private Date creationTime;
	private String comment;
}
