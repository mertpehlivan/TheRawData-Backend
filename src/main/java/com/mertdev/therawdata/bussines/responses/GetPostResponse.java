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
	private UUID shareUserId;
	private String shareFullName;
	private String shareUniqueName;
	private String shareProfileImage;
	private UUID id;
	private UUID userId;
	private String fullname;
	private String uniqueName;
	private String profileImage;
	private String title;
	private String creationTime;
	private String publicationType;
	private String pdfFileName;
	private Boolean addOnly;
	private Boolean pdfStatus;
	private String comment;
	private String summary;
	private List<AuthorResponse> authors;
	private List<SummaryRawDataFileResponse> rawdatafiles;
	
}
