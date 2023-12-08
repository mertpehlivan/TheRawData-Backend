package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.entities.abstracts.Publication;

public interface PublicationAuthorService {
	public void createAuthor(List<String> authors, Publication publication);
}
