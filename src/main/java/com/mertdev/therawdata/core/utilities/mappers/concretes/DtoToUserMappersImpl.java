package com.mertdev.therawdata.core.utilities.mappers.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DtoToUserMappers;
import com.mertdev.therawdata.entities.concretes.User;

public class DtoToUserMappersImpl implements DtoToUserMappers {

	@Override
	public List<GetUserResponse> userTo(List<User> users) {
        return users.stream()
                .map(this::mapToGetUserResponse)
                .collect(Collectors.toList());
    }

    private GetUserResponse mapToGetUserResponse(User user) {
        GetUserResponse response = new GetUserResponse();
        response.setId(user.getId());
        response.setFirstname(user.getFirstname());
        response.setLastname(user.getLastname());
        response.setEmail(user.getEmail());
        response.setCountry(user.getCountry());
        return response;
    }

}
