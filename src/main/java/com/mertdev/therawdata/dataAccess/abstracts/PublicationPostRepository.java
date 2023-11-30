package com.mertdev.therawdata.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mertdev.therawdata.entities.concretes.PublicationPost;
@Repository
public interface PublicationPostRepository extends JpaRepository<PublicationPost, Long>{

}
