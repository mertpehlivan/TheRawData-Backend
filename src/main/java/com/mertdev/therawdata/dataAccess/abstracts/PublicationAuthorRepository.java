package com.mertdev.therawdata.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mertdev.therawdata.entities.concretes.PublicationAuthor;
@Repository
public interface PublicationAuthorRepository extends JpaRepository<PublicationAuthor, Long>{
	
}
