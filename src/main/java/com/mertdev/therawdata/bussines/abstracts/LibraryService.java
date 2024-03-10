package com.mertdev.therawdata.bussines.abstracts;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.mertdev.therawdata.bussines.responses.GetLibraryItemResponse;

public interface LibraryService {
	public List<GetLibraryItemResponse> getMyPublicationsToMyLibrary(Pageable pageable) throws Exception;

	byte[] getMyPublicationsToRawData(Long id) throws Exception;
}
