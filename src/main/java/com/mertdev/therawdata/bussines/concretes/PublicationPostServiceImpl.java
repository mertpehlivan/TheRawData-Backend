package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicationPostServiceImpl implements PublicationPostService{
	private final PublicationPostRepository repository;
	private final UserRepository userRepository;
	private final UserService userService;
	@Override
	public PostIdResponse createPublication(Article article) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(article);
		publicationPost.setUser(user);
		PublicationPost data =  repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
		
	}

}
