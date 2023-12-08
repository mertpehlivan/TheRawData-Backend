package com.mertdev.therawdata.core.utilities.mappers.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.entities.concretes.User;

public interface DTOToUserMappers {
	public List<GetUserResponse> userTo(List<User> users);
}
