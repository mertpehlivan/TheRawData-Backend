package com.mertdev.therawdata.bussines.concretes;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.AuthenticationService;
import com.mertdev.therawdata.bussines.abstracts.JwtService;
import com.mertdev.therawdata.bussines.requests.LoginRequest;
import com.mertdev.therawdata.bussines.requests.RegisterRequest;
import com.mertdev.therawdata.bussines.responses.AuthenticationResponse;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.Role;
import com.mertdev.therawdata.entities.concretes.User;
import com.mertdev.therawdata.exceptions.EmailOrPasswordNotFound;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	@Override
	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.country(request.getCountry())
				.role(Role.USER)
				.build();
		userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Override
	public AuthenticationResponse login(LoginRequest request){
		authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getEmail(), 
							request.getPassword()
							)
				);
		var user = userRepository.findAllByEmail(request.getEmail()).orElseThrow(()-> new EmailOrPasswordNotFound("User not found"));
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

}
