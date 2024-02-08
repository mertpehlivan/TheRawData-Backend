package com.mertdev.therawdata.webApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.RawDataService;
import com.mertdev.therawdata.bussines.concretes.S3Service;
import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;
import com.mertdev.therawdata.bussines.requests.UpdateRawDataRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rawData")
@RequiredArgsConstructor
public class RawDataController {
	private final RawDataService rawDataService;
	private final S3Service s3Service;

	@PostMapping("/create")
	public ResponseEntity<String> createRawData(CreateRawDataRequest createRawDataRequest) {
		try {
			rawDataService.createRawData(createRawDataRequest);
			return ResponseEntity.ok("Başarılı işlem");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return ResponseEntity.badRequest().body(e.toString());
		}

	}

	@GetMapping("previewImage/{imageName}")
	public byte[] getPreviewImage(@PathVariable String imageName) {
		System.out.println(imageName);
		return null;
	}

	@PostMapping("/update")
	public ResponseEntity<String> updateRawData(@Valid UpdateRawDataRequest updateRawDataRequest) {
		try {
			rawDataService.rawDataUpdate(updateRawDataRequest);
			System.out.println(updateRawDataRequest.getComment());
			return ResponseEntity.ok("Raw data updated successfully.");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error updating raw data: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteRawData(@PathVariable Long id) {
		try {
			System.out.println("Deleting raw data with ID: " + id);
			
			rawDataService.deleteRawData(id);

			return ResponseEntity.ok("Raw data deleted successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error deleting raw data: " + e.getMessage());
		}
	}

}