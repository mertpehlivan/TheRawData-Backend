package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import com.amazonaws.services.dynamodbv2.document.Page;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.requests.CreateChapterInABookRequest;
import com.mertdev.therawdata.bussines.requests.CreateCompanyTestReportRequest;
import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.GetSearchPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.abstracts.PublicationType;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;

public interface PublicationPostService {
	public PublicationPost findPost(UUID id);

	PostIdResponse createPublication(Article article, CreateArticleRequest createArticleRequest);

	PostIdResponse createPublication(ResearchProject reasearchProject,
			CreateReasearchProjectRequest createReasearchProjectRequest);

	PostIdResponse createPublication(ConferencePaper conferencePaper,
			CreateConferencePaperRequest conferencePaperRequest);

	PostIdResponse createPublication(Thesis thesis, CreateThesisRequest createThesisRequest);

	PostIdResponse createPublication(CompanyTestReport companyTestReport,
			CreateCompanyTestReportRequest createCompanyTestReportRequest);
	
	PostIdResponse createPublication(ChapterInBook chapterInBook, CreateChapterInABookRequest chapterInABookRequest);

	public List<PublicationType> getAllPublication();

	public List<GetPostResponse> getAllPost(List<Publication> items);

	public List<GetPostResponse> getAllByUniqueName(String uniqueName);

	List<GetPostResponse> getAllPost(Pageable pageable);

	public GetPostResponse getPost(UUID publicationId);

	

	List<GetPostResponse> getFollowingUserPost(Pageable pageable);

	List<GetSearchPostResponse> getSearchPost(String title, Pageable pageable);

	List<GetPostResponse> getAllByUniqueName(String uniqueName, String PublicationType, Pageable pageable);

	<T> List<PublicationPostResponse> getAll(List<T> items);

	

}
