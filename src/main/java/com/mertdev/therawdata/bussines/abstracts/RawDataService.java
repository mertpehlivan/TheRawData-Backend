package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;
import com.mertdev.therawdata.bussines.responses.PostIdResponse;

public interface RawDataService {
	 public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception;
}
