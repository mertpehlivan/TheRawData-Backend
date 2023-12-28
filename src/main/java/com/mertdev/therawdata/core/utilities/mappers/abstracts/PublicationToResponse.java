package com.mertdev.therawdata.core.utilities.mappers.abstracts;

import com.mertdev.therawdata.bussines.responses.ArticleResponse;
import com.mertdev.therawdata.bussines.responses.ChapterInABookResponse;
import com.mertdev.therawdata.bussines.responses.CompanyTestReportResponse;
import com.mertdev.therawdata.bussines.responses.ConferencePaperResponse;
import com.mertdev.therawdata.bussines.responses.ResearchProjectResponse;
import com.mertdev.therawdata.bussines.responses.ThesisResponse;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;

public interface PublicationToResponse {
	public ChapterInABookResponse toResponse(ChapterInBook object);
	public ArticleResponse toResponse(Article object);
	public ConferencePaperResponse toResponse(ConferencePaper object);
	public ResearchProjectResponse toResponse(ResearchProject object);
	public CompanyTestReportResponse toResponse(CompanyTestReport object);
	public ThesisResponse toResponse(Thesis object);
	
	
}
