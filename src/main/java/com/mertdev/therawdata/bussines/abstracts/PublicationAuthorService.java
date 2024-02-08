package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;
import java.util.UUID;

import com.mertdev.therawdata.entities.abstracts.Publication;

public interface PublicationAuthorService {
	public void createAuthor(List<String> authors, Publication publication);
	void addAuthorPost(UUID publicationPostId);
}
