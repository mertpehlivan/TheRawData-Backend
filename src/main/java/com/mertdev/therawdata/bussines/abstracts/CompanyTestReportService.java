package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface CompanyTestReportService {
	public PostIdResponse createCompanyTestReport(CreateCompanyTestReportRequest reportRequest);
}
