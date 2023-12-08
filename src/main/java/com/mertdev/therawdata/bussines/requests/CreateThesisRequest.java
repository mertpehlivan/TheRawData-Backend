package com.mertdev.therawdata.bussines.requests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateThesisRequest {
	private String title;
	private String degree;
	private String pages;
	private String university;
	private List<String> authors;
}
