package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.mertdev.therawdata.bussines.responses.InvitationsResponse;

public interface InvitationService {
	public List<InvitationsResponse> getAll(Pageable pageable);
}
