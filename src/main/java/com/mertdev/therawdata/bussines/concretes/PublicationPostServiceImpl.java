package com.mertdev.therawdata.bussines.concretes;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.dataAccess.abstracts.PublicationPostRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.ReasearchProject;
import com.mertdev.therawdata.entities.concretes.Thesis;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublicationPostServiceImpl implements PublicationPostService{
	private final  PublicationPostRepository repository;
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
	public PostIdResponse createPublication(ChapterInBook chapterInBook) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(chapterInBook);
		publicationPost.setUser(user);
		PublicationPost data =  repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
		
	}
	@Override
	public PublicationPost findPost(UUID id) {
		
		return repository.getById(id);
	}
	@Override
	public PostIdResponse createPublication(ConferencePaper conferencePaper) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(conferencePaper);
		publicationPost.setUser(user);
		PublicationPost data =  repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}
	@Override
	public PostIdResponse createPublication(Thesis thesis) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(thesis);
		publicationPost.setUser(user);
		PublicationPost data =  repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}
	@Override
	public PostIdResponse createPublication(ReasearchProject reasearchProject) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(reasearchProject);
		publicationPost.setUser(user);
		PublicationPost data =  repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}
	@Override
	public PostIdResponse createPublication(CompanyTestReport companyTestReport) {
		String email = userService.getCurrentUsername();
		User user = userRepository.findAllByEmail(email).orElseThrow();
		PublicationPost publicationPost = new PublicationPost();
		publicationPost.setPublication(companyTestReport);
		publicationPost.setUser(user);
		PublicationPost data =  repository.save(publicationPost);
		PostIdResponse idResponse = new PostIdResponse();
		idResponse.setId(data.getId());
		return idResponse;
	}

}
