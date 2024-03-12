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
import com.mertdev.therawdata.bussines.responses.RegisterResponse;
import com.mertdev.therawdata.exceptions.EmailException;
import com.mertdev.therawdata.exceptions.UniqueNameException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @CrossOrigin()
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = authenticationService.register(request);
            return ResponseEntity.ok(response);
        } catch (EmailException e) {

            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage(),false));
        } catch (UniqueNameException e) {

            return ResponseEntity.badRequest().body(new RegisterResponse(e.getMessage(),false));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new AuthenticationResponse("Internal Server Error","",false));
        }
    }
    @PostMapping("/invite")
    public ResponseEntity<?> tempCreateUser(@RequestBody RegisterRequest request) {
    	System.out.println(request);
        try {
            return ResponseEntity.ok(authenticationService.tempCreateUser(request));
        }  catch (Exception e) {
            return ResponseEntity.status(500).body(new EmailException(e.getMessage()));
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
        return "Invalid email or password.";
    }
}
