package com.mertdev.therawdata.dataAccess.abstracts;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mertdev.therawdata.entities.abstracts.Publication;
import com.mertdev.therawdata.entities.concretes.Article;
import com.mertdev.therawdata.entities.concretes.PublicationPost;
@Repository
public interface PublicationPostRepository extends JpaRepository<PublicationPost, UUID>{
	public List<PublicationPost> findAllByOrderByCreationTimeDesc(Pageable pageable);
	public List<PublicationPost> findAllByOrderByCreationTimeDesc();
	
}
