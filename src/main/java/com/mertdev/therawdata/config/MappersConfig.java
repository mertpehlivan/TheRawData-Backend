package com.mertdev.therawdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mertdev.therawdata.core.utilities.mappers.abstracts.DtoToUserMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RequestToArticleMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.core.utilities.mappers.concretes.DtoToUserMappersImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.RequestToArticleMappersImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.UserToDTOMappersImpl;

@Configuration
public class MappersConfig {
	@Bean
	RequestToArticleMappers requestToArticleMappers() {
		return new RequestToArticleMappersImpl();
	}
	
	@Bean
	UserToDTOMappers userToDTOMappers() {
		return new UserToDTOMappersImpl();
	}
	@Bean 
	DtoToUserMappers dtoToUserMappers() {
		return new DtoToUserMappersImpl();
	}
}
