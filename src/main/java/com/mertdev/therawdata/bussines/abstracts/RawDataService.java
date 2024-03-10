package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;
import com.mertdev.therawdata.bussines.requests.UpdateRawDataRequest;
import com.mertdev.therawdata.entities.concretes.User;

public interface RawDataService {
	 public void createRawData(CreateRawDataRequest createRawDataRequest) throws Exception;
	 public byte[] getPreviewImage(String previewImageName);
	
	void rawDataUpdate(UpdateRawDataRequest updateRawDataRequest) throws Exception;
	void deleteRawData(Long rawDataId);
	byte[] getRawDataMyPublicationToLibrary(Long rawDataId, User user) throws Exception;
	Double totalRawDataSize();
	
}
