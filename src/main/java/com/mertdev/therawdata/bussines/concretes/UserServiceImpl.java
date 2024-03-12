package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.MailService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.ChangePasswordRequest;
import com.mertdev.therawdata.bussines.responses.GetProfileDataResponse;
import com.mertdev.therawdata.bussines.responses.GetUserResponse;
import com.mertdev.therawdata.bussines.responses.NotificationResponse;
import com.mertdev.therawdata.bussines.responses.UserPublicationsCountResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToUserMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.dataAccess.abstracts.NotificationRepository;
import com.mertdev.therawdata.dataAccess.abstracts.ShareRepository;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.ChapterInBook;
import com.mertdev.therawdata.entities.concretes.CompanyTestReport;
import com.mertdev.therawdata.entities.concretes.ConferencePaper;
import com.mertdev.therawdata.entities.concretes.Notification;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
import com.mertdev.therawdata.entities.concretes.ResearchProject;
import com.mertdev.therawdata.entities.concretes.Share;
import com.mertdev.therawdata.entities.concretes.Thesis;
import com.mertdev.therawdata.entities.concretes.User;
import com.mertdev.therawdata.exceptions.EmailException;
import com.mertdev.therawdata.exceptions.UniqueNameException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserToDTOMappers toDTOMappers;
	private final DTOToUserMappers toUserMappers;
	private final SimpMessagingTemplate messagingTemplate;
	private final NotificationRepository notificationRepository;
	private final MailService mailService;
	private final ShareRepository shareRepository;
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
	public List<GetUserResponse> searchUsers(String[] searchTerms) {
		try {
			List<User> searchResults = new ArrayList<>();

			for (String term : searchTerms) {
				if (!term.isEmpty()) {
					System.out.println(term);
					List<User> termResults = userRepository.searchUsers(term);
					searchResults.addAll(termResults);
				}
			}

			// Combine and remove duplicates
			Set<User> uniqueResults = new HashSet<>(searchResults);

			List<User> verifiedUsers = uniqueResults.stream()
					.filter(user -> Boolean.TRUE.equals(user.getEmailVerficationStatus())).collect(Collectors.toList());
			return toUserMappers.userTo(new ArrayList<>(uniqueResults));
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<GetUserResponse> searchUsersByUniqueName(String uniqueName) {
		try {
			List<User> users = userRepository.findByUniqueNameStartingWithIgnoreCase(uniqueName);
			return toUserMappers.userTo(users);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
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
			profileResponse.setPublications(user.getShares().size());
			
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
	public UserPublicationsCountResponse countResponse(String uniqueName) {
		List<Share> shares = shareRepository.findByUserUniqueNameOrderByCreationTime(uniqueName);
		
		UserPublicationsCountResponse publicationCountResponse = new UserPublicationsCountResponse(); 
		for(Share item : shares) {
			if (item.getPublicationPost().getPublication() instanceof Article) {
				publicationCountResponse.setArticleCount(publicationCountResponse.getArticleCount() + 1);
			} else if (item.getPublicationPost().getPublication() instanceof ChapterInBook) {
				publicationCountResponse.setChapterInBook(publicationCountResponse.getChapterInBook() + 1);
			} else if (item.getPublicationPost().getPublication() instanceof CompanyTestReport) {
				publicationCountResponse.setCompanyTestReport(publicationCountResponse.getCompanyTestReport() + 1);
			} else if (item.getPublicationPost().getPublication() instanceof ConferencePaper) {
				publicationCountResponse.setConferencePaper(publicationCountResponse.getConferencePaper() + 1);
			} else if (item.getPublicationPost().getPublication() instanceof ResearchProject) {
				publicationCountResponse.setResearchProject(publicationCountResponse.getResearchProject() + 1);
			} else if (item.getPublicationPost().getPublication() instanceof Thesis) {
				publicationCountResponse.setThesis(publicationCountResponse.getThesis() + 1);
			}
		}
		return publicationCountResponse;
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

			sendNotificationToUser(following, follower,
					"%s %s".formatted(follower.getFirstname(), follower.getLastname()));

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	private void sendNotificationToUser(User following, User follower, String fullName) {
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

	@Override
	public void changeEmail(String newEmail) {
		if (userRepository.existsByEmail(newEmail)) {
			throw new EmailException("This email address is already used");
		}
		User user = this.getCurrentUser();
		user.setEmail(newEmail);
		User changeUser = userRepository.save(user);
		try {
			mailService.sendEmailCode(changeUser, changeUser.getEmail());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Boolean changeEmailStatus(String newEmail) {
		return userRepository.existsByEmail(newEmail);
	}

	@Override
	public void changeUsername(String newUsername) {
		try {
			if (userRepository.existsByUniqueName(newUsername)) {
				throw new UniqueNameException("This username address is already used");
			}
			User user = this.getCurrentUser();
			user.setUniqueName(newUsername);
			userRepository.save(user);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void changePassword(ChangePasswordRequest request) throws Exception {
		User user = getCurrentUser();
		System.out.println(request);
		try {
			if(request.getConfirmPassword() == null ||request.getNewPassword() == null ||request.getCurrentPassword() == null ) {
				throw new Exception("Not null");
			}
			else if(passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
				
				if (request.getNewPassword().equals(request.getConfirmPassword())) {
					if(request.getNewPassword().equals(request.getCurrentPassword())!= true) {
						user.setPassword(passwordEncoder.encode(request.getNewPassword()));
						userRepository.save(user);
					}else {
						throw new Exception("Your current password must be different from your new password.");
					}
					
				}else {
					throw new Exception("The new password and the confirmation password are not the same.");
				}
			}else {
				throw new Exception("Your current password is incorrect");
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void changeCountry(String newCountry) {
		User user = this.getCurrentUser();
		user.setCountry(newCountry);
		userRepository.save(user);
	}

}
