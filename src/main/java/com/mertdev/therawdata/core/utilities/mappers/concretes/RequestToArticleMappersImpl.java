package com.mertdev.therawdata.core.utilities.mappers.concretes;

import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RequestToArticleMappers;
import com.mertdev.therawdata.entities.concretes.Article;

public class RequestToArticleMappersImpl implements RequestToArticleMappers{

	@Override
	public Article createArticleToArticle(CreateArticleRequest createArticleRequest) {
		Article article = new Article();
		article.setJournalName(createArticleRequest.getJournalName());
		article.setDoi(createArticleRequest.getDoi());
		article.setIssue(createArticleRequest.getIssue());
		article.setPages(createArticleRequest.getPages());
		article.setTitle(createArticleRequest.getTitle());
		article.setVolume(createArticleRequest.getVolume());
		return article;
	}

}
