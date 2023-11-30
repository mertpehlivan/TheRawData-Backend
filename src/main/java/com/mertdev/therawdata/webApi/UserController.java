package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.SearchRequest;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/")
@CrossOrigin (origins = "http://localhost:3000/**")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping("/get-user")
	@CrossOrigin (origins = "http://localhost:3000")
	public GetUserResponse getUser() {
		return userService.getUser();
	}
	@GetMapping("/find")
	public ResponseEntity<List<GetUserResponse>> findUsers(
            @RequestParam(value = "firstName", required = false) String firstname,
            @RequestParam(value = "lastName", required = false) String lastname) {
		 return ResponseEntity.ok(userService.searchUsers(firstname , lastname));
	}
	

}
