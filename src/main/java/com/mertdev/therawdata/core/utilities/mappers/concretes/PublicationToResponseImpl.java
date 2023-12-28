package com.mertdev.therawdata.core.utilities.mappers.concretes;

import com.mertdev.therawdata.bussines.responses.ArticleResponse;
import com.mertdev.therawdata.bussines.responses.ChapterInABookResponse;
import com.mertdev.therawdata.bussines.responses.CompanyTestReportResponse;
import com.mertdev.therawdata.bussines.responses.ConferencePaperResponse;
import com.mertdev.therawdata.bussines.responses.ResearchProjectResponse;
import com.mertdev.therawdata.bussines.responses.ThesisResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationToResponse;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;

public class PublicationToResponseImpl implements PublicationToResponse{

	@Override
	public ChapterInABookResponse toResponse(ChapterInBook object) {
		 if (object == null) {
		        return null;
		    }

		    return new ChapterInABookResponse(
		            object.getId(),
		            object.getTitle(),
		            object.getChapterNumber(),
		            object.getBookTitle(),
		            object.getDate(),
		            object.getPages(),
		            object.getDoi(),
		            object.getPublisher(),
		            object.getIsbn(),
		            object.getEditor(),
		            object.getComment(),
		            object.getCreationTime()
		    );
	}

	@Override
	public ArticleResponse toResponse(Article object) {
		// Check if object is null
	    if (object == null) {
	        return null;
	    }

	    return new ArticleResponse(
	            object.getTitle(),
	            object.getId(),
	            object.getJournalName(),
	            object.getVolume(),
	            object.getIssue(),
	            object.getPages(),
	            object.getDoi(),
	            object.getComment(),
	            object.getCreationTime()
	    );
	}

	@Override
	public ConferencePaperResponse toResponse(ConferencePaper object) {
	    // Check if object is null
	    if (object == null) {
	        return null;
	    }

	    // Map specific ConferencePaper fields
	    return new ConferencePaperResponse(
	            object.getId(),
	            object.getTitle(),
	            object.getDate(),
	            object.getConferenceName(),
	            object.getLocation(),
	            object.getDoi(),
	            object.getPages(),
	            object.getCreationTime(),
	            object.getComment()
	    );
	}

	@Override
	public ResearchProjectResponse toResponse(ResearchProject object) {
		 // Check if object is null
	    if (object == null) {
	        return null;
	    }

	    // Map specific ResearchProject fields
	    return new ResearchProjectResponse(
	        object.getId(),
	        object.getTitle(),
	        object.getDate(),
	        object.getCreationTime(),
	        object.getComment()
	    );
	}

	@Override
	public CompanyTestReportResponse toResponse(CompanyTestReport object) {
		// Check if object is null
	    if (object == null) {
	        return null;
	    }

	    // Map specific CompanyTestReport fields
	    return new CompanyTestReportResponse(
	            object.getId(),
	            object.getTitle(), 
	            object.getDate(),
	            object.getCompanyName(),
	            object.getCreationTime(),
	            object.getComment()
	    );
	}

	@Override
	public ThesisResponse toResponse(Thesis object) {
		if (object == null) {
	        return null;
	    }
		
	    // Map specific Thesis fields
	    return new ThesisResponse(
	        object.getId(),
	        object.getTitle(),
	        object.getDegree(),
	        object.getPages(),
	        object.getUniversity(),
	        object.getCreationTime(),
	        object.getComment()
	    );
	}

}
