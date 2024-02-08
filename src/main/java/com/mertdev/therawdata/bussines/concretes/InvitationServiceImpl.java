package com.mertdev.therawdata.bussines.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.InvitationService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.responses.InvitationsResponse;
import com.mertdev.therawdata.dataAccess.abstracts.InvitationRepository;
import com.mertdev.therawdata.entities.concretes.Invitations;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {
	private final InvitationRepository invitationRepository;
	private final UserService userService;

	@Override
	public List<InvitationsResponse> getAll(Pageable pageable) {
		User user = userService.getCurrentUser();
		Page<Invitations> invitations = invitationRepository.findByUserId(user.getId(),
				PageRequest.of(pageable.getPageNumber(), 6, Sort.by(Sort.Direction.DESC, "id")));
		 List<InvitationsResponse> invitationsResponses = new ArrayList<>();
		for(Invitations invitation : invitations) {
			InvitationsResponse invitationsResponse = new InvitationsResponse();
			invitationsResponse.setId(invitation.getId());
			invitationsResponse.setContant(invitation.getContant());
			invitationsResponse.setFullName(invitation.getFullName());
			invitationsResponse.setPublicationUrl(invitation.getPublicationUrl());
			invitationsResponse.setTitle(invitation.getTitle());
			invitationsResponse.setUserUrl(invitation.getUserUrl());
			invitationsResponse.setPublicationId(invitation.getPublicationId());
			
			invitationsResponses.add(invitationsResponse);
		}
		return invitationsResponses;

	}

}
