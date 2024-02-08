package com.mertdev.therawdata.bussines.concretes;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mertdev.therawdata.bussines.abstracts.AuthenticationService;
import com.mertdev.therawdata.bussines.abstracts.JwtService;
import com.mertdev.therawdata.bussines.abstracts.MailService;
import com.mertdev.therawdata.bussines.requests.LoginRequest;
import com.mertdev.therawdata.bussines.requests.RegisterRequest;
import com.mertdev.therawdata.bussines.responses.AuthenticationResponse;
import com.mertdev.therawdata.core.utilities.annotations.abstracts.UniqueUsername;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;
import com.mertdev.therawdata.entities.concretes.Role;
import com.mertdev.therawdata.entities.concretes.User;
import com.mertdev.therawdata.exceptions.EmailException;
import com.mertdev.therawdata.exceptions.EmailOrPasswordNotFound;
import com.mertdev.therawdata.exceptions.UniqueNameException;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final MailService mailService;

	@Override
	public AuthenticationResponse register(RegisterRequest request) throws Exception {

	    if (userRepository.existsByEmail(request.getEmail())) {
	        User user = userRepository.findByEmail(request.getEmail());
	        if (user.getEmailVerficationStatus()) {
	            throw new EmailException("Email already in use");
	        } else {
	            String code = mailService.generateVerificationCode();
	            mailService.sendVerificationCode(request.getLastname(), request.getEmail(), code);
	            
	            user.setFirstname(request.getFirstname());
	            user.setLastname(request.getLastname());
	            user.setPassword(passwordEncoder.encode(request.getPassword()));
	            user.setCountry(request.getCountry());
	            user.setRole(Role.USER);
	            user.setUniqueName(request.getUniqueName());
	            user.setEmailVerfication(code);
	            user.setEmailVerficationStatus(false);

	            userRepository.save(user);

	            var jwtToken = jwtService.generateToken(user);
	            return AuthenticationResponse.builder().token(jwtToken).build();
	        }
	    } else if (userRepository.existsByUniqueName(request.getUniqueName())) {
	        throw new UniqueNameException("Unique name already in use");
	    }

	    try {
	        String code = mailService.generateVerificationCode();
	        mailService.sendVerificationCode(request.getLastname(), request.getEmail(), code);

	        var user = User.builder()
	                .firstname(request.getFirstname())
	                .lastname(request.getLastname())
	                .email(request.getEmail())
	                .password(passwordEncoder.encode(request.getPassword()))
	                .country(request.getCountry())
	                .role(Role.USER)
	                .uniqueName(request.getUniqueName())
	                .emailVerfication(code)
	                .emailVerficationStatus(false)
	                .build();

	        userRepository.save(user);

	        var jwtToken = jwtService.generateToken(user);
	        return AuthenticationResponse.builder().token(jwtToken).build();
	    } catch (Exception e) {
	        System.err.println(e);
	        throw e;
	    }
	}


	@Override
	public AuthenticationResponse login(LoginRequest request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = userRepository.findAllByEmail(request.getEmail())
				.orElseThrow(() -> new EmailOrPasswordNotFound("User not found"));
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder().token(jwtToken).build();
	}

	@Override
	public UUID tempCreateUser(RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			User user = userRepository.findByEmail(request.getEmail());
			if (user.getEmailVerficationStatus()) {
				throw new EmailException("A user with this email already exists");
			}else {
				mailService.sendInvite(user.getEmail(), user.getFirstname(), user.getLastname());
				return user.getId();
			}
			
		}else {
			var user = User.builder().firstname(request.getFirstname()).lastname(request.getLastname())
					.email(request.getEmail()).role(Role.USER).emailVerficationStatus(false).build();
			userRepository.save(user);
			mailService.sendInvite(user.getEmail(), user.getFirstname(), user.getLastname());
			return user.getId();
		}
		

	}

}
