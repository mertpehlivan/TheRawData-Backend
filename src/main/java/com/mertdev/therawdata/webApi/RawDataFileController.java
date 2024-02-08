package com.mertdev.therawdata.webApi;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteRawDataFile(@PathVariable UUID id) {
		try {
			System.out.println("Deleting raw data with ID: " + id);
			
			rawDataFileService.deleteRawDataFile(id);

			return ResponseEntity.ok("Raw data deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting raw data: " + e.getMessage());
		}
	}
}
