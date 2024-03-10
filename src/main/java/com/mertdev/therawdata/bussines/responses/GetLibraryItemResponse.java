package com.mertdev.therawdata.bussines.responses;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetLibraryItemResponse {
	private UUID postId;
	private String postTitle;
	private String publicationType;
	private String publicationSummary;
	private List<GetLibraryRawDataFileResponse> files;
	
	
}
