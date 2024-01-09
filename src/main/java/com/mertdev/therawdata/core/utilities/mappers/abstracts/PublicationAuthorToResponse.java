package com.mertdev.therawdata.core.utilities.mappers.abstracts;

import com.mertdev.therawdata.bussines.responses.AuthorResponse;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;

public interface PublicationAuthorToResponse {
	public AuthorResponse  toResponse(PublicationAuthor author,String imageUrl);
}
