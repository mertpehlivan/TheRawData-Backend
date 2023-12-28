package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.CompanyTestReportService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.dataAccess.abstracts.CompanyTestReportRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
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
		PostIdResponse id = postService.createPublication(companyTestReport);
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
	public List<GetPostResponse> getAllCompanyTestReport(String uniqueName) {
		List<CompanyTestReport> companyTestReports = companyTestReportRepository.findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(uniqueName);

		List<Publication> publication = new ArrayList<>();
		for(CompanyTestReport companyTestReport : companyTestReports) {
			publication.add(companyTestReport);
		}
		return postService.getAllPost(publication);
	}

}
