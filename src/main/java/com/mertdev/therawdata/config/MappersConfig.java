package com.mertdev.therawdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToArticleMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.DTOToUserMappers;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationAuthorToResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.PublicationToResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.RawDataFileToResponse;
import com.mertdev.therawdata.core.utilities.mappers.abstracts.UserToDTOMappers;
import com.mertdev.therawdata.core.utilities.mappers.concretes.DtoToUserMappersImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.PublicationAuthorToResponseImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.PublicationToResponseImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.RawDataFileToResponseImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.RequestToArticleMappersImpl;
import com.mertdev.therawdata.core.utilities.mappers.concretes.UserToDTOMappersImpl;
import com.mertdev.therawdata.entities.concretes.PublicationAuthor;

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
	@Bean
	PublicationToResponse publicationToResponse() {
		return new PublicationToResponseImpl();
	}
	@Bean
	RawDataFileToResponse dataFileToResponse() {
		return new RawDataFileToResponseImpl();
	}
	@Bean
	PublicationAuthorToResponse publicationAuthor() {
		return new PublicationAuthorToResponseImpl();
	}
}
