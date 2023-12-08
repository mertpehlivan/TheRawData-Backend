package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ArticleService;
import com.mertdev.therawdata.bussines.abstracts.PublicationAuthorService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToArticleMappers;
import com.mertdev.therawdata.dataAccess.abstracts.ArticleRepository;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class ArticleServiceImpl implements ArticleService {
	
	private final DTOToArticleMappers articleMappers;
	private final PublicationPostService postService;
	private final ArticleRepository articleRepository ;
	private final PublicationAuthorService publicationAuthorService;

	
	@Override
	public PostIdResponse createArticle(CreateArticleRequest createArticleRequest) {
		 
		Article article = articleMappers.createArticleToArticle(createArticleRequest);
		publicationAuthorService.createAuthor(createArticleRequest.getAuthors(), article);
		return postService.createPublication(article);
	}

}
