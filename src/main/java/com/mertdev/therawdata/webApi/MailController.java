package com.mertdev.therawdata.webApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.MailService;
import com.mertdev.therawdata.bussines.abstracts.UserService;
import com.mertdev.therawdata.bussines.requests.EmailVerficationRequest;
import com.mertdev.therawdata.bussines.requests.SendEmailCodeRequest;
import com.mertdev.therawdata.entities.concretes.User;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/mail")
@AllArgsConstructor
public class MailController {
	private final MailService mailService;
	private final UserService userService;

	@PostMapping("/email-verfication")
	public ResponseEntity<Boolean> sendEmailVerificationCode(@RequestBody EmailVerficationRequest request) {
		System.out.println(request.getCode());
		try {
			boolean verificationResult = mailService.userEmailVerfication(request);
			System.out.println(verificationResult);
			if (verificationResult) {

				return ResponseEntity.ok(true);
			} else {

				return ResponseEntity.ok(false);
			}
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
		}
	}

	@PostMapping("/sendEmailCode")
	public ResponseEntity<String> sendEmailCode(@RequestBody SendEmailCodeRequest request) {
		System.out.println(request.getEmail());
		User user = userService.getCurrentUser();
		try {
			mailService.sendEmailCode(user, request.getEmail());

			return ResponseEntity.ok("Code sent successfully");

		} catch (Exception e) {
			
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	

}
