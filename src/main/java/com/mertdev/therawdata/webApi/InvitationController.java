package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.InvitationService;
import com.mertdev.therawdata.bussines.requests.InvitationRequest;
import com.mertdev.therawdata.bussines.responses.InvitationsResponse;

import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/api/v1/invitations")
@AllArgsConstructor
public class InvitationController {
	private final InvitationService invitationService;
	
	@GetMapping("/")
	public List<InvitationsResponse> getAllInvitations(Pageable pageable) {
		return invitationService.getAll(pageable);
	}
	@PutMapping("/admit")
	public void putMethodName(@RequestBody InvitationRequest  invitationRequest) {
		
	}
}
