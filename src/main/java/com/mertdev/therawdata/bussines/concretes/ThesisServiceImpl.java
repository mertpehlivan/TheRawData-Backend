package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.ThesisService;
import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;
import com.mertdev.therawdata.dataAccess.abstracts.ThesisRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.Thesis;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThesisServiceImpl implements ThesisService{
	private final PublicationAuthorService publicationAuthorService;
	private final PublicationPostService postService;
	private final ThesisRepository thesisRepository;
	@Override
	public PostIdResponse createThesis(CreateThesisRequest createThesisRequest) {
		Thesis thesis = new Thesis();
		thesis.setDegree(createThesisRequest.getDegree());
		thesis.setPages(createThesisRequest.getPages());
		thesis.setTitle(createThesisRequest.getTitle());
		thesis.setUniversity(createThesisRequest.getUniversity());
		thesis.setComment(createThesisRequest.getComment());
		PostIdResponse id = postService.createPublication(thesis);
		publicationAuthorService.createAuthor(createThesisRequest.getAuthors(), thesis);
		return id;
	}
	@Override
	public List<PublicationPostResponse> getAllThesis() {
		
		return postService.getAll(
				thesisRepository.findAllByOrderByCreationTimeDesc()
			);
	}
	@Override
	public List<GetPostResponse> getAllThesis(String uniqueName) {
		List<Thesis> thesisies = thesisRepository.findByPublicationPost_User_UniqueNameOrderByCreationTimeDesc(uniqueName);
		List<Publication> publication = new ArrayList<>();
		for(Thesis thesis : thesisies) {
			publication.add(thesis);
		}
		return postService.getAllPost(publication);
		
	}

}
