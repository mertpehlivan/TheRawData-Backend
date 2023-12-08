package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ConferencePaperService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ConferencePaperRepository;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConferenecePaperServiceImpl implements ConferencePaperService {

	private final PublicationPostService postService;
	private final ConferencePaperRepository paperRepository ;
	private final PublicationAuthorService publicationAuthorService;
	@Override
	public PostIdResponse createConferencePaper(CreateConferencePaperRequest conPaperRequest) {
		ConferencePaper conferencePaper = new ConferencePaper();
		conferencePaper.setConferenceName(conPaperRequest.getConferenceName());
		conferencePaper.setDate(conPaperRequest.getDate());
		conferencePaper.setDoi(conPaperRequest.getDoi());
		conferencePaper.setLocation(conPaperRequest.getLocation());
		conferencePaper.setPages(conPaperRequest.getPages());
		conferencePaper.setTitle(conPaperRequest.getTitle());
		publicationAuthorService.createAuthor(conPaperRequest.getAuthors(), conferencePaper);
		return postService.createPublication(conferencePaper);
	}

}
