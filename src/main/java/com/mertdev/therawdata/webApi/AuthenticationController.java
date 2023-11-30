package com.mertdev.therawdata.webApi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mertdev.therawdata.bussines.abstracts.AuthenticationService;
import com.mertdev.therawdata.bussines.requests.LoginRequest;
import com.mertdev.therawdata.bussines.requests.RegisterRequest;
import com.mertdev.therawdata.bussines.responses.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @CrossOrigin()
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
    	System.out.println("servis çağrıldı");
    	try {
    		return ResponseEntity.ok(authenticationService.register(request));
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
    	
        
    }
    @CrossOrigin()
    @PostMapping("/login")
    public	AuthenticationResponse authenticate(
            @RequestBody LoginRequest request
    ){
    	System.out.println(request);
    	try {
    		 return authenticationService.login(request);
		} catch (Exception e) {
			System.out.println(e);
			throw e;
		}
    	
       
    }
    @GetMapping("/check-auth")
    public String checkAuthentication(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "Kullanıcı kimlik doğrulama durumu: Authenticated";
        } else {
            return "Kullanıcı kimlik doğrulama durumu: Not Authenticated";
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleBadCredentialsException(BadCredentialsException ex) {
        return "Incorrect username or password.";
    }
}
