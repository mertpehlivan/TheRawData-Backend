package com.mertdev.therawdata.webApi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.LibraryService;
import com.mertdev.therawdata.bussines.responses.GetLibraryItemResponse;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {
	
	@Autowired
	private LibraryService libraryService;
	
	
	@GetMapping("/myPublication")
	public ResponseEntity<?> getMyPublicationToLibrary(Pageable pageable){
		System.out.println("sayfa :"+ pageable.getPageNumber());
		
		try {
			List<GetLibraryItemResponse> responses = libraryService.getMyPublicationsToMyLibrary(pageable);
			return ResponseEntity.ok(responses);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	@GetMapping("/rawData/{id}")
	public byte[] getMyPublicationToRawData(@PathVariable Long id) throws Exception{
		System.out.println(id);
		try {
			return libraryService.getMyPublicationsToRawData(id);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
}
