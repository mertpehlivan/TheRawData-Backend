package com.mertdev.therawdata.webApi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1")

public class AuthCheckController {
	@CrossOrigin (origins = "http://localhost:3000")
	@GetMapping("/check-auth")
	public ResponseEntity<?> getMethodName() {
		return ResponseEntity.ok("ok");
	}
	
}
