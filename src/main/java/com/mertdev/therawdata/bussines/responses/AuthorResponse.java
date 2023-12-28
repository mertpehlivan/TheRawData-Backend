package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponse {
	private String firstname;
	private String lastname;
	private String profileImageUrl;
}
