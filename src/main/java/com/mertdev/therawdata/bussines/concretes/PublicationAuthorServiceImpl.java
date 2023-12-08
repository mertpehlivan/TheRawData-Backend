package com.mertdev.therawdata.bussines.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.amazonaws.services.appstream.model.User;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.abstracts.Publication;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationAuthorServiceImpl implements PublicationAuthorService{
	private final PublicationPostRepository publicationPostRepository;
	private final UserRepository userRepository;
	@Override
	public void createAuthor(List<String> authors, Publication publication) {
		// TODO Auto-generated method stub
		
	}
	
	

}
