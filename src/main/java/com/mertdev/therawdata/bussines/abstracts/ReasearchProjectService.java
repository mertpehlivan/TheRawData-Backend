package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateReasearchProjectRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface ReasearchProjectService {
	public PostIdResponse createReasearchProject(CreateReasearchProjectRequest creProjectRequest);
}
