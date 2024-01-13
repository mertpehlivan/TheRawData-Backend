package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
	private Long id;
	private String type;
	private String content;
	private String publicationTitle;
	private String fullName;
	private String userLink;
	private String publicationLink;
	private Boolean status;
}
