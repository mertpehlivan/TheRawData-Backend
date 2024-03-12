package com.mertdev.therawdata.webApi;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.ChangePasswordRequest;
import com.mertdev.therawdata.bussines.requests.ChangeUsernameRequest;
import com.mertdev.therawdata.bussines.requests.GetByUsernameRequest;
import com.mertdev.therawdata.bussines.requests.SearchRequest;
import com.mertdev.therawdata.bussines.requests.SendEmailCodeRequest;
import com.mertdev.therawdata.bussines.requests.UpdateCountryRequest;
import com.mertdev.therawdata.bussines.responses.GetProfileDataResponse;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.bussines.responses.UserPublicationsCountResponse;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user/")
@CrossOrigin(origins = "http://localhost:3000/**")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final UserRepository userRepository;

	@GetMapping("/get-user")
	public GetUserResponse getUser() {
		return userService.getUser();
	}
	@GetMapping("/userPublicationsCount/{uniqueName}")
	public UserPublicationsCountResponse publicationsCountResponse(@PathVariable String uniqueName) {
		return userService.countResponse(uniqueName);
	}

	@GetMapping("/search")
	public ResponseEntity<List<GetUserResponse>> searchUsers(
			@RequestParam(name = "searchTerm", required = false) String searchTerm) {
		System.out.println(searchTerm);
		if (searchTerm == null || searchTerm.isEmpty()) {
			// Handle the case when no searchTerm is provided
			return ResponseEntity.badRequest().build();
		}

		// Split the search term into individual words
		String[] searchTerms = searchTerm.split("\\s+");

		try {
			List<GetUserResponse> searchResults = userService.searchUsers(searchTerms);
			return ResponseEntity.ok(searchResults);
		} catch (Exception e) {
			// Handle any exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/searchByUniqueName")
	public ResponseEntity<List<GetUserResponse>> searchUsersByUniqueName(
			@RequestParam(name = "uniqueName", required = false) String uniqueName) {
		System.out.println(uniqueName);

		try {
			System.out.println(uniqueName);
			List<GetUserResponse> searchResults = userService.searchUsersByUniqueName(uniqueName);
			return ResponseEntity.ok(searchResults);
		} catch (Exception e) {
			// Handle any exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{uniqueName}")
	public GetProfileDataResponse getProfileDataResponse(@PathVariable("uniqueName") String uniqueName) {
		System.out.println(uniqueName);
		return userService.getDataByUsername(uniqueName);
	}

	@GetMapping("/searchByInitials")
	public List<User> searchUsersByInitials(@RequestParam String initials) {
		return userService.searchByInitials(initials);
	}

	@PostMapping("/follow/{followingId}")
	public void followUser(@PathVariable UUID followingId) {
		userService.followUser(followingId);
	}

	@PostMapping("/unfollow/{followingId}")
	public void unfollowUser(@PathVariable UUID followingId) {
		try {
			userService.unFollowing(followingId);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	@GetMapping("/isFollowing/{followingId}")
	public ResponseEntity<Boolean> isFollowingUser(@PathVariable UUID followingId) {
		return ResponseEntity.ok(userService.isFollowing(followingId));
	}

	@PostMapping("/changeEmail")
	public ResponseEntity<String> changeEmail(@RequestBody SendEmailCodeRequest request) {
		System.out.println("New Email:" + request.getEmail());
		try {
			userService.changeEmail(request.getEmail());
			return ResponseEntity.ok("Email Address Successfully changed");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/changeEmailStatus")
	public Boolean changeEmailStatus(@RequestBody SendEmailCodeRequest request) {
		System.out.println(request);
		return userService.changeEmailStatus(request.getEmail());
	}

	@PostMapping("/changeUsername")
	public ResponseEntity<String> changeUsername(@RequestBody ChangeUsernameRequest request) {
		try {
			if(request.getUsername() == null) {
				
				return ResponseEntity.badRequest().body("Username content not found");
			}
			userService.changeUsername(request.getUsername());
			return ResponseEntity.ok("Username successfully changed.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	@PostMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
		try {
			userService.changePassword(request);
			return ResponseEntity.ok("Your password has been successfully changed");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	@PostMapping("/changeCountry")
	public ResponseEntity<?> changeCountry(@RequestBody UpdateCountryRequest newCountry) {
	    try {
	        userService.changeCountry(newCountry.getCountry());
	        return ResponseEntity.ok().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to change country: " + e.getMessage());
	    }
	}

	
	
	

}
