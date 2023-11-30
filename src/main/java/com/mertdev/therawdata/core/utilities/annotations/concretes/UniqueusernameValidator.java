package com.mertdev.therawdata.core.utilities.annotations.concretes;

import org.springframework.beans.factory.annotation.Autowired;

import com.mertdev.therawdata.core.utilities.annotations.abstracts.UniqueUsername;
import com.mertdev.therawdata.dataAccess.abstracts.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class UniqueusernameValidator implements  ConstraintValidator<UniqueUsername,String> {
    @Autowired
    UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return !userRepository.existsByEmail(value);
	}

	
   
    
}
