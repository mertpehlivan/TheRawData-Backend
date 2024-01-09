package com.mertdev.therawdata.webApi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.RawDataService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/")
@AllArgsConstructor
public class ImageController {
	private final RawDataService rawDataService;

	@GetMapping("previewImage/{imageName}")
	public byte[] getPreviewImage(@PathVariable String imageName) {
		
		return rawDataService.getPreviewImage(imageName);
	}
}
