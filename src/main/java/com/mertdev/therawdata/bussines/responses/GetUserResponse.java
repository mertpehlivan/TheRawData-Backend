package com.mertdev.therawdata.bussines.responses;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse{
	private UUID id;
	private String firstname;
	private String lastname;
	private String email;
	private String country;
	
}
