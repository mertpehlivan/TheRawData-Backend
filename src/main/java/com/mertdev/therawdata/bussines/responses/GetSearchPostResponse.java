package com.mertdev.therawdata.bussines.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSearchPostResponse {
	private UUID id;
	private String title;
	private String type;
	private String shareUserFullName;
	private String shareUserUniqueName;
	
}
