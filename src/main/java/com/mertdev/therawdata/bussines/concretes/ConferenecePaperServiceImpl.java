package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ConferencePaperService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ConferencePaperRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
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
		conferencePaper.setComment(conPaperRequest.getComment());
		PostIdResponse id = postService.createPublication(conferencePaper);
		publicationAuthorService.createAuthor(conPaperRequest.getAuthors(), conferencePaper);
		return id;
	}
	@Override
	public List<PublicationPostResponse> getAllConferencePaper() {
		return postService.getAll(
				paperRepository.findAllByOrderByCreationTimeDesc()
			);
	}
	@Override
	public List<GetPostResponse> getAllConferencePaper(String uniqueName) {
		List<ConferencePaper> conferencePapers = paperRepository.findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(uniqueName);

		List<Publication> publication = new ArrayList<>();
		for(ConferencePaper conferencePaper : conferencePapers) {
			publication.add(conferencePaper);
		}
		return postService.getAllPost(publication);
	}

}
