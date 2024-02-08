package com.mertdev.therawdata.bussines.abstracts;

import java.util.UUID;

import com.mertdev.therawdata.bussines.requests.LoginRequest;
import com.mertdev.therawdata.bussines.requests.RegisterRequest;
import com.mertdev.therawdata.bussines.responses.AuthenticationResponse;

public interface AuthenticationService {
	public AuthenticationResponse register(RegisterRequest request) throws Exception;
	public AuthenticationResponse login(LoginRequest request);
	public UUID tempCreateUser(RegisterRequest request);
}
