package com.mertdev.therawdata.bussines.responses;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {
	private UUID id;
	private UUID userId;
	private String fullname;
	private String uniqueName;
	private String title;
	private String creationTime;
	private String publicationType;
	private String comment;
	private List<AuthorResponse> authors;
	private SummaryRawDataFileResponse rawdatafile;
	
}
