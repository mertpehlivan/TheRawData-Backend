package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;

public interface RawDataService {
	 public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception;
	 public byte[] getPreviewImage(String previewImageName);
}
