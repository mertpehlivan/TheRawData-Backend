package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.NotificationService;
import com.mertdev.therawdata.bussines.responses.NotificationResponse;
import com.mertdev.therawdata.bussines.responses.NotificationStatusRespone;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/v1/notification")
@AllArgsConstructor
public class NotificationController {
	private final NotificationService notificationService;
	@GetMapping("/")
	public List<NotificationResponse> getAll(Pageable pageable){
		System.out.println(pageable.getPageNumber());
		return notificationService.getAll(pageable);
	}
	@GetMapping("/count")
	public NotificationStatusRespone getStatusCount(){
		return notificationService.getCountStatusFalseNotifications();
	}

}
