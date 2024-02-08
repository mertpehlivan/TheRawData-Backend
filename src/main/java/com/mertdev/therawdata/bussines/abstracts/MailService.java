package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.EmailVerficationRequest;

import jakarta.mail.MessagingException;

public interface MailService {

	
	void sendForgetPasswordCode(String email, String forgetPasswordCode);

	boolean userEmailVerfication(EmailVerficationRequest emailVerficationRequest);

	String generateVerificationCode();

	void sendVerificationCode(String lastname, String email, String verificationCode) throws MessagingException;
	
	void sendInvite(String email,String firstname,String lastname);
}
