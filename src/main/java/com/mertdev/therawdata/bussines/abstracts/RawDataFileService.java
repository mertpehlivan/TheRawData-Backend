package com.mertdev.therawdata.bussines.abstracts;

import java.util.UUID;

import com.mertdev.therawdata.bussines.requests.CreateRawDataFileRequest;
import com.mertdev.therawdata.bussines.requests.UpdateTitleRawDataFileRequest;
import com.mertdev.therawdata.bussines.responses.CreatedRawDataFileIdResponse;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

public interface RawDataFileService {
	public CreatedRawDataFileIdResponse createRawDataFile(CreateRawDataFileRequest createRawDataFileRequest);
	public RawDataFile getByFileId(UUID id);
	void updateRawDataFileName(UpdateTitleRawDataFileRequest request);
	void deleteRawDataFile(UUID fileId) throws Exception;
}
