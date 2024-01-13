package com.mertdev.therawdata.bussines.abstracts;


import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.mertdev.therawdata.bussines.responses.NotificationResponse;
import com.mertdev.therawdata.bussines.responses.NotificationStatusRespone;

public interface NotificationService {
	public List<NotificationResponse> getAll(Pageable pageable);
	void updateStatusFalseNotifications(Long notificationId);
	NotificationStatusRespone getCountStatusFalseNotifications();

	
}
