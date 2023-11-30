package com.mertdev.therawdata.bussines.concretes;

import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.ArticleService;
import com.mertdev.therawdata.bussines.abstracts.PublicationPostService;
import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RequestToArticleMappers;
import com.mertdev.therawdata.dataAccess.abstracts.ArticleRepository;
import com.mertdev.therawdata.entities.concretes.Article;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class ArticleServiceImpl implements ArticleService {
	
	private final RequestToArticleMappers articleMappers;
	private final PublicationPostService postService;
	private final ArticleRepository articleRepository ;

	
	@Override
	public PostIdResponse createArticle(CreateArticleRequest createArticleRequest) {
		 
		Article article = articleMappers.createArticleToArticle(createArticleRequest);
		return postService.createPublication(article);
	}

}
