package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.requests.CreateAuthorsRequest;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationAuthorServiceImpl implements PublicationAuthorService{
	PublicationPostRepository publicationPostRepository;
	@Override
	public void createAuthor(CreateAuthorsRequest createAuthorsRequest) {
		
		
	}

}
