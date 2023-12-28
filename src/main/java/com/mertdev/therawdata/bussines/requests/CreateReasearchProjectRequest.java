package com.mertdev.therawdata.bussines.requests;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Data;
@Data
@Service
public class CreateReasearchProjectRequest {
	private String title;
	private Date date;
	private String comment;
	private List<String> authors;
}
