package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface ThesisService {
	public PostIdResponse createThesis(CreateThesisRequest createThesisRequest);
	public List<PublicationPostResponse> getAllThesis();
	public List<GetPostResponse> getAllThesis(String uniqueName);
}
