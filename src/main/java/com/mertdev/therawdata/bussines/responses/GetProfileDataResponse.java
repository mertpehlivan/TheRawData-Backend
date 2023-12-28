package com.mertdev.therawdata.bussines.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileDataResponse {
	private UUID id;
	private String firstname;
	private String lastname;
	private String country;
	private String uniqueName;
	private int followers;
	private int following;
}
