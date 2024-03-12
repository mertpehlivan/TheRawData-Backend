package com.mertdev.therawdata.bussines.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPublicationsCountResponse {
	private Integer articleCount = 0;
	private Integer chapterInBook= 0;
	private Integer companyTestReport= 0;
	private Integer conferencePaper= 0;
	private Integer researchProject= 0;
	private Integer thesis= 0;
	
	
}
