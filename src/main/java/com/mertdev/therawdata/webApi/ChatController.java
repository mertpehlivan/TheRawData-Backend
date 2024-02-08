package com.mertdev.therawdata.webApi;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.mertdev.therawdata.bussines.responses.InvitationsResponse;
import com.mertdev.therawdata.bussines.responses.NotificationResponse;

import lombok.AllArgsConstructor;
@Controller
@AllArgsConstructor
public class ChatController{	
	@MessageMapping("/chat")
    @SendTo("/topic/{userId}/notfications")
    public NotificationResponse sendMessage(@Payload NotificationResponse notfication) {
        return notfication;
    }
	@MessageMapping("/chat2")
    @SendTo("/topic/{userId}/invitations")
    public InvitationsResponse invitationNotification(@Payload InvitationsResponse notfication) {
        return notfication;
    }
	
	
}
