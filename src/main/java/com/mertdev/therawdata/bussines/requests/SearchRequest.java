package com.mertdev.therawdata.bussines.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchRequest {
	private String firstname;
	private String lastname;
}
