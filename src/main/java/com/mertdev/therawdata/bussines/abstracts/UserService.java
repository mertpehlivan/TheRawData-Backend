package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;

import com.mertdev.therawdata.bussines.responses.GetProfileDataResponse;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.entities.concretes.User;

public interface UserService {
	public String getCurrentUsername();
	public User getCurrentUser();
	public GetUserResponse getUser();
	public GetProfileDataResponse getDataByUsername(String uniqueName);
	public List<User> searchByInitials(String initials);
	public Boolean isFollowing(UUID followingId);
	public void unFollowing(UUID followingId);
	public void followUser(@PathVariable UUID followingId);
	List<GetUserResponse> searchUsers(String[] searchTerms);
	List<GetUserResponse> searchUsersByUniqueName(String uniqueName);
	
}
