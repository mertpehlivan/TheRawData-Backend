package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface ReasearchProjectService {
	public PostIdResponse createReasearchProject(CreateReasearchProjectRequest creProjectRequest);
	List<GetPostResponse> getAllReasearchProject(String uniqueName);
}
