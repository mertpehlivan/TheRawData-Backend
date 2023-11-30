package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.entities.concretes.Article;

public interface PublicationPostService{
	public PostIdResponse createPublication(Article article);
}
