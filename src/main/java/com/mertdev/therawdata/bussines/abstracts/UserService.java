package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.GetByUsernameRequest;
import com.mertdev.therawdata.bussines.responses.GetProfileDataResponse;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.entities.concretes.User;

public interface UserService {
	public String getCurrentUsername();
	public GetUserResponse getUser();
	public List<GetUserResponse> searchUsers(String firstname,String lastname);
	public GetProfileDataResponse getDataByUsername(String uniqueName);
	public List<User> searchByInitials(String initials);
}
