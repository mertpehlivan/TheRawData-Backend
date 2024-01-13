package com.mertdev.therawdata.bussines.concretes;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.GetProfileDataResponse;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.bussines.responses.NotificationResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToUserMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.dataAccess.abstracts.NotificationRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.Notification;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserToDTOMappers toDTOMappers;
	private final DTOToUserMappers toUserMappers;
	private final SimpMessagingTemplate messagingTemplate;
	private final NotificationRepository notificationRepository;
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

	@Override
	public GetProfileDataResponse getDataByUsername(String uniqueName) {
		try {
			User user = userRepository.getByUniqueName(uniqueName);

			GetProfileDataResponse profileResponse = new GetProfileDataResponse();
			profileResponse.setFirstname(user.getFirstname());
			profileResponse.setLastname(user.getLastname());
			profileResponse.setCountry(user.getCountry());
			profileResponse.setFollowers(user.getFollowers().size());
			profileResponse.setFollowing(user.getFollowing().size());
			profileResponse.setUniqueName(user.getUniqueName());
			profileResponse.setId(user.getId());
			profileResponse.setProfileImageUrl(user.getProfileImageName());
			return profileResponse;

		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public List<User> searchByInitials(String initials) {
		return userRepository.findByInitials("%" + initials + "%");
	}

	@Override
	public User getCurrentUser() {
		return userRepository.findByEmail(getCurrentUsername());
	}

	@Override
	public Boolean isFollowing(UUID followingId) {
		try {
			String email = getCurrentUsername();
			User follower = userRepository.findByEmail(email);

			if (follower.getId().equals(followingId)) {
				return false;
			}

			User following = userRepository.findById(followingId)
					.orElseThrow(() -> new RuntimeException("Following user not found with id: " + followingId));

			boolean isFollowing = follower.getFollowing().contains(following);

			return isFollowing;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void unFollowing(UUID followingId) {
		try {
			String email = getCurrentUsername();
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

		} catch (Exception e) {

		}

	}

	@Override
	public void followUser(UUID followingId) {
		try {
			String email = getCurrentUsername();
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
			
			sendNotificationToUser(following, follower, "%s %s".formatted(follower.getFirstname(), follower.getLastname()));

		} catch (Exception e) {
			System.err.println(e);
		}
	}
	private void sendNotificationToUser(User following,User follower, String fullName) {
			System.out.println(following.getId());
			String destination = "/topic/%s/notifications".formatted(following.getId().toString());
			NotificationResponse notificationResponse = new NotificationResponse();
			notificationResponse.setContent("started following you.");
			notificationResponse.setFullName(fullName);
			notificationResponse.setPublicationLink(null);
			notificationResponse.setPublicationTitle(null);
			notificationResponse.setUserLink("/%s".formatted(follower.getUniqueName()));
			notificationResponse.setType("follow");
			notificationResponse.setStatus(false);
			messagingTemplate.convertAndSend(destination, notificationResponse);
			
		Notification notification = new Notification();
		notification.setContent("started following you.");
		notification.setFullName(notificationResponse.getFullName());
		notification.setPublicationLink(null);
		notification.setPublicationTitle(null);
		notification.setUserLink(notificationResponse.getUserLink());
		notification.setType(notificationResponse.getType());
		notification.setUser(following);
		notification.setStatus(false);
		
		notificationRepository.save(notification);

	}

}
