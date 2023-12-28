package com.mertdev.therawdata.core.utilities.mappers.concretes;

import java.util.ArrayList;
import java.util.List;

import com.mertdev.therawdata.bussines.responses.RawDataFileResponse;
import com.mertdev.therawdata.bussines.responses.RawDataResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RawDataFileToResponse;
import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.RawData;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

public class RawDataFileToResponseImpl implements RawDataFileToResponse{

	@Override
	public List<RawDataFileResponse> toResponse(List<RawDataFile> files) {
		List<RawDataFileResponse> rawDataFiles = new ArrayList<>();
		for(RawDataFile rawDataFile : files) {
			RawDataFileResponse tempFile = new RawDataFileResponse();
			tempFile.setId(rawDataFile.getId());
			tempFile.setName(rawDataFile.getName());
			List<RawDataResponse> rawDatas = new ArrayList<>();
			for(RawData rawData : rawDataFile.getRawDatas()) {
				RawDataResponse tempRawData = new RawDataResponse();
				tempRawData.setComment(rawData.getComment());
				tempRawData.setId(rawData.getId());
				tempRawData.setName(rawData.getName());
				tempRawData.setPreviewImageName(rawData.getPreviewImageName());
				tempRawData.setRawDataName(rawData.getRawDataName());
				rawDatas.add(tempRawData);
			}
			tempFile.setRawDatas(rawDatas);
			rawDataFiles.add(tempFile);
		}
		return rawDataFiles;
	}

}
