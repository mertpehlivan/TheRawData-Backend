package com.mertdev.therawdata.bussines.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationsResponse {
	private String fullName;
	private String contant;
	private UUID userId;
	private Long id;
	private String title;
	private String userUrl;
	private String publicationUrl;
}
