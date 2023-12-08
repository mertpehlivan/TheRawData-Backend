package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.ThesisService;
import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.entities.concretes.Thesis;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThesisServiceImpl implements ThesisService{
	private final PublicationAuthorService publicationAuthorService;
	private final PublicationPostService postService;
	@Override
	public PostIdResponse createThesis(CreateThesisRequest createThesisRequest) {
		Thesis thesis = new Thesis();
		thesis.setDegree(createThesisRequest.getDegree());
		thesis.setPages(createThesisRequest.getPages());
		thesis.setTitle(createThesisRequest.getTitle());
		thesis.setUniversity(createThesisRequest.getUniversity());
		publicationAuthorService.createAuthor(createThesisRequest.getAuthors(), thesis);
		return postService.createPublication(thesis);
	}

}
