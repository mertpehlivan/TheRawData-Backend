package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.CompanyTestReportService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.PublicationPost;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyTestReportServiceImpl implements CompanyTestReportService{
	private final PublicationPostService postService;
	private final PublicationAuthorService publicationAuthorService;
	@Override
	public PostIdResponse createCompanyTestReport(CreateCompanyTestReportRequest reportRequest) {
		CompanyTestReport companyTestReport = new CompanyTestReport();
		companyTestReport.setTitle(reportRequest.getTitle());
		companyTestReport.setDate(reportRequest.getDate());
		companyTestReport.setCompanyName(reportRequest.getCompanyName());

		publicationAuthorService.createAuthor(reportRequest.getAuthors(), companyTestReport);
		return postService.createPublication(companyTestReport);
	}

}
