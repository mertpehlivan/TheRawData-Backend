package com.mertdev.therawdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToUserMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToArticleMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.core.utilities.mappers.concretes.DtoToUserMappersImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.RequestToArticleMappersImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.UserToDTOMappersImpl;

@Configuration
public class MappersConfig {
	@Bean
	DTOToArticleMappers requestToArticleMappers() {
		return new RequestToArticleMappersImpl();
	}
	
	@Bean
	UserToDTOMappers userToDTOMappers() {
		return new UserToDTOMappersImpl();
	}
	@Bean 
	DTOToUserMappers dtoToUserMappers() {
		return new DtoToUserMappersImpl();
	}
}
