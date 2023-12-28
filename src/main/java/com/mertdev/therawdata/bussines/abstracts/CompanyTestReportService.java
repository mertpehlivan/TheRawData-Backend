package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface CompanyTestReportService {
	public PostIdResponse createCompanyTestReport(CreateCompanyTestReportRequest reportRequest);
	public List<PublicationPostResponse> getAllCompanyTestReport(); 
	public List<GetPostResponse> getAllCompanyTestReport(String uniqueName);
}
