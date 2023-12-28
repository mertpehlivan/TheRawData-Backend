package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.CreateArticleRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface ArticleService {
	public PostIdResponse createArticle(CreateArticleRequest createArticleRequest);
	public List<PublicationPostResponse> getAllArticle(); 
	public List<GetPostResponse> getAllArticle(String uniqueName);
}
