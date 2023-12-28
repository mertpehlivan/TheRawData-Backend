package com.mertdev.therawdata.core.utilities.mappers.abstracts;

import java.util.List;

import com.mertdev.therawdata.bussines.responses.RawDataFileResponse;
import com.mertdev.therawdata.entities.concretes.RawDataFile;

public interface RawDataFileToResponse {
	public List<RawDataFileResponse> toResponse(List<RawDataFile> file);
}
