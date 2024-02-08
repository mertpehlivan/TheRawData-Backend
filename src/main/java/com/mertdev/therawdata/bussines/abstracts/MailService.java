package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.EmailVerficationRequest;
import com.mertdev.therawdata.entities.concretes.User;

import jakarta.mail.MessagingException;

public interface MailService {

	
	void sendForgetPasswordCode(String email, String forgetPasswordCode);

	boolean userEmailVerfication(EmailVerficationRequest emailVerficationRequest);

	String generateVerificationCode();

	
	void sendInvite(String email,String firstname,String lastname);

	void sendVerificationCode(User user, String email, String verificationCode) throws MessagingException;
}
