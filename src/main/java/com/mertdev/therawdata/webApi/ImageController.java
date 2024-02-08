package com.mertdev.therawdata.webApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.RawDataService;
import com.mertdev.therawdata.bussines.concretes.S3Service;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
public class ImageController {
	private final RawDataService rawDataService;
	private final S3Service s3Service;
	@GetMapping("previewImage/{imageName}")
	public byte[] getPreviewImage(@PathVariable String imageName) {
		
		return rawDataService.getPreviewImage(imageName);
	}
	@GetMapping("profileImage/{imageName}")
	public byte[] getProfileImage(@PathVariable String imageName) {
	    try {
	        if (imageName != null) {
	            return s3Service.getObject("profileImage/%s".formatted(imageName));
	        }
	    } catch (Exception e) {
	        return null;
	    }
	    return null;
	}

	
}
