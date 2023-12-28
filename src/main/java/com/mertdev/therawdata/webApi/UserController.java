package com.mertdev.therawdata.webApi;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.GetByUsernameRequest;
import com.mertdev.therawdata.bussines.requests.SearchRequest;
import com.mertdev.therawdata.bussines.responses.GetProfileDataResponse;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
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
	@CrossOrigin(origins = "http://localhost:3000")
	public GetUserResponse getUser() {
		return userService.getUser();
	}

	@GetMapping("/find")
	public ResponseEntity<List<GetUserResponse>> findUsers(
			@RequestParam(value = "firstName", required = false) String firstname,
			@RequestParam(value = "lastName", required = false) String lastname) {
		return ResponseEntity.ok(userService.searchUsers(firstname, lastname));
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
	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping("/follow/{followingId}")
	public ResponseEntity<String> followUser(@PathVariable UUID followingId) {
		try {
			String email = userService.getCurrentUsername();
			User follower = userRepository.findByEmail(email);

			if (follower == null) {
				throw new RuntimeException("Follower not found with email: " + email);
			}

			User following = userRepository.findById(followingId)
					.orElseThrow(() -> new RuntimeException("Following user not found with id: " + followingId));

			if (follower.getFollowing().contains(following)) {
				throw new RuntimeException(
						"User with id " + follower.getId() + " is already following user with id " + followingId);
			}

			follower.getFollowing().add(following);
			following.getFollowers().add(follower);

			userRepository.save(follower);
			userRepository.save(following);

			return ResponseEntity
					.ok("User with id " + follower.getId() + " is now following user with id " + followingId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Follow operation failed: " + e.getMessage());
		}
	}

	@PostMapping("/unfollow/{followingId}")
	public ResponseEntity<String> unfollowUser(@PathVariable UUID followingId) {
		try {
			String email = userService.getCurrentUsername();
			User follower = userRepository.findByEmail(email);

			if (follower == null) {
				throw new RuntimeException("Follower not found with email: " + email);
			}

			User following = userRepository.findById(followingId)
					.orElseThrow(() -> new RuntimeException("Following user not found with id: " + followingId));

			if (follower.getId().equals(following.getId())) {
				throw new RuntimeException("Cannot unfollow yourself.");
			}

			if (!follower.getFollowing().contains(following)) {
				throw new RuntimeException(
						"User with id " + follower.getId() + " is not following user with id " + followingId);
			}

			follower.getFollowing().remove(following);
			following.getFollowers().remove(follower);

			userRepository.save(follower);
			userRepository.save(following);

			return ResponseEntity
					.ok("User with id " + follower.getId() + " is no longer following user with id " + followingId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Unfollow operation failed: " + e.getMessage());
		}
	}

	@GetMapping("/isFollowing/{followingId}")
	public ResponseEntity<Boolean> isFollowingUser(@PathVariable UUID followingId) {
		try {
			String email = userService.getCurrentUsername();
			User follower = userRepository.findByEmail(email);

			if (follower.getId().equals(followingId)) {
				return ResponseEntity.ok(false);
			}

			User following = userRepository.findById(followingId)
					.orElseThrow(() -> new RuntimeException("Following user not found with id: " + followingId));

			boolean isFollowing = follower.getFollowing().contains(following);

			return ResponseEntity.ok(isFollowing);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

}
