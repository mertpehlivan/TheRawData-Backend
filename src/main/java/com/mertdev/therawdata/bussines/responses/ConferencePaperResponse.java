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
public class ConferencePaperResponse implements PublicationResponse{
	private UUID id;
	private String title;
	private Date date;
	private String conferenceName;
	private String location;
	private String doi;
	private String pages;
	private Date creationTime;
	private String comment;
}
