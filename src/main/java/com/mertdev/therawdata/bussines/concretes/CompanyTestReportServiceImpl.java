package com.mertdev.therawdata.bussines.concretes;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.CompanyTestReportService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.dataAccess.abstracts.CompanyTestReportRepository;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyTestReportServiceImpl implements CompanyTestReportService{
	private final CompanyTestReportRepository companyTestReportRepository;
	private final PublicationPostService postService;
	private final PublicationAuthorService publicationAuthorService;
	@Override
	public PostIdResponse createCompanyTestReport(CreateCompanyTestReportRequest reportRequest) {
		CompanyTestReport companyTestReport = new CompanyTestReport();
		companyTestReport.setTitle(reportRequest.getTitle());
		companyTestReport.setDate(reportRequest.getDate());
		companyTestReport.setCompanyName(reportRequest.getCompanyName());
		companyTestReport.setComment(reportRequest.getComment());
		PostIdResponse id = postService.createPublication(companyTestReport,reportRequest);
		publicationAuthorService.createAuthor(reportRequest.getAuthors(), companyTestReport);
		return id;
	}
	@Override
	public List<PublicationPostResponse> getAllCompanyTestReport() {
		
		return postService.getAll(
				companyTestReportRepository.findAllByOrderByCreationTimeDesc()
			);
	}
	@Override
	public List<GetPostResponse> getAllCompanyTestReport(String uniqueName,Pageable pageable) {
		
		return postService.getAllByUniqueName(uniqueName, "Company Test Report", pageable);
	}

}
