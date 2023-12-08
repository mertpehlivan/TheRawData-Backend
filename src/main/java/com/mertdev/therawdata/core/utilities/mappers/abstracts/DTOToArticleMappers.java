package com.mertdev.therawdata.core.utilities.mappers.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.PublicationPost;

public interface DTOToArticleMappers {
	public Article createArticleToArticle(CreateArticleRequest createArticleRequest);
}
