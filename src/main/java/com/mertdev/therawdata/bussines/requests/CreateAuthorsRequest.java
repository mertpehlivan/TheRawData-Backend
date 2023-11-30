package com.mertdev.therawdata.bussines.requests;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorsRequest {
	private UUID id;
	private List<String> authors;
}
