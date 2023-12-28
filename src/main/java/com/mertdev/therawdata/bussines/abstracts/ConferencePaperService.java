package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface ConferencePaperService {
	public PostIdResponse createConferencePaper(CreateConferencePaperRequest conPaperRequest);
	public List<PublicationPostResponse> getAllConferencePaper();
	public List<GetPostResponse> getAllConferencePaper(String uniqueName);
}
