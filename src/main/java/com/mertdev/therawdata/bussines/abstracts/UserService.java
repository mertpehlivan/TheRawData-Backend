package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.responses.GetUserResponse;

public interface UserService {
	public String getCurrentUsername();
	public GetUserResponse getUser();
	public List<GetUserResponse> searchUsers(String firstname,String lastname);
}
