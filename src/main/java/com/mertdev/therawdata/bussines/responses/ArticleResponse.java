package com.mertdev.therawdata.bussines.responses;

import java.util.Date;
import java.util.UUID;

import com.mertdev.therawdata.bussines.responses.abstracts.PublicationResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponse implements PublicationResponse{
	private String title;
	private UUID id;
	private String journalName;
	private String volume;
	private String issue;
	private String pages;
	private String doi;
	private String comment;
	private Date creationTime;
}
