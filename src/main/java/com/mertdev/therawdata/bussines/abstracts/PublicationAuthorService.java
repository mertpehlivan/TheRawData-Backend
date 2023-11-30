package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateAuthorsRequest;

public interface PublicationAuthorService {
	public void createAuthor(CreateAuthorsRequest createAuthorsRequest);
}
