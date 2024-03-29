package com.mertdev.therawdata.core.utilities.mappers.concretes;

import com.mertdev.therawdata.bussines.responses.AuthorResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationAuthorToResponse;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;

public class PublicationAuthorToResponseImpl implements PublicationAuthorToResponse{
	public AuthorResponse  toResponse(PublicationAuthor author,String imageUrl) {
		AuthorResponse tempAuthor = new AuthorResponse();
		tempAuthor.setFirstname(author.getAuthor().getFirstname());
		tempAuthor.setLastname(author.getAuthor().getLastname());
		tempAuthor.setProfileImageUrl(imageUrl);
		tempAuthor.setUniqueName(author.getAuthor().getUniqueName());
		return tempAuthor;
	}

	@Override
	public AuthorResponse toResponse(PublicationAuthor author) {
		AuthorResponse tempAuthor = new AuthorResponse();
		tempAuthor.setFirstname(author.getAuthor().getFirstname());
		tempAuthor.setLastname(author.getAuthor().getLastname());
		tempAuthor.setProfileImageUrl(author.getAuthor().getProfileImageName());
		tempAuthor.setUniqueName(author.getAuthor().getUniqueName());
		return tempAuthor;
	}
}
