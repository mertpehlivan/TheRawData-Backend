package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.mertdev.therawdata.bussines.requests.CreateConferencePaperRequest;
import com.mertdev.therawdata.bussines.responses.GetPostResponse;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;
import com.mertdev.therawdata.bussines.responses.PublicationPostResponse;

public interface ConferencePaperService {
	public PostIdResponse createConferencePaper(CreateConferencePaperRequest conPaperRequest);

	List<GetPostResponse> getAllConferencePaper(String uniqueName, Pageable pageable);
}
