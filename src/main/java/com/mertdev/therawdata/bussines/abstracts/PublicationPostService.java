package com.mertdev.therawdata.bussines.abstracts;

import java.util.UUID;

import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.ReasearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;

public interface PublicationPostService{
	public PostIdResponse createPublication(Article article);
	public PublicationPost findPost(UUID id);
	public PostIdResponse createPublication(ChapterInBook chapterInBook);
	public PostIdResponse createPublication(ConferencePaper conferencePaper);
	public PostIdResponse createPublication(Thesis thesis);
	public PostIdResponse createPublication(ReasearchProject reasearchProject);
	public PostIdResponse createPublication(CompanyTestReport companyTestReport);

}
