package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.ReasearchProjectService;
import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
import com.mertdev.therawdata.entities.concretes.ReasearchProject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReasearchProjectServiceImpl implements ReasearchProjectService {
	private final PublicationPostService postService;
	private final PublicationAuthorService publicationAuthorService;
	@Override
	public PostIdResponse createReasearchProject(CreateReasearchProjectRequest creProjectRequest) {
		ReasearchProject reasearchProject = new ReasearchProject();
		reasearchProject.setDate(creProjectRequest.getDate());
		reasearchProject.setTitle(creProjectRequest.getTitle());
		publicationAuthorService.createAuthor(creProjectRequest.getAuthors(), reasearchProject);
		return postService.createPublication(reasearchProject);

	}

}
