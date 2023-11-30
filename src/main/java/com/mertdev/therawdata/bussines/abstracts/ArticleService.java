package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface ArticleService {
	public PostIdResponse createArticle(CreateArticleRequest createArticleRequest);
}
