package com.mertdev.therawdata.bussines.abstracts;

import com.mertdev.therawdata.bussines.requests.LoginRequest;
import com.mertdev.therawdata.bussines.requests.RegisterRequest;
import com.mertdev.therawdata.bussines.responses.AuthenticationResponse;

public interface AuthenticationService {
	public AuthenticationResponse register(RegisterRequest request);
	public AuthenticationResponse login(LoginRequest request);
}
