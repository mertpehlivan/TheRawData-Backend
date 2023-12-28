package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.ReasearchProjectService;
import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ResearchProjectRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.ResearchProject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReasearchProjectServiceImpl implements ReasearchProjectService {
	private final PublicationPostService postService;
	private final PublicationAuthorService publicationAuthorService;
	private final ResearchProjectRepository researchProjectRepository;
	@Override
	public PostIdResponse createReasearchProject(CreateReasearchProjectRequest creProjectRequest) {
		ResearchProject reasearchProject = new ResearchProject();
		reasearchProject.setDate(creProjectRequest.getDate());
		reasearchProject.setTitle(creProjectRequest.getTitle());
		reasearchProject.setComment(creProjectRequest.getComment());
		PostIdResponse id = postService.createPublication(reasearchProject);
		publicationAuthorService.createAuthor(creProjectRequest.getAuthors(), reasearchProject);
		return id;

	}
	@Override
	public List<GetPostResponse> getAllReasearchProject(String uniqueName) {
		List<ResearchProject> researchProjects = researchProjectRepository.findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(uniqueName);

		List<Publication> publication = new ArrayList<>();
		for(ResearchProject researchProject : researchProjects) {
			publication.add(researchProject);
		}
		return postService.getAllPost(publication);
	}

}
