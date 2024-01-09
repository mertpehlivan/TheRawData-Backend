package com.mertdev.therawdata.webApi;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.RawDataService;
import com.mertdev.therawdata.bussines.concretes.S3Service;
import com.mertdev.therawdata.bussines.requests.CreateRawDataRequest;

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
	 public byte[] getPreviewImage(@PathVariable String imageName){
		 System.out.println(imageName);
		 return null;
	 }
    
} 