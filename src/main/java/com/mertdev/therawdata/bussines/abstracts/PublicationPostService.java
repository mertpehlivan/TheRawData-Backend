package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;

import com.mertdev.therawdata.bussines.responses.GetPostResponse;
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

public interface PublicationPostService{
	public PublicationPost findPost(UUID id);
	public PostIdResponse createPublication(Article article);
	public PostIdResponse createPublication(ChapterInBook chapterInBook);
	public PostIdResponse createPublication(ConferencePaper conferencePaper);
	public PostIdResponse createPublication(Thesis thesis);
	public PostIdResponse createPublication(ResearchProject reasearchProject);
	public PostIdResponse createPublication(CompanyTestReport companyTestReport);
	public <T> List<PublicationPostResponse> getAll(List<T> items);
	public List<PublicationType> getAllPublication();
	public List<GetPostResponse> getAllPost(List<Publication> items);
	public List<GetPostResponse> getAllByUniqueName(String uniqueName);
	List<GetPostResponse> getAllPost(Pageable pageable);
	public GetPostResponse getPost(UUID publicationId);
}
