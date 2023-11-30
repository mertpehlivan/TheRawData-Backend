package com.mertdev.therawdata.core.utilities.mappers.concretes;

import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.entities.concretes.User;

public class UserToDTOMappersImpl implements UserToDTOMappers{

	@Override
	public GetUserResponse toGetUserResponse(User user) {
		GetUserResponse responseUser = new GetUserResponse();
		responseUser.setId(user.getId());
		responseUser.setFirstname(user.getFirstname());
		responseUser.setLastname(user.getLastname());
		responseUser.setEmail(user.getEmail());
		responseUser.setCountry(user.getCountry());
		return responseUser;
	}

}
