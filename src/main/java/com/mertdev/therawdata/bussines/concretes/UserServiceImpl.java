package com.mertdev.therawdata.bussines.concretes;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DtoToUserMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.User;

import io.jsonwebtoken.lang.Collections;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor 
public class UserServiceImpl implements UserService{
	
	UserRepository userRepository;
	UserToDTOMappers toDTOMappers;
	DtoToUserMappers toUserMappers;
	@Override
	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }

        return "anonymousUser";
	}

	@Override
	public GetUserResponse getUser() {
		User user = userRepository.findByEmail(getCurrentUsername());
		return toDTOMappers.toGetUserResponse(user);
	}

	@Override
	public List<GetUserResponse> searchUsers(String firstname, String lastname) {
		System.out.println(firstname);
	    try {
	        List<User> users;

	        if (firstname != "" && lastname != "") {
	        	System.out.println("ikiside null değil");
	            users = userRepository.findByFirstnameStartingWithAndLastnameStartingWith(firstname, lastname);
	        } else if (firstname != "") {
	        	System.out.println("firstname");
	            users = userRepository.findByFirstnameStartingWith(firstname);
	        } else if (lastname != "") {
	            users = userRepository.findByLastnameStartingWith(lastname);
	        } else {
	            users = userRepository.findAll();
	        }

	        return toUserMappers.userTo(users);
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
}
