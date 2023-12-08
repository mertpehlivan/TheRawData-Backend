package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateThesisRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface ThesisService {
	public PostIdResponse createThesis(CreateThesisRequest createThesisRequest);
}
