package com.mertdev.therawdata.core.utilities.mappers.abstracts;

import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.entities.concretes.User;

public interface UserToDTOMappers {
	public GetUserResponse toGetUserResponse(User user);
}
