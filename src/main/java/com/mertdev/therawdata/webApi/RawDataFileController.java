package com.mertdev.therawdata.webApi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.RawDataFileService;
import com.mertdev.therawdata.bussines.requests.CreateRawDataFileRequest;
import com.mertdev.therawdata.bussines.requests.UpdateTitleRawDataFileRequest;
import com.mertdev.therawdata.bussines.responses.CreatedRawDataFileIdResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rawdatafile")
@RequiredArgsConstructor
public class RawDataFileController {
	private final RawDataFileService rawDataFileService;
	
	@PostMapping("/create")
	public CreatedRawDataFileIdResponse createRawDataFile(CreateRawDataFileRequest createRawDataFileRequest) {
		return rawDataFileService.createRawDataFile(createRawDataFileRequest);
	}
	@PostMapping("/updateTitle")
	public void updateRawDataFileName(UpdateTitleRawDataFileRequest request) {
		System.out.println(request);
		rawDataFileService.updateRawDataFileName(request);
	}
}
