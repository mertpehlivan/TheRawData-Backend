package com.mertdev.therawdata.bussines.abstracts;

import java.util.UUID;

import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;
import com.mertdev.therawdata.bussines.requests.UpdateRawDataRequest;

public interface RawDataService {
	 public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception;
	 public byte[] getPreviewImage(String previewImageName);
	
	void rawDataUpdate(UpdateRawDataRequest updateRawDataRequest) throws Exception;
	void deleteRawData(Long rawDataId);
	
}
