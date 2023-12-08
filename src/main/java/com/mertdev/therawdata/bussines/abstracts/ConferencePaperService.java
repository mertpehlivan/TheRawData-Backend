package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface ConferencePaperService {
	public PostIdResponse createConferencePaper(CreateConferencePaperRequest conPaperRequest);
}
