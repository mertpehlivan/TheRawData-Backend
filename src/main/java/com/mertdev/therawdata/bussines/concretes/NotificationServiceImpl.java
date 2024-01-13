package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.NotificationService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.NotificationResponse;
import com.mertdev.therawdata.bussines.responses.NotificationStatusRespone;
import com.mertdev.therawdata.dataAccess.abstracts.NotificationRepository;
import com.mertdev.therawdata.entities.concretes.Notification;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
	private final NotificationRepository notificationRepository;
	private final UserService userService;
	@Override
	public List<NotificationResponse> getAll(Pageable pageable) {
		User user = userService.getCurrentUser();
		List<NotificationResponse> notificationResponses = new ArrayList<>();
		Page<Notification> notifications = notificationRepository
				.findByUserId(user.getId(),PageRequest.of(pageable.getPageNumber(), 6, Sort.by(Sort.Direction.DESC, "id")));
		for(Notification notification : notifications) {
			NotificationResponse notificationResponse = new NotificationResponse();
			notificationResponse.setId(notification.getId());
			notificationResponse.setContent(notification.getContent());
			notificationResponse.setFullName(notification.getFullName());
			notificationResponse.setPublicationLink(notification.getPublicationLink());
			notificationResponse.setPublicationTitle(notification.getPublicationTitle());
			notificationResponse.setUserLink("/%s".formatted(notification.getUserLink()));
			notificationResponse.setType(notification.getType());
			notificationResponse.setStatus(false);
			notificationResponses.add(notificationResponse);
			if(notification.getStatus() == false) {
				notification.setStatus(true);
				notificationRepository.save(notification);
			}
			
			
			
		}
		return notificationResponses;
	}



	@Override
	public NotificationStatusRespone getCountStatusFalseNotifications() {
		User user = userService.getCurrentUser();
		NotificationStatusRespone respone =new NotificationStatusRespone();
		respone.setId(notificationRepository.countByUserIdAndStatus(user.getId(), false));
		return respone;
	}



	@Override
	public void updateStatusFalseNotifications(Long notificationId) {
		Optional<Notification> notification = notificationRepository.findById(notificationId);
		if(notification.get().getStatus() == false) {
			notification.get().setStatus(true);
			notificationRepository.save(notification.get());
		}
		
	}

	

}
